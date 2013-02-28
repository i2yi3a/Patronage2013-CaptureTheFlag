package com.blstream.patronage.ctf.repository;

import com.blstream.patronage.ctf.model.SimpleModel;
import org.springframework.data.mongodb.repository.MongoRepository;

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
 * This class is a representation of MongoDB repository for all simple models.
 */
@Named("simpleRepository")
public interface SimpleRepository extends MongoRepository<SimpleModel, String> {

    /**
     * This method returns simple model object based on name.
     * @param name
     * @return SimpleModel
     */
    SimpleModel findByName(String name);
}
