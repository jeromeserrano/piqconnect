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

public enum PiqConnectCounter {
    NUMBER_SELF_LOOP,
    NUMBER_BLOCK_VECTOR,
    NUMBER_BLOCK_MATRIX,
    NUMBER_FINAL_VECTOR,
    NUMBER_INCOMPLETE_VECTOR,
    ERROR_MISSING_SELF_VECTOR,
    ERROR_NO_INITIAL_VECTOR
}
