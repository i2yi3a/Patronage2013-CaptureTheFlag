package com.blstream.patronage.ctf.service.impl;

import com.blstream.patronage.ctf.common.service.CrudServiceImpl;
import com.blstream.patronage.ctf.model.PortalRole;
import com.blstream.patronage.ctf.repository.PortalRoleRepository;
import com.blstream.patronage.ctf.service.PortalRoleService;

import javax.inject.Inject;
import javax.inject.Named;

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
 * This is a implementation of PortalRoleService interface.
 *
 * @see com.blstream.patronage.ctf.service.PortalRoleService
 */
@Named("portalRoleService")
public class PortalRoleServiceImpl extends CrudServiceImpl<PortalRole, String, PortalRoleRepository> implements PortalRoleService {

    @Inject
    @Named("portalRoleRepository")
    @Override
    public void setRepository(PortalRoleRepository repository) {
        super.repository = repository;
    }

    /**
     * @see com.blstream.patronage.ctf.service.PortalRoleService#findByAuthority(String)
     */
    @Override
    public PortalRole findByAuthority(String authority) {
        return repository.findByAuthority(authority);
    }
}
