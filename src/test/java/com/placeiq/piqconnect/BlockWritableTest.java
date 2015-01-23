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

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class BlockWritableTest {

    @Test
    public void serializeVector() throws IOException {
        BlockWritable b1 = new BlockWritable(6, BlockWritable.TYPE.VECTOR_INITIAL);
        b1.setVectorElem(1, 10L);
        b1.setVectorElem(3, 30L);
        b1.setVectorElem(5, 50L);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream w = new DataOutputStream(baos);
        b1.write(w);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        DataInputStream r = new DataInputStream(bais);
        BlockWritable b2 = new BlockWritable(6);
        b2.readFields(r);

        assertEquals(b1, b2);
    }

    @Test
    public void serializeMatrix() throws IOException {
        BlockWritable b1 = new BlockWritable(3, BlockWritable.TYPE.MATRIX);
        b1.addMatrixElem(1, 10);
        b1.addMatrixElem(3, 30);
        b1.addMatrixElem(5, 50);
        b1.setBlockRow(10);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream w = new DataOutputStream(baos);
        b1.write(w);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        DataInputStream r = new DataInputStream(bais);
        BlockWritable b2 = new BlockWritable(3);
        b2.readFields(r);

        assertEquals(b1, b2);
    }
}

