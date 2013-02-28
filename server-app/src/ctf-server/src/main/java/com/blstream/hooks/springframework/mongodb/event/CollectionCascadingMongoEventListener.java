package com.blstream.hooks.springframework.mongodb.event;

import com.blstream.hooks.springframework.mongodb.mapping.DBRefCollectionCascade;
import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.model.MappingException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.util.ReflectionUtils;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.Collection;

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
 * For more details please visit below website
 * @see <a href="http://maciejwalkowiak.pl/blog/2012/04/30/spring-data-mongodb-cascade-save-on-dbref-objects/">Spring Data MongoDB cascade save on DBRef objects</a>
 *
 * User: lahim
 * Date: 1/23/13
 */
public class CollectionCascadingMongoEventListener extends AbstractMongoEventListener {

    @Inject
    private MongoOperations mongoOperations;

    public void onBeforeConvert(final Object source) {
        ReflectionUtils.doWithFields(source.getClass(), new ReflectionUtils.FieldCallback() {

            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                ReflectionUtils.makeAccessible(field);

                if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(DBRefCollectionCascade.class)) {
                    final Object fieldValue = field.get(source);

                    if (fieldValue instanceof Collection<?>) {
                        Collection<?> collection = (Collection<?>) fieldValue;

                        for (Object collectionElement : collection) {
                            DbRefFieldCallback callback = new DbRefFieldCallback();

                            ReflectionUtils.doWithFields(collectionElement.getClass(), callback);

                            if (!callback.isIdFound()) {
                                throw new MappingException("Cannot perform cascade save on child object without id set");
                            }

                            mongoOperations.save(collectionElement);
                        }
                    }
                }
            }
        });
    }

    private static class DbRefFieldCallback implements ReflectionUtils.FieldCallback {
        private boolean idFound;

        public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
            ReflectionUtils.makeAccessible(field);

            if (field.isAnnotationPresent(Id.class)) {
                idFound = true;
            }
        }

        public boolean isIdFound() {
            return idFound;
        }
    }
}
