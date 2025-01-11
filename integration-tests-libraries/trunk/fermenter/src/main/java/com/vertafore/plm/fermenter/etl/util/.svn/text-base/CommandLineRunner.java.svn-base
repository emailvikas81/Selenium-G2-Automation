package com.vertafore.plm.fermenter.etl.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommandLineRunner {
    private EnvironmentUtilities env = new EnvironmentUtilities();

    public void executeProcess(List<String> arguments) {
        List<String> commandWithArguments = buildCommandLineForProcess(arguments);
        ProcessBuilder builder = buildProcess(commandWithArguments);
        System.out.println(String.format("Running kitchen: %s",builder.command().toString()));
        try {
            Process shellScript = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(shellScript.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.err.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    public List<String> buildCommandLineForProcess(List<String> arguments) {
        List<String> commandWithArguments = new ArrayList<String>();
        String script = "";
        String path = env.getEnvironmentVariable("PDI_HOME");
        if (path == null) {
            throw new IllegalStateException("PDI_HOME environment variable is NULL");
        }
        Character commandLineWrapper;

        if(env.getSystemProperty("os.name").startsWith("Windows")) {
            commandWithArguments.add("cmd.exe");
            commandWithArguments.add("/C");
            commandLineWrapper = '"';
            script = "\\kitchen.bat";
        } else { //Assuming OS with bash
            commandWithArguments.add("bash");
            commandWithArguments.add("-c");
            commandLineWrapper = '\'';
            script = "/kitchen.sh";
        }

        // make sure the arguments are surrounded by quotes to prevent spaces in the argument for causing issues.
        StringBuilder command = new StringBuilder(String.format("%s%s",path,script));
        for (String argument : arguments) {
            argument = argument.trim();
            if (argument.length() > 0) {
                if (!argument.matches("'[^\"]+'")) {
                    argument = String.format(" %c%s%c", commandLineWrapper, argument, commandLineWrapper);
                }
                command.append(argument);
            }
        }

        commandWithArguments.add(command.toString());
        return commandWithArguments;
    }

    public ProcessBuilder buildProcess(List<String> commandWithArguments){
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(commandWithArguments.toArray(new String[commandWithArguments.size()]));
        builder.redirectErrorStream(true);
        return builder;
    }





}

