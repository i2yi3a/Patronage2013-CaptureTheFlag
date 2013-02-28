package com.blstream.patronage.ctf.common.service;

import java.io.Serializable;
import java.util.List;

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
 * This class is a representation of a generic CRUD service.
 * C - Create
 * R - Read
 * U - Update
 * D - Delete
 *
 * Generic types like T nad ID are: document and id of this document.
 */
public interface CrudService<T, ID extends Serializable> {

    /**
     * This method creates new document.
     * @param resource
     * @return T
     */
    T create(T resource);

    /**
     * This method updates existing document.
     * @param id
     * @param resource
     * @return T
     */
    T update(ID id, T resource);

    /**
     * This method removes permanently existing document.
     * @param id
     */
    void delete(ID id);

    /**
     * This method removes permanently all existing documents.
     * If cascadeMode is TRUE, then all dependencies will be also removed.
     * @param cascadeMode
     */
    void deleteAll(boolean cascadeMode);

    /**
     * This method finds document based on ID.
     * @param id
     * @return T
     */
    T findById(ID id);

    /**
     * This method finds all documents.
     * @return List
     */
    List<T> findAll();

    /**
     * This method returns a count of all documents.
     * @return Long
     */
    Long count();
}
