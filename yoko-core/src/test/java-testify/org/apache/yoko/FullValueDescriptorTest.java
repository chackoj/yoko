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

import acme.Loader;
import acme.Processor;
import acme.Widget;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.Servant;
import testify.bus.Bus;
import testify.jupiter.annotation.Tracing;
import testify.jupiter.annotation.iiop.ConfigureOrb;
import testify.jupiter.annotation.iiop.ConfigureServer;
import testify.jupiter.annotation.iiop.ConfigureServer.BeforeServer;
import testify.jupiter.annotation.iiop.ConfigureServer.Separation;
import testify.jupiter.annotation.logging.Logging;

import javax.rmi.CORBA.Tie;
import javax.rmi.CORBA.Util;
import javax.rmi.PortableRemoteObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;

import static testify.jupiter.annotation.iiop.ConfigureOrb.NameService.READ_WRITE;
import static testify.util.Throw.invokeWithImpunity;

@ConfigureServer(
        separation = Separation.INTER_ORB,
        serverOrb = @ConfigureOrb(nameService = READ_WRITE)
)
@Tracing
public class FullValueDescriptorTest {
    private static final Loader CLIENT_LOADER = Loader.V1;
    private static final Loader SERVER_LOADER = Loader.V2;

    private static final Class<? extends Widget> PAYLOAD_CLASS = CLIENT_LOADER.loadClass("versioned.SmallWidget");;
    private static final Constructor<? extends Widget> PAYLOAD_CONSTRUCTOR = invokeWithImpunity(PAYLOAD_CLASS::getConstructor);

    private static final NameComponent[] PROCESSOR_BIND_NAME = {new NameComponent("VersionedProcessor", "")};
    private static final Class<? extends Processor> TARGET_CLASS = CLIENT_LOADER.loadClass("versioned.VersionedProcessor");
    private static final Constructor<? extends Processor> TARGET_CONSTRUCTOR = invokeWithImpunity(TARGET_CLASS::getConstructor, Bus.class);
    private static Processor stub;

    @BeforeServer
    public static void initServer(ORB orb, Bus bus) throws Exception {
        bus.log("Got constructor for server target object");
        Processor target = TARGET_CONSTRUCTOR.newInstance(bus);
        bus.log("Created server target object");
        POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        rootPOA.the_POAManager().activate();

        PortableRemoteObject.exportObject(target);
        bus.log("Exported server target object");
        Tie tie = Util.getTie(target);
        System.out.println("About to activate object with root POA");
        rootPOA.activate_object((Servant)tie);
        bus.log("Activated object with root POA");

        NamingContext nc = NamingContextHelper.narrow(orb.resolve_initial_references("NameService"));
        nc.bind(PROCESSOR_BIND_NAME, tie.thisObject());
    }

    @BeforeAll
    public static void initClient(ORB orb, Bus bus) throws Exception {
        NamingContext nc = NamingContextHelper.narrow(orb.resolve_initial_references("NameService"));
        Object obj = nc.resolve(PROCESSOR_BIND_NAME);
        stub = (Processor)PortableRemoteObject.narrow(obj, TARGET_CLASS);
        bus.log("Narrowed stub");
    }

    @Test
    @Logging()
    public void testMarshallingAnAbstract() throws Exception {
        // Set the thread context class loader so that the return value can be unmarshalled
        Widget payload = PAYLOAD_CONSTRUCTOR.newInstance();
        Widget returned = stub.processAbstract(Widget::validateAndReplace, payload);
        returned.validateAndReplace();
    }

    @Test
    @Logging("yoko.verbose")
    public void testMarshallingAnAny() throws Exception {
        // Set the thread context class loader so that the return value can be unmarshalled
//        Widget payload = PAYLOAD_CONSTRUCTOR.newInstance();
//        Widget returned = stub.processAny(Widget::validateAndReplace, payload);
//        returned.validateAndReplace();
    }

    @Test
    @Logging("yoko.verbose")
    public void testMarshallingAValue() throws Exception {
        // Set the thread context class loader so that the return value can be unmarshalled
        Widget payload = PAYLOAD_CONSTRUCTOR.newInstance();
        Widget returned = stub.processValue(Widget::validateAndReplace, payload);
        returned.validateAndReplace();
    }

    @Test
    public void testSerializingAValue() throws Exception {
        // test that the validateAndReplace() methods work correctly when using normal serialization
        Widget w1 = PAYLOAD_CONSTRUCTOR.newInstance();
        Widget w2 = SERVER_LOADER.deserializeFromBytes(serializeToBytes(w1));
        w2 = w2.validateAndReplace();
        w1 = CLIENT_LOADER.deserializeFromBytes(serializeToBytes(w2));
        w1 = w1.validateAndReplace();
    }

    private byte[] serializeToBytes(Widget payload) throws IOException {
        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
        ) {
            oos.writeObject(payload);
            oos.flush();
            return baos.toByteArray();
        }
    }
}
