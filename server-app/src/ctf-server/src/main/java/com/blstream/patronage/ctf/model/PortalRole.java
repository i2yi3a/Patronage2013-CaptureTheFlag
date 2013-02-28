package com.blstream.patronage.ctf.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * Copyright 2013 BLStream
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * User: lahim
 * Date: 1/22/13
 *
 * This class is a representation of portal authority object. It's holds all security
 * roles for portal user object.
 *
 * @see com.blstream.patronage.ctf.model.PortalUser
 */
@Document
public class PortalRole implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = -6176379095019368456L;

    @Id
    private String authority;


    public PortalRole() {
    }

    @PersistenceConstructor
    public PortalRole(String authority) {
        super();
        this.authority = authority;
    }

    /**
     * Sets a role name.
     * @param authority
     */
    public void setAuthority(String authority) {
        this.authority = authority;
    }

    /**
     * Gets a role name.
     * @return
     */
    @Override
    public String getAuthority() {
        return authority;
    }
}
