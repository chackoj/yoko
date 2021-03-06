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

package org.apache.yoko.orb.OB;

//
// IDL:orb.yoko.apache.org/OB/TimeoutPolicy:1.0
//
/**
 *
 * The timeout policy. This policy can be used to specify the
 * default timeout for connection establishment and requests.
 * If an object also has <code>ConnectionTimeoutPolicy</code> or
 * <code>RequestTimeoutPolicy</code> set, those values have precedence.
 *
 * @see ConnectionTimeoutPolicy
 * @see RequestTimeoutPolicy
 *
 **/

public interface TimeoutPolicyOperations extends org.omg.CORBA.PolicyOperations
{
    //
    // IDL:orb.yoko.apache.org/OB/TimeoutPolicy/value:1.0
    //
    /**
     *
     * If an object has a <code>TimeoutPolicy</code> set and a
     * connection cannot be established or no response to a request
     * is available after <code>value</code> milliseconds, a
     * <code>CORBA::NO_RESPONSE</code> exception is raised.
     * The default value is <code>-1</code>, which means no timeout.
     *
     **/

    int
    value();
}
