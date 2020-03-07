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

import static dev.startupstack.identityservice.Constants.TENANTS_URL;
import static dev.startupstack.identityservice.Constants.ROLE_TENANT_ADMIN;
import static dev.startupstack.identityservice.Constants.ROLE_TENANT_USER;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import dev.startupstack.identityservice.tenants.models.TenantModel;

@RequestScoped
@Path(TENANTS_URL)
@Tag(name = "tenant")
@RolesAllowed({ROLE_TENANT_ADMIN, ROLE_TENANT_USER})
public class TenantResource {

    @Inject
    TenantService tenantService;

    @GET
    @Operation(summary = "Fetches info and metadata of tenant")
    @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TenantModel.class)))
    @APIResponse(responseCode = "401", description = "No valid JWT token found")
    @APIResponse(responseCode = "403", description = "Not authorized to query this object")
    @APIResponse(responseCode = "404", description = "Object or repository not found")
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/tenant/{id}")
    public Response getTenantInfo(@NotBlank @PathParam("id") final String tenantID) {
        return tenantService.getTenantInfo(tenantID);
    }

    @POST
    @Operation(summary = "Creates a new tenant")
    @APIResponse(responseCode = "201", description = "Creation was successful")
    @APIResponse(responseCode = "401", description = "No valid JWT token found")
    @APIResponse(responseCode = "403", description = "Not authorized to upload to given repository")
    @APIResponse(responseCode = "404", description = "Repository not found")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/tenant")
    public Response createTenant(@NotBlank @PathParam("name") final String tenantName) {
        return tenantService.createTenant(tenantName);
    }

    @DELETE
    @Operation(summary = "Deletes object from a given repository")
    @APIResponse(responseCode = "204", description = "Delete was successful")
    @APIResponse(responseCode = "401", description = "No valid JWT token found")
    @APIResponse(responseCode = "403", description = "Not authorized to upload to given repository")
    @APIResponse(responseCode = "404", description = "Object or repository not found")
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/tenant/id")
    public Response deleteObject(@NotBlank @PathParam("id") final String tenantID) {
        return tenantService.deleteTenant(tenantID);
    }

}