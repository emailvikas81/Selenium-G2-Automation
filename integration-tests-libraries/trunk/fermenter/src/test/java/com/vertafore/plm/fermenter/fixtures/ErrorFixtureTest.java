package com.vertafore.plm.fermenter.fixtures;

import junit.framework.AssertionFailedError;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 * User: deaconda
 * Date: 8/22/14
 * Time: 4:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class ErrorFixtureTest {
    @Test(expected = ClassNotFoundException.class)
    public void executeTestsTest_FailWithException() throws Exception {
        ErrorFixture classUnderTest = new ErrorFixture("test");
        ClassNotFoundException testException = new ClassNotFoundException();
        classUnderTest.setFailure(testException);
        classUnderTest.executeTest();
    }
    @Test
    public void executeTestsTest_FailWithMessage() throws Exception {
        ErrorFixture classUnderTest = new ErrorFixture("test");
        String testMessage = "testMessage for FailWithMessage()";
        classUnderTest.setFailure(testMessage);
        try {
            classUnderTest.executeTest();
            fail("Expected a Assertion Failed error");
        } catch (AssertionFailedError e){
            assertEquals("testMessage for FailWithMessage()", e.getMessage());
        }
    }

    @Test
     public void executeTestsTest_FailWithoutExceptionOrMessage() throws Exception {
        ErrorFixture classUnderTest = new ErrorFixture("test");
        try {
            classUnderTest.executeTest();
            fail("Expected a Assertion Failed error");
        } catch (AssertionFailedError e){
            assertEquals("Failed to setup test fixture", e.getMessage());
        }
    }

    @Test
    public void executeTestsTest_FailWithoutExceptionOrEmptyMessage() throws Exception {
        ErrorFixture classUnderTest = new ErrorFixture("test");
        String testMessage = "";
        classUnderTest.setFailure(testMessage);
        try {
            classUnderTest.executeTest();
            fail("Expected a Assertion Failed error");
        } catch (AssertionFailedError e){
            assertEquals("Failed to setup test fixture", e.getMessage());
        }
    }
}
