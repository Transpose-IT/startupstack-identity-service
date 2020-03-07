/** 
* This file is part of startupstack.
* Copyright (c) 2020-2022, Transpose-IT B.V.
*
* Startupstack is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* Startupstack is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You can find a copy of the GNU General Public License in the
* LICENSE file.  Alternatively, see <http://www.gnu.org/licenses/>.
*/
package dev.startupstack.identityservice.tenants;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import dev.startupstack.identityservice.utils.WebResponseBuilder;
import dev.startupstack.identityservice.utils.keycloak.KeycloakAdminService;


@Dependent
public class TenantServiceKeycloakImpl implements TenantService {

    private static final Logger LOG = Logger.getLogger(TenantServiceKeycloakImpl.class);

    @Inject
    KeycloakAdminService keycloakAdminService;


    @Inject
    JsonWebToken jwt;

    @Override
    public Response getTenantInfo(String tenantID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response deleteTenant(String tenantID) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Creates a new Tenant, which is a group in keycloak with the attributes set accordingly. At this level we expect the tenantModel to 
     * have the right data filled in and we only have to save it to Keycloak.
     * 
     * @param tenant The TenantModel will all required values set
     * @return a JAX-RS Response model which either contains the error message or 
     */
    @Override
    public Response createTenant(String tenantName) {
        LOG.infof("Creating new tenant '%s' ...", tenantName);

        Response response = keycloakAdminService.createTenant(tenantName);

        if (!response.getStatusInfo().equals(Status.CREATED)) {
            StatusType statusInfo = response.getStatusInfo();
            String message = "Got HTTP " + statusInfo.getStatusCode() + " - " + statusInfo.getReasonPhrase();

            LOG.errorf("Creating new tenant '%s': FAILED - %s", tenantName, message);
            return WebResponseBuilder.build(statusInfo.getReasonPhrase(), statusInfo.getStatusCode());
        } else {
            return response;
        }
    }


}