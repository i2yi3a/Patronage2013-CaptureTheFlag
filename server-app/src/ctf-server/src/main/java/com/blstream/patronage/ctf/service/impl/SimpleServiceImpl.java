package com.blstream.patronage.ctf.service.impl;

import com.blstream.patronage.ctf.common.service.CrudServiceImpl;
import com.blstream.patronage.ctf.model.SimpleModel;
import com.blstream.patronage.ctf.repository.SimpleRepository;
import com.blstream.patronage.ctf.service.SimpleService;

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
 * User: mkr
 * Date: 1/16/13
 *
 * Example.
 *
 * TODO: remove this example class.
 */
@Named("simpleService")
public class SimpleServiceImpl extends CrudServiceImpl<SimpleModel, String, SimpleRepository> implements SimpleService {

    @Inject
    @Named("simpleRepository")
    @Override
    public void setRepository(SimpleRepository repository) {
        super.repository = repository;
    }

    @Override
    public SimpleModel findByName(String name) {
        return repository.findByName(name);
    }
}
