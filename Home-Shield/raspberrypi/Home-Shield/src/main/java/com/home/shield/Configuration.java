/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.home.shield;

public class Configuration {
    public final static String SERIAL_PORT_SENDER = "/dev/ttyACM0";
    public final static String SERIAL_PORT_RECEIVER = "/dev/ttyACM1";
    public final static int SERIAL_BIT_RATE = 38400;
    public final static int PIR_BOUNCE_TIME = 5000;
    public final static int PIR_FIRST_WARN_DURATION = 5000;
    public final static int PIR_SECOND_WARN_DURATION = 10000;

}
