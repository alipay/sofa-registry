/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.sofa.registry.server.meta.revision;

import com.alipay.sofa.registry.core.model.AppRevisionRegister;
import com.alipay.sofa.registry.core.model.AppRevisionKey;
import com.alipay.sofa.registry.store.api.annotation.RaftReference;

import java.util.List;

public class AppRevisionRegistry {
    @RaftReference
    private AppRevisionService appRevisionService;

    public void register(AppRevisionRegister appRevision) {
        if (appRevisionService.existed(appRevision.appname, appRevision.revision)) {
            return;
        }
        appRevisionService.add(appRevision);
    }

    public List<AppRevisionKey> checkRevisions(String keysDigest) {
        if (keysDigest.equals(appRevisionService.getKeysDigest())) {
            return null;
        }
        return appRevisionService.getKeys();
    }

    public List<AppRevisionRegister> fetchRevisions(List<AppRevisionKey> keys) {
        return appRevisionService.getMulti(keys);
    }
}