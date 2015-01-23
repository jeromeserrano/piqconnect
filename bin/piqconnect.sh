##
# PIQConnect: Connected-component analysis for Big Graph
#
# __________.___________  _________                                     __
# \______   \   \_____  \ \_   ___ \  ____   ____   ____   ____   _____/  |_
#  |     ___/   |/  / \  \/    \  \/ /  _ \ /    \ /    \_/ __ \_/ ___\   __\
#  |    |   |   /   \_/.  \     \___(  <_> )   |  \   |  \  ___/\  \___|  |
#  |____|   |___\_____\ \_/\______  /\____/|___|  /___|  /\___  >\___  >__|
#                      \__>       \/            \/     \/     \/     \/
#
# Copyright (c) 2014 PlaceIQ, Inc
#
# This software is licensed under Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# ----------------------------------------------------------------------------
# Author: Jerome Serrano <jerome.serrano@placeiq.com>
# Date: 2015-01-09
# ----------------------------------------------------------------------------

JAR=/usr/local/piqconnect/piqconnect-1.0-SNAPSHOT-fatjar.jar

WORK_DIR=piqconnect_work

N_NODES=$1
N_REDUCERS_RUNNER=$2
N_REDUCERS_BLOCKS=$2
INPUT_EDGES=$3
BLOCK_SIZE=$4
MAX_CONVERGENCE=$5
MAX_ITERATIONS=$6

HADOOP_OPTS_RUNNER='-D mapred.map.child.java.opts=-Xmx2048M -D io.sort.mb=1024 -D io.sort.record.percent=0.7 -D mapred.job.reuse.jvm.num.tasks=-1'
HADOOP_OPTS_BLOCK="-D io.sort.mb=716 -D io.sort.record.percent=0.7 -D mapred.job.reuse.jvm.num.tasks=-1 -D mapred.job.reduce.input.buffer.percent=0.7"

HADOOP="hadoop jar $JAR"

hadoop dfs -rm -r $WORK_DIR

$HADOOP com.placeiq.piqconnect.InitialVectorGenerator $WORK_DIR/initial_vector $N_NODES $N_REDUCERS_BLOCKS
$HADOOP com.placeiq.piqconnect.BlocksBuilder $HADOOP_OPTS_BLOCK $WORK_DIR/initial_vector $WORK_DIR/vector_blocks $BLOCK_SIZE $N_REDUCERS_BLOCKS vector
$HADOOP com.placeiq.piqconnect.BlocksBuilder $HADOOP_OPTS_BLOCK $INPUT_EDGES $WORK_DIR/edge_blocks $BLOCK_SIZE $N_REDUCERS_BLOCKS matrix
$HADOOP com.placeiq.piqconnect.Runner $HADOOP_OPTS_RUNNER $WORK_DIR/edge_blocks $WORK_DIR/vector_blocks $WORK_DIR $N_REDUCERS_BLOCKS $BLOCK_SIZE $MAX_CONVERGENCE $MAX_ITERATIONS
