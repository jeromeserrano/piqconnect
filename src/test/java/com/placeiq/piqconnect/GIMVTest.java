/**
 * PIQConnect: Connected-component analysis for Big Graph
 *
 * __________.___________      _____    _____________ ___  _________
 * \______   \   \_____  \    /  _  \  /   _____/    |   \/   _____/
 *  |     ___/   |/  / \  \  /  /_\  \ \_____  \|    |   /\_____  \
 *  |    |   |   /   \_/.  \/    |    \/        \    |  / /        \
 *  |____|   |___\_____\ \_/\____|__  /_______  /______/ /_______  /
 *                      \__>        \/        \/                 \/
 *
 * Copyright (c) 2014 PlaceIQ, Inc
 *
 * This software is licensed under Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ----------------------------------------------------------------------------
 * Author: Jerome Serrano <jerome.serrano@placeiq.com>
 * Date: 2015-01-09
 * ---------------------------------------------------------------------------*/

 package com.placeiq.piqconnect;

import gnu.trove.list.array.TLongArrayList;
import gnu.trove.list.array.TIntArrayList;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class GIMVTest {

    //
    //     |0 1 0|      |0|        |1|
    // M = |1 0 1|  V = |1|  res = |0|
    //     |0 1 0|      |2|        |1|
    @Test
    public void simple() throws IOException {
        TIntArrayList matrixIndexes = new TIntArrayList(new int[] {0, 1, 1, 0, 1, 2, 2, 1});
        TLongArrayList vectorValues = new TLongArrayList(new long[] {0, 1, 2});
        TLongArrayList res = new TLongArrayList(new long[] {1, 0, 1});
        Assert.assertEquals(res, IterationStage1.GIMV.minBlockVector(matrixIndexes, vectorValues));
    }

    @Test
    public void simple2() throws IOException {
        TIntArrayList matrixIndexes = new TIntArrayList(new int[] {0, 1, 1, 0, 1, 2, 2, 1, 3, 3});
        TLongArrayList vectorValues = new TLongArrayList(new long[] {0, 1, 2, 3});
        TLongArrayList res = new TLongArrayList(new long[] {1, 0, 1, 3});
        assertEquals(res, IterationStage1.GIMV.minBlockVector(matrixIndexes, vectorValues));
    }

    //
    //     |0 1|      |0|        |1|
    // M = |0 1|  V = |1|  res = |1|
    @Test
    public void simple3() throws IOException {
        TIntArrayList matrixIndexes = new TIntArrayList(new int[] {0, 1, 1, 1});
        TLongArrayList vectorValues = new TLongArrayList(new long[] {0, 1});
        TLongArrayList res = new TLongArrayList(new long[] {1, 1});
        assertEquals(res, IterationStage1.GIMV.minBlockVector(matrixIndexes, vectorValues));
    }

    //
    //     |0 1|      |0|        |1|
    // M = |1 0|  V = |1|  res = |0|
    @Test
    public void simple4() throws IOException {
        TIntArrayList matrixIndexes = new TIntArrayList(new int[] {0, 1, 1, 0});
        TLongArrayList vectorValues = new TLongArrayList(new long[] {0, 1});
        TLongArrayList res = new TLongArrayList(new long[] {1, 0});
        assertEquals(res, IterationStage1.GIMV.minBlockVector(matrixIndexes, vectorValues));
    }

    //
    //     |0 0 0|      |3 |        |-1|
    // M = |0 0 0|  V = |-1|  res = |-1|
    //     |1 0 0|      |-1|        | 3|
    @Test
    public void partVector1() throws IOException {
        TIntArrayList matrixIndexes = new TIntArrayList(new int[] {2, 0});
        TLongArrayList vectorValues = new TLongArrayList(new long[] {3, -1, -1});
        TLongArrayList res = new TLongArrayList(new long[] {-1, -1, 3});
        assertEquals(res, IterationStage1.GIMV.minBlockVector(matrixIndexes, vectorValues));
    }

    //
    //     |0 0 0|      |-1|        |-1|
    // M = |0 0 0|  V = |-1|  res = |-1|
    //     |1 0 0|      |-1|        |-1|
    @Test
    public void partVector2() throws IOException {
        TIntArrayList matrixIndexes = new TIntArrayList(new int[] {2, 0});
        TLongArrayList vectorValues = new TLongArrayList(new long[] {-1, -1, -1});
        TLongArrayList res = new TLongArrayList(new long[] {-1, -1, -1});
        assertEquals(res, IterationStage1.GIMV.minBlockVector(matrixIndexes, vectorValues));
    }

    //
    //     |1 0 0|      | 3|        | 3|
    // M = |0 0 0|  V = |-1|  res = |-1|
    //     |1 0 0|      |-1|        | 3|
    @Test
    public void partVector3() throws IOException {
        TIntArrayList matrixIndexes = new TIntArrayList(new int[] {0, 0, 2, 0});
        TLongArrayList vectorValues = new TLongArrayList(new long[] {3, -1, -1});
        TLongArrayList res = new TLongArrayList(new long[] {3, -1, 3});
        assertEquals(res, IterationStage1.GIMV.minBlockVector(matrixIndexes, vectorValues));
    }

    //
    //     |1 1 0|      | 3|        |-1|
    // M = |0 0 0|  V = |-1|  res = |-1|
    //     |1 0 0|      |-1|        | 3|
    @Test
    public void partVector4() throws IOException {
        TIntArrayList matrixIndexes = new TIntArrayList(new int[] {0, 0, 0, 1, 2, 0});
        TLongArrayList vectorValues = new TLongArrayList(new long[] {3, -1, -1});
        TLongArrayList res = new TLongArrayList(new long[] {3, -1, 3});
        assertEquals(res, IterationStage1.GIMV.minBlockVector(matrixIndexes, vectorValues));
    }

    //
    //     |0 0 1|      |0|        | 2|
    // M = |0 0 0|  V = |1|  res = |-1|
    //     |0 0 0|      |2|        |-1|
    @Test
    public void partVector5() throws IOException {
        TIntArrayList matrixIndexes = new TIntArrayList(new int[] {0, 2});
        TLongArrayList vectorValues = new TLongArrayList(new long[] {0, 1, 2});
        TLongArrayList res = new TLongArrayList(new long[] {2, -1, -1});
        assertEquals(res, IterationStage1.GIMV.minBlockVector(matrixIndexes, vectorValues));
    }

    //
    //     |1 0 0|      | 1|        | 1|
    // M = |0 0 0|  V = |-1|  res = |-1|
    //     |0 0 0|      |-1|        |-1|
    @Test
    public void partVector6() throws IOException {
        TIntArrayList matrixIndexes = new TIntArrayList(new int[] {0, 0});
        TLongArrayList vectorValues = new TLongArrayList(new long[] {1, -1, -1});
        TLongArrayList res = new TLongArrayList(new long[] {1, -1, -1});
        assertEquals(res, IterationStage1.GIMV.minBlockVector(matrixIndexes, vectorValues));
    }

    //
    //     |1 0 0|      | 1|        | 1|
    // M = |1 0 0|  V = |-1|  res = | 1|
    //     |0 0 0|      |-1|        |-1|
    @Test
    public void partVector7() throws IOException {
        TIntArrayList matrixIndexes = new TIntArrayList(new int[] {0, 0, 1, 0});
        TLongArrayList vectorValues = new TLongArrayList(new long[] {1, -1, -1});
        TLongArrayList res = new TLongArrayList(new long[] {1, 1, -1});
        assertEquals(res, IterationStage1.GIMV.minBlockVector(matrixIndexes, vectorValues));
    }

    //
    //     |0 0 0|      | 4|        |-1|
    // M = |1 0 0|  V = |-1|  res = | 4|
    //     |0 0 0|      |-1|        |-1|
    @Test
    public void partVector8() throws IOException {
        TIntArrayList matrixIndexes = new TIntArrayList(new int[] {1, 0});
        TLongArrayList vectorValues = new TLongArrayList(new long[] {4, -1, -1});
        TLongArrayList res = new TLongArrayList(new long[] {-1, 4, -1});
        assertEquals(res, IterationStage1.GIMV.minBlockVector(matrixIndexes, vectorValues));
    }
}

