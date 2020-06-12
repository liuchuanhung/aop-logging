package org.liuchuanhung.aop.logging.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DummyControllerTest {

    private DummyController dummyController = new DummyController();

    @Test
    public void loggableTest() throws Throwable {
        String response = dummyController.greeting("dummyPath", "Ryan");
        assertEquals("Hello Ryan!", response);
    }
}