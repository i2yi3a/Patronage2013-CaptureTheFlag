package com.blstream.patronage.ctf.service;

import com.blstream.patronage.ctf.common.service.CrudService;
import com.blstream.patronage.ctf.model.PortalRole;

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
 * This class is a representation of portal role service in CRUD logic model.
 *
 * @see com.blstream.patronage.ctf.common.service.CrudService
 */
public interface PortalRoleService extends CrudService<PortalRole, String> {

    /**
     * This method finds portal authority based on role name.
     * @param authority
     * @return PortalRole
     */
    PortalRole findByAuthority(String authority);
}
