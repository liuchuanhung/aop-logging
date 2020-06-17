package org.liuchuanhung.aop.logging.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoggableAspectTest {

    @Mock
    private ProceedingJoinPoint proceedingJoinPoint;
    @Mock
    private MethodSignature signature;
    @Mock
    private JoinPoint.StaticPart staticPart;
    @Mock
    private JoinPoint joinPoint;

    private LoggableAspect loggableAspect = new LoggableAspect();

    @Test
    public void loggableTest() throws Throwable {
        when(proceedingJoinPoint.getStaticPart()).thenReturn(staticPart);
        when(staticPart.getSignature()).thenReturn(signature);
        when(signature.getDeclaringTypeName()).thenReturn("dummyType");
        when(signature.getName()).thenReturn("dummyName");
        when(proceedingJoinPoint.getSignature()).thenReturn(signature);
        when(signature.getParameterTypes()).thenReturn(new Class[0]);
        loggableAspect.loggable(proceedingJoinPoint);
        verify(proceedingJoinPoint, times(1)).proceed();
    }

    @Test
    public void afterReturningLoggable_resultIsNotNull_test() {
        String result = "result";
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getReturnType()).thenReturn("dummyReturnType".getClass());
        loggableAspect.afterReturningLoggable(joinPoint, result);
        verify(joinPoint, times(1)).getSignature();
    }

    @Test
    public void afterReturningLoggable_resultIsNull_test() {
        loggableAspect.afterReturningLoggable(joinPoint, null);
        verify(joinPoint, times(0)).getSignature();
    }
}