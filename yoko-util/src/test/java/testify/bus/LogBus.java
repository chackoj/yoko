/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package testify.bus;

import java.util.function.Consumer;

public interface LogBus extends EventBus {
    enum LogLevel implements TypeRef<String> {DEFAULT}

    default void log(String message) { log(LogLevel.DEFAULT, message); }

    default void log(LogLevel level, String message) { put(level, message); }

    default void enableLogging() { enableLogging(System.err::println); }

    default void enableLogging(Consumer<String> action) { enableLogging(LogLevel.DEFAULT, action); }

    default void enableLogging(LogLevel level, Consumer<String> action) { onMsg(level, action); }
}
