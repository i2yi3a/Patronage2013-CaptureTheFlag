package com.blstream.patronage.ctf.common.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
 * This class is a representation of generic REST controller which works on CRUD logic model.
 */
public interface RestController<T, ID extends Serializable> {

    /**
     * This method creates a new document.
     * @param resource
     * @return T
     */
    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody T create(@RequestBody T resource);

    /**
     * This method updates an existing document.
     * @param id
     * @param resource
     * @return T
     */
    @RequestMapping(value = "{id}", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody T update(@PathVariable ID id, @RequestBody T resource);

    /**
     * This method finds all existing documents.
     * @return List
     */
    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody Iterable<T> findAll();

    /**
     * This method finds existing document based on id.
     * @param id
     * @return T
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody T findById(@PathVariable ID id);

    /**
     * This method removes existing document based on id.
     * @param id
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable ID id);
}
