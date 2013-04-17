package com.blstream.patronage.ctf.common.web.controller;

import com.blstream.patronage.ctf.common.service.CrudService;
import com.blstream.patronage.ctf.model.BaseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

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
 * User: mkr
 * Date: 1/16/13
 *
 * This is a representation of base REST controller with CRUD logic model. It's an implementation
 * of RestController interface.
 *
 * @see com.blstream.patronage.ctf.common.web.controller.RestController
 * @see com.blstream.patronage.ctf.common.web.controller.AbstractRestController
 */
public abstract class BaseRestController<T extends BaseModel<ID>, ID extends Serializable, S extends CrudService<T, ID>> extends AbstractRestController implements RestController<T, ID> {

    private static Logger logger = LoggerFactory.getLogger(BaseRestController.class);

    protected S service;

    /**
     * This is an abstract method where service is set.
     * @param service
     */
    protected abstract void setService(S service);

    /**
     * @see com.blstream.patronage.ctf.common.web.controller.RestController#create(com.blstream.patronage.ctf.model.BaseModel)
     */
    @Override
    public T create(@RequestBody T resource) {
        if (logger.isDebugEnabled()) {
            logger.debug("---- create method invoked ----");
        }
        return service.create(resource);
    }

    /**
     * @see com.blstream.patronage.ctf.common.web.controller.RestController#create(com.blstream.patronage.ctf.model.BaseModel)
     */
    @Override
    public T update(@PathVariable ID id, @RequestBody T resource) {
        if (logger.isDebugEnabled()) {
            logger.debug("---- update method invoked ----");
            logger.debug(String.format("id: %s", id));
        }

        Assert.notNull(id, "ID cannot be null");
        Assert.notNull(resource, "Resources cannot be null");
        Assert.isTrue(id.equals(resource.getId()), "Request ID and resource ID cannot be different from each other");

        return service.update(id, resource);
    }

    /**
     * @see com.blstream.patronage.ctf.common.web.controller.RestController#findAll()
     */
    @Override
    public Iterable<T> findAll() {
        if (logger.isDebugEnabled()) {
            logger.debug("---- findAll method invoked ----");
        }

        return service.findAll();
    }

    /**
     * @see com.blstream.patronage.ctf.common.web.controller.RestController#findById(java.io.Serializable)
     */
    @Override
    public T findById(@PathVariable ID id) {
        if (logger.isDebugEnabled()) {
            logger.debug("---- findById method invoked ----");
            logger.debug(String.format("id: %s", id));
        }

        Assert.notNull(id, "ID cannot be null");
        return service.findById(id);
    }

    /**
     * @see com.blstream.patronage.ctf.common.web.controller.RestController#delete(java.io.Serializable)
     */
    @Override
    public void delete(@PathVariable ID id) {
        if (logger.isDebugEnabled()) {
            logger.debug("---- delete method invoked ----");
            logger.debug(String.format("id: %s", id));
        }

        Assert.notNull(id, "ID cannot be null");
        service.delete(id);
    }

    /**
     * This is a test method which always returns simple It's alive! text.
     * @return "It's alive!"
     */
    @RequestMapping("/isAlive")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('read')")
    public @ResponseBody String isAlive() {
        if (logger.isDebugEnabled()) {
            logger.debug("---- isAlive method invoked ----");
        }

        return "It's alive!";
    }
}
