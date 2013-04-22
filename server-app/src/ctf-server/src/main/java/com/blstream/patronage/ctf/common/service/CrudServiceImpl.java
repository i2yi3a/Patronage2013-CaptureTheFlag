package com.blstream.patronage.ctf.common.service;

import com.blstream.patronage.ctf.common.exception.AlreadyExistsException;
import com.blstream.patronage.ctf.common.exception.NotFoundException;
import com.blstream.patronage.ctf.model.BaseModel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
 * This class is a implementation of CrudService interface.
 *
 * @see com.blstream.patronage.ctf.common.service.CrudService
 */
public abstract class CrudServiceImpl<T extends BaseModel<ID>, ID extends Serializable, R extends PagingAndSortingRepository<T, ID>> implements CrudService<T, ID> {

    protected R repository;

    /**
     * This is an abstract method where repository will be set.
     * @param repository
     */
    protected abstract void setRepository(R repository);

    /**
     * @see com.blstream.patronage.ctf.common.service.CrudService#create(Object)
     */
    @Override
    @Transactional
    public T create(T resource) {
        Assert.notNull(resource, "Resource couldn't be null");

        if (resource.getId() != null && repository.exists(resource.getId()))
            throw new AlreadyExistsException(String.format("Resource with ID: %s already exists.", resource.getId()));

        return repository.save(resource);
    }

    /**
     * @see com.blstream.patronage.ctf.common.service.CrudService#update(java.io.Serializable, Object)
     */
    @Override
    @Transactional
    public T update(ID id, T resource) {
        Assert.notNull(id, "Resource ID couldn't be null");
        Assert.notNull(resource, "Resource couldn't be null");

        if (!repository.exists(id))
            throw new NotFoundException(String.format("Resource with ID: %s doesn't exist.", resource.getId()));

        return repository.save(resource);
    }

    /**
     * @see com.blstream.patronage.ctf.common.service.CrudService#delete(java.io.Serializable)
     */
    @Override
    @Transactional
    public void delete(ID id) {
        Assert.notNull(id, "Resource ID couldn't be null");

        if (!repository.exists(id))
            throw new NotFoundException(String.format("Resource with ID: %s doesn't exist.", id));

        repository.delete(id);
    }

    /**
     * @see com.blstream.patronage.ctf.common.service.CrudService#deleteAll(boolean)
     */
    @Override
    @Transactional
    public void deleteAll(boolean cascadeMode) {
        if (!cascadeMode) {
            repository.deleteAll();
        } else {
            Iterable<T> list = repository.findAll();
            for (T element : list) {
                repository.delete(element);
            }
        }
    }

    /**
     * @see com.blstream.patronage.ctf.common.service.CrudService#findById(java.io.Serializable)
     */
    @Override
    public T findById(ID id) {
        Assert.notNull(id, "Resource ID couldn't be null");

        if (!repository.exists(id))
            throw new NotFoundException(String.format("Resource with ID: %s doesn't exist.", id));

        return repository.findOne(id);
    }

    /**
     * @see com.blstream.patronage.ctf.common.service.CrudService#findAll()
     */
    @Override
    public List<T> findAll() {
        return (List<T>) repository.findAll();
    }

    /**
     * @see com.blstream.patronage.ctf.common.service.CrudService#count()
     */
    @Override
    public Long count() {
        return repository.count();
    }
}
