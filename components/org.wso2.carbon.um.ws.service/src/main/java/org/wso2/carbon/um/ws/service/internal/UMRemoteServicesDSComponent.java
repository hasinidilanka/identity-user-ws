/*
 * Copyright (c) 2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.um.ws.service.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.wso2.carbon.registry.core.service.RegistryService;
import org.wso2.carbon.user.core.service.RealmService;

@Component(
        name = "remote.um.api.component",
        immediate = true)
public class UMRemoteServicesDSComponent {

    private static final Log log = LogFactory.getLog(UMRemoteServicesDSComponent.class);

    private static final String ACTIVATION_MESSAGE = "Remote User Mgt bundle is activated ";

    private static final String UNSETTING_MESSAGE = "Unsetting the Realm service ";

    public static RegistryService getRegistryService() {

        return UMRemoteServicesDataHolder.getInstance().getRegistryService();
    }

    @Reference(
            name = "registry.service",
            service = org.wso2.carbon.registry.core.service.RegistryService.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unsetRegistryService")
    protected void setRegistryService(RegistryService registryService) {

        UMRemoteServicesDataHolder.getInstance().setRegistryService(registryService);
    }

    public static RealmService getRealmService() {

        return UMRemoteServicesDataHolder.getInstance().getRealmService();
    }

    @Reference(
            name = "user.realmservice.default",
            service = org.wso2.carbon.user.core.service.RealmService.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unsetRealmService")
    protected void setRealmService(RealmService realmService) {

        if (log.isDebugEnabled()) {
            log.debug("Setting the Realm Service");
        }
        UMRemoteServicesDataHolder.getInstance().setRealmService(realmService);
    }

    @Activate
    protected void activate(ComponentContext ctxt) {

        log.debug(ACTIVATION_MESSAGE);
    }

    @Deactivate
    protected void deactivate(ComponentContext ctxt) {

        return;
    }

    protected void unsetRealmService(RealmService realmService) {

        if (log.isDebugEnabled()) {
            log.debug(UNSETTING_MESSAGE);
        }
        UMRemoteServicesDataHolder.getInstance().setRealmService(null);
    }

    protected void unsetRegistryService(RegistryService registryService) {

        UMRemoteServicesDataHolder.getInstance().setRegistryService(null);
    }
}
