/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.yoko.osgi.locator;

public interface Register {
    void registerProvider(ServiceProvider provider);

    void unregisterProvider(ServiceProvider provider);

    @Deprecated
    void registerProvider(BundleProviderLoader bundleProviderLoader);

    @Deprecated
    void unregisterProvider(BundleProviderLoader bundleProviderLoader);

    void registerService(ServiceProvider provider);

    void unregisterService(ServiceProvider provider);

    @Deprecated
    void registerService(BundleProviderLoader bundleProviderLoader);

    @Deprecated
    void unregisterService(BundleProviderLoader bundleProviderLoader);

    void registerPackages(PackageProvider packageProvider);

    void unregisterPackages(PackageProvider packageProvider);
}
