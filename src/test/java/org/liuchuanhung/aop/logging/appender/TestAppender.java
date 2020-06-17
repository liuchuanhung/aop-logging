package org.liuchuanhung.aop.logging.appender;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.AppenderBase;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class TestAppender extends AppenderBase<LoggingEvent> {

    @Getter
    @Setter
    private static List<LoggingEvent> loggingEvents = new ArrayList<>();

    @Override
    protected void append(LoggingEvent e) {
        loggingEvents.add(e);
    }
}