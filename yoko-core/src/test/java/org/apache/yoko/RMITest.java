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
package org.apache.yoko;

import test.rmi.ClientMain;
import test.rmi.ServerMain;

import java.nio.file.Files;
import java.nio.file.Paths;

public class RMITest extends AbstractOrbTestBase {
    private static final String REF_FILE = "Sample.ref";

    public void testRMI() throws Exception {
        server.launch();
        Files.deleteIfExists(Paths.get(REF_FILE));
        server.invokeMainAsync(ServerMain.class);
        setWaitForFile(REF_FILE);
        waitForFile();
        client.invokeMain(ClientMain.class);
        server.exit(0);
    }
}
