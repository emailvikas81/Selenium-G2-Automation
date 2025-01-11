package com.vertafore.plm.etl;

import com.vertafore.plm.etl.testing.ConfigFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bhandy
 * Date: 7/31/14
 * Time: 11:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommandLineRunner implements ETLRunner {

    public void executeProcess(List<String> arguments) {
        List<String> commandWithArguments;
        ProcessBuilder builder = new ProcessBuilder();

        commandWithArguments = buildCommandLineForProcess(arguments);

        builder.command(commandWithArguments.toArray(new String[commandWithArguments.size()]));
        builder.redirectErrorStream(true);

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
        String script = ConfigFactory.getConfig().getProperty("commandline.bat");
        String path = ConfigFactory.getConfig().getProperty("commandline.dir");
        Character commandLineWrapper;

        if(System.getProperty("os.name").startsWith("Windows")) {
            commandWithArguments.add("cmd.exe");
            commandWithArguments.add("/C");
            commandLineWrapper = '"';
        } else { //Assuming OS with bash
            commandWithArguments.add("bash");
            commandWithArguments.add("-c");
            commandLineWrapper = '\'';
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

}
