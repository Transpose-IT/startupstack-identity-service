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
package dev.startupstack.identityservice.tenants.models;

import dev.startupstack.identityservice.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TenantInfoModel
 */
public class TenantModel {

    private String tenantName;
    private Map<String, List<String>> tenantAttributes;


    public Map<String,List<String>> getAllTenantAttributes() {
        return this.tenantAttributes;
    }

    public void setTenantAttribute(Map.Entry<String,List<String>> attribute) {
        this.tenantAttributes.put(attribute.getKey(), attribute.getValue());
    }

    public List<String> getTenantAttribute(String key) {
        return this.tenantAttributes.get(key);
    }

    
    public String getTenantName() {
        return this.tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantID() {
        List<String> value = this.tenantAttributes.get(Constants.METADATA_TENANT_ID);
        return value.get(0);
    }

    public void setTenantID(String tenantID) {
        List<String> value = new ArrayList<String>();
        value.add(tenantID);
        this.tenantAttributes.put(Constants.METADATA_TENANT_ID, value);
    }

}