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
import javax.ws.rs.core.Response;

import dev.startupstack.identityservice.tenants.models.TenantModel;

/**
 * ObjectsService
 */
@Dependent
public interface TenantService {

    public Response getTenantInfo(String tenantID);

    public Response deleteTenant(String tenantID);

    public Response createTenant(String tenantName);

}