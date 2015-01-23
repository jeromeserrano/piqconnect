/**
 * PIQConnect: Connected-component analysis for Big Graph
 *
 * __________.___________  _________                                     __
 * \______   \   \_____  \ \_   ___ \  ____   ____   ____   ____   _____/  |_
 *  |     ___/   |/  / \  \/    \  \/ /  _ \ /    \ /    \_/ __ \_/ ___\   __\
 *  |    |   |   /   \_/.  \     \___(  <_> )   |  \   |  \  ___/\  \___|  |
 *  |____|   |___\_____\ \_/\______  /\____/|___|  /___|  /\___  >\___  >__|
 *                      \__>       \/            \/     \/     \/     \/
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
import org.apache.hadoop.io.WritableComparator;

import java.io.IOException;

public class VLongWritableComparator extends WritableComparator {
    public VLongWritableComparator() {
        super(VLongWritable.class);
    }

    public int compare(byte[] b1, int s1, int l1,
                       byte[] b2, int s2, int l2) {
        long thisValue;
        long thatValue;
        try {
            thisValue = readVLong(b1, s1);
            thatValue = readVLong(b2, s2);
        } catch (IOException e) {
            throw new RuntimeException("corrupted data, failed to parse VLongWritable");
        }
        return (thisValue < thatValue ? -1 : (thisValue == thatValue ? 0 : 1));
    }
}
