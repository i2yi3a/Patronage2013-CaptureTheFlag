package com.blstream.patronage.ctf;

import com.blstream.patronage.ctf.model.SimpleModel;
import com.blstream.patronage.ctf.repository.SimpleRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

import static org.junit.Assert.assertNotNull;

/**
 * User: mkr
 * Date: 1/16/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class SimpleModelInitializerTest {

    private static final Logger logger = LoggerFactory.getLogger(SimpleModelInitializerTest.class);

    @Inject
    @Named("simpleRepository")
    private SimpleRepository repository;

    private SimpleModel simpleModel;

    @Before
    @Transactional
    public void init() {
        simpleModel = new SimpleModel("Willson");
        simpleModel = repository.save(simpleModel);

        logger.debug(String.format("Simple model was created with id: %s", simpleModel.getId()));

        assertNotNull(simpleModel);
    }

//    @After
    @Transactional
    public void cleanUp() {
        assertNotNull(simpleModel);
        repository.delete(simpleModel);

        logger.debug(String.format("Simple model was removed from db with id: %s", simpleModel.getId()));
    }

    @Test
    public void checkModelTest() {
        assertNotNull(simpleModel);
        assertNotNull(simpleModel.getName());

        logger.debug(String.format("Simple model name: %s", simpleModel.getName()));
    }
}
