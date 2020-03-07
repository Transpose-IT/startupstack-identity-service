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

package dev.startupstack.identityservice.utils.keycloak;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;

import dev.startupstack.identityservice.tenants.models.TenantModel;
import dev.startupstack.identityservice.utils.WebResponseBuilder;


/**
 * KeycloakAdminService
 */
@Dependent
public class KeycloakAdminService {

    private static final Logger LOG = Logger.getLogger(KeycloakAdminService.class);

    @Inject
    @ConfigProperty(name = "quarkus.oidc.auth-server-url")
    String serverUrl;

    @Inject
    @ConfigProperty(name = "quarkus.oidc.client-id")
    String clientID;

    @Inject
    @ConfigProperty(name = "quarkus.oidc.credentials.secret")
    String clientSecret;

    @Inject
    @ConfigProperty(name = "startupstack.identityservice.keycloak.realm")
    String realmName;

    Keycloak keycloak;

    @PostConstruct
    void initialize() {
        this.keycloak = KeycloakBuilder.builder()
            .serverUrl(this.serverUrl)
            .clientId(this.clientID)
            .clientSecret(this.clientSecret)
            .realm(this.realmName)
            .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
            .build();
    }

    public Keycloak getAdminClient() {
        return this.keycloak;
    }

    void createRealm(String name) {

        RealmRepresentation newRealm = new RealmRepresentation();
        newRealm.setEnabled(true);
        newRealm.setRealm(name);
        newRealm.setDisplayName(name);
        this.keycloak.realms().create(newRealm);

    }

    /**
     * Creates a new keycloak group which represents a tenant in startupstack. 
     * 
     * @param tenantName The name of the tenant to create
     * @return A JAX-RS response object showing the result
     */
    public Response createTenant(String tenantName) {
        LOG.infof("Creating keycloak group for tenant '%s' ...", tenantName);

        GroupRepresentation group = new GroupRepresentation();
        group.setName(tenantName);

        Response response = this.keycloak.realm(realmName).groups().add(group);
        String groupID = KeycloakUtils.getCreatedId(response);
        this.keycloak.realm(realmName).groups().group(groupID).update(group);

        if (!response.getStatusInfo().equals(Status.CREATED)) {
            StatusType statusInfo = response.getStatusInfo();
            String message = "Got HTTP " + statusInfo.getStatusCode() + " - " + statusInfo.getReasonPhrase();

            LOG.errorf("Creating keycloak group for tenant '%s': FAILED - %s", tenantName, message);
            return WebResponseBuilder.build(message, response.getStatus());
        } 
        else {
            LOG.infof("Creating keycloak group for tenant '%s': OK", tenantName);
            return Response.status(Status.CREATED).build();
        }
    }

    public TenantModel getTenantByName(String tenantName) {
        for (GroupRepresentation group : this.keycloak.realm(realmName).groups().groups() ) {
            if (group.getName().equals(tenantName)) {
                return tenantModelFactory(group);
            }
        }
        return null;
    }
    public TenantModel getTenantByID(String tenantID) {
        for (GroupRepresentation group : this.keycloak.realm(realmName).groups().groups() ) {
            if (group.getId().equals(tenantID)) {
                return tenantModelFactory(group);
            }
        }
        return null;
    }
    TenantModel tenantModelFactory(GroupRepresentation group) {
        TenantModel tenant = new TenantModel();
        tenant.setTenantName(group.getName());
        tenant.setTenantID(group.getId());
        return tenant;
    }

}