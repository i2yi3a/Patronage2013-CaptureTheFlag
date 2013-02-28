package com.blstream.patronage.ctf.service.impl;

import com.blstream.patronage.ctf.common.service.CrudServiceImpl;
import com.blstream.patronage.ctf.model.PortalUser;
import com.blstream.patronage.ctf.repository.PortalUserRepository;
import com.blstream.patronage.ctf.service.PortalUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * Date: 1/23/13
 *
 * This is a implementation of PortalUserService interface.
 *
 * @see com.blstream.patronage.ctf.service.PortalUserService
 */
@Named("portalUserService")
public class PortalUserServiceImpl extends CrudServiceImpl<PortalUser, String, PortalUserRepository> implements PortalUserService {

    private static final Logger logger = LoggerFactory.getLogger(PortalUserServiceImpl.class);

    @Inject
    @Named("portalUserRepository")
    @Override
    public void setRepository(PortalUserRepository repository) {
        super.repository = repository;
    }

    /**
     * @see com.blstream.patronage.ctf.service.PortalUserService#findByUsername(String)
     */
    @Override
    public PortalUser findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
