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

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.VLongWritable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FinalResultBuilder {

    public static class _Mapper extends Mapper<BlockIndexWritable, BlockWritable, VLongWritable, VLongWritable> {
        private final VLongWritable KEY   = new VLongWritable();
        private final VLongWritable VALUE = new VLongWritable();

        private int blockSize = 32;

        @Override
        public void setup(Mapper.Context ctx) {
            Configuration conf = ctx.getConfiguration();
            blockSize = conf.getInt(Constants.PROP_BLOCK_SIZE, 32);
        }

        public void map(BlockIndexWritable key, BlockWritable value, Context ctx) throws IOException, InterruptedException {
            long blockIdx = key.getI();
            for (int i = 0; i < value.getVectorElemValues().size(); i++) {
                long component = value.getVectorElemValues().get(i);
                if (component >= 0) {
                    KEY.set(blockSize * blockIdx + i);
                    VALUE.set(component);
                    ctx.write(KEY, VALUE);
                }
            }
        }
    }
}
