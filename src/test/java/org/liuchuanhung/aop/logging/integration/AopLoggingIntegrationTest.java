package org.liuchuanhung.aop.logging.integration;

import ch.qos.logback.classic.spi.LoggingEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.liuchuanhung.aop.logging.appender.TestAppender;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AopLoggingIntegrationTest {

    @LocalServerPort
    private String port;

    private TestRestTemplate restTemplate;

    @Before
    public void init() {
        restTemplate = new TestRestTemplate();
        TestAppender.setLoggingEvents(new ArrayList<>());
    }

    @Test
    public void greetingTest() {
        String name = "Ryan";
        String response = this.restTemplate.getForObject(
                "http://localhost:{0}/dummy?name={1}", String.class, port, name);
        assertEquals("Hello Ryan!", response);
    }

    @Test
    public void loggableAspect_logsBasicRequestInformationTest() {
        String name = "Ryan";
        String response = this.restTemplate.getForObject(
                "http://localhost:{0}/dummy?name={1}", String.class, port, name);
        assertEquals("Hello Ryan!", response);
        List<LoggingEvent> events = TestAppender.getLoggingEvents();
        assertTrue(events.stream().anyMatch(event -> event.getFormattedMessage().equals("Return Value: Hello Ryan!")));
        assertTrue(events.stream().anyMatch(event -> event.getFormattedMessage().equals("Return Type: java.lang.String")));
        assertTrue(events.stream().anyMatch(event -> event.getFormattedMessage().equals("Method Argument(s): [{class java.lang.String=Ryan}]")));
        assertTrue(events.stream().anyMatch(event -> event.getFormattedMessage().equals("Calling Method: greeting")));
        assertTrue(events.stream().anyMatch(event -> event.getFormattedMessage().equals("Calling Class: org.liuchuanhung.aop.logging.controller.DummyController")));
    }
}