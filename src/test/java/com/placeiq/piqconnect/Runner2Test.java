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

import org.apache.hadoop.io.VLongWritable;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import static com.placeiq.piqconnect.Utils.*;

import com.placeiq.piqconnect.BlockWritable.TYPE;


public class Runner2Test {
    ReduceDriver<VLongWritable, BlockWritable, BlockIndexWritable, BlockWritable> reduceDriver;

    @Before
    public void setUp() {
        IterationStage2._Reducer reducer = new IterationStage2._Reducer();
        reduceDriver = ReduceDriver.newReduceDriver(reducer);
    }


    //
    //     |A B|          |X|          |AX|   |BY|
    // M = |C D|      V = |Y|    M*V = |CX| + |DY|
    //
    //      |0|
    //  V = |1|
    //      |2|
    //      |3|
    //
    //       |0|        |2|
    //  AX = |0|   BY = |2|
    //
    //       |0|        |2|
    //  CX = |1|   DY = |3|
    //
    @Test
    public void reduce1() throws IOException {
        reduceDriver.getConfiguration().setInt(Constants.PROP_BLOCK_SIZE, 2);

        reduceDriver.addInput(new VLongWritable(0), Arrays.asList(
                blockVector(TYPE.VECTOR_INITIAL, 0, 1),
                blockVector(TYPE.VECTOR_INCOMPLETE, 0, 0),
                blockVector(TYPE.VECTOR_INCOMPLETE, 2, 2)));

        reduceDriver.addInput(new VLongWritable(1), Arrays.asList(
                blockVector(TYPE.VECTOR_INITIAL, 2, 3),
                blockVector(TYPE.VECTOR_INCOMPLETE, 0, 1),
                blockVector(TYPE.VECTOR_INCOMPLETE, 2, 3)));

        reduceDriver.addOutput(blockIndex(0), blockVector(TYPE.VECTOR_INCOMPLETE, 0, 0));
        reduceDriver.addOutput(blockIndex(1), blockVector(TYPE.VECTOR_INCOMPLETE, 0, 1));

        reduceDriver.runTest();
    }

    //
    //  |0|    |1|    |3 |   |0|
    //  |1|  + |0|  + |-1| = |0|
    //  |3|    |2|    |-1|   |2|
    //
    @Test
    public void reduce2() throws IOException {
        reduceDriver.getConfiguration().setInt(Constants.PROP_BLOCK_SIZE, 3);

        reduceDriver.addInput(new VLongWritable(0), Arrays.asList(
                blockVector(TYPE.VECTOR_INITIAL, 0, 1, 3),
                blockVector(TYPE.VECTOR_INCOMPLETE, 1, 0, 2),
                blockVector(TYPE.VECTOR_INCOMPLETE, 3, -1, -1)));

        reduceDriver.addOutput(blockIndex(0), blockVector(TYPE.VECTOR_INCOMPLETE, 0, 0, 2));

        reduceDriver.runTest();
    }

    //
    //  |3|     |2|    |2|
    //  |-1|  + |1|  = |-1|
    //  |-1|    |2|    |-1|
    //
    @Test
    public void reduce3() throws IOException {
        reduceDriver.getConfiguration().setInt(Constants.PROP_BLOCK_SIZE, 3);

        reduceDriver.addInput(new VLongWritable(0), Arrays.asList(
                blockVector(TYPE.VECTOR_INITIAL, 3, -1, -1),
                blockVector(TYPE.VECTOR_INCOMPLETE, 2, 1, 2)));

        reduceDriver.addOutput(blockIndex(0), blockVector(TYPE.VECTOR_INCOMPLETE, 2, -1, -1));

        reduceDriver.runTest();
    }
}

