package com.vertafore.plm.fermenter.etl.util;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;


public class CommandLineRunnerTest {
    @InjectMocks
    private CommandLineRunner commandLineRunner =  new CommandLineRunner();
    @Mock
    private EnvironmentUtilities mockEnv;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void buildCommandLineForProcessTest_Windows() {
        List<String> expectedCommandWithArguments = new ArrayList<String>();
        when(mockEnv.getSystemProperty("os.name")).thenReturn("Windows");
        when(mockEnv.getEnvironmentVariable("PDI_HOME")).thenReturn("pathToKitchen");
        expectedCommandWithArguments.add("cmd.exe");
        expectedCommandWithArguments.add("/C");
        expectedCommandWithArguments.add("pathToKitchen\\kitchen.bat \"-test\" \"-this\"");
        List<String> commandWithArguments = commandWithArguments();
        assertTrue("Command line program and arguments", commandWithArguments.equals(expectedCommandWithArguments));
    }

    @Test
    public void buildCommandLineForProcessTest_NotWindows() {
        List<String> expectedCommandWithArguments = new ArrayList<String>();
        when(mockEnv.getSystemProperty("os.name")).thenReturn("NotWindows");
        when(mockEnv.getEnvironmentVariable("PDI_HOME")).thenReturn("pathToKitchen");
        expectedCommandWithArguments.add("bash");
        expectedCommandWithArguments.add("-c");
        expectedCommandWithArguments.add("pathToKitchen/kitchen.sh \'-test\' \'-this\'");
        List<String> commandWithArguments = commandWithArguments();
        assertTrue("Command line program and arguments", commandWithArguments.equals(expectedCommandWithArguments));
    }

    @Test
    public void buildProcessTest() {
        when(mockEnv.getSystemProperty("os.name")).thenReturn("Windows");
        when(mockEnv.getEnvironmentVariable("PDI_HOME")).thenReturn("pathToKitchen");
        List<String> commandWithArguments = commandWithArguments();
        CommandLineRunner commandLineRunner =  new CommandLineRunner();
        ProcessBuilder builder = commandLineRunner.buildProcess(commandWithArguments);
        assertEquals("Builder has has three command arguments", builder.command().size(), 3);
        assertEquals("Builder has redirectErrorStream", builder.redirectErrorStream(), Boolean.TRUE);
    }

    @Test
    public void buildProcessWithNoPdiHomeEnvVariable() {
        when(mockEnv.getSystemProperty("os.name")).thenReturn("Windows");
        when(mockEnv.getEnvironmentVariable("PDI_HOME")).thenReturn(null);
        try{
            List<String> commandWithArguments = commandWithArguments();
            fail("Expected an IllegalStateException exception");
        } catch (IllegalStateException ise){
            assertEquals("PDI_HOME environment variable is NULL",ise.getMessage());
        }
    }

    private List<String> commandWithArguments(){
        List<String> arguments = new ArrayList<String>();
        arguments.add("-test");
        arguments.add("-this");
        List<String> commandWithArguments = commandLineRunner.buildCommandLineForProcess(arguments);
        return commandWithArguments;
    }
}
