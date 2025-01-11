package com.vertafore.plm.fermenter.scanners;

import com.vertafore.plm.fermenter.FixtureDefinition;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

public class FileTestsScanner implements TestsScanner {

	private String regExPattern;

	public FileTestsScanner(){
		this(".*\\.tests");
	}
	public FileTestsScanner(String regExPattern){
		this.regExPattern = regExPattern;
	}
	public ArrayList<FixtureDefinition> getFixtureTestDefinitions() {
		ArrayList<FixtureDefinition> fds = new ArrayList<FixtureDefinition>();

		ArrayList<String> fixtureFiles = findFixtureTestFiles();
		for (String file : fixtureFiles) {
			fds.addAll(loadFixtureDefinition(file));
		}

		return fds;
	}

	private ArrayList<String> findFixtureTestFiles() {
		Pattern pattern = Pattern.compile(regExPattern);
		ArrayList<String> retval = new ArrayList<String>();
		String classPath = System.getProperty("java.class.path", ".");
		String[] classPathElements = classPath.split(System.getProperty("path.separator","."));
		for (String element : classPathElements) {
			File file = new File(element);
			if (file.isDirectory()) {
				retval.addAll(getResourcesFromDirectory(file, pattern));
			}
		}

		return retval;
	}

	private Collection<String> getResourcesFromDirectory(File directory, Pattern pattern) {
		ArrayList<String> retval = new ArrayList<String>();
		File[] fileList = directory.listFiles();
		for (File file : fileList) {
			if (file.isDirectory()) {
				retval.addAll(getResourcesFromDirectory(file, pattern));
			} else {
				try {
					String fileName = file.getCanonicalPath();
					boolean accept = pattern.matcher(fileName).matches();
					if (accept) {
						retval.add(fileName);
					}
				} catch (IOException e) {
					throw new Error(e);
				}
			}
		}
		return retval;
	}

	private ArrayList<FixtureDefinition> loadFixtureDefinition(String fileName) {
		ArrayList<FixtureDefinition> fds = new ArrayList<FixtureDefinition>();

		try (BufferedReader reader = Files.newBufferedReader(new File(fileName).toPath(), Charset.forName("US-ASCII"))) {
			String line = null;


			String scenario = null;
			String fixture = null;
			String description = null;
			while ((line = reader.readLine()) != null) {

				if (line.startsWith("Scenario:")) {
					scenario = line.substring(line.indexOf("Scenario:") + 9).trim();
				} else if(line.startsWith("Fixture:")) {
					fixture = line.substring(line.indexOf("Fixture:") + 8).trim();
				} else if(line.startsWith("Description:")) {
					description = "\n"+line.substring(line.indexOf("Description:") + 12).trim();

                    while ((line = reader.readLine()) != null && !line.startsWith("Tests:") ) {
                        description+=("\n"+line);
                    }
                    if(line.startsWith("Tests:")) {
                        readTests(reader, fds, scenario, fixture, description);
                    }
				}
			}

		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}

		return fds;
	}

    private void readTests(BufferedReader reader, ArrayList<FixtureDefinition> fds, String scenario, String fixture, String description) throws IOException {
        String[] testKeys = null;
        ArrayList<String[]> testValues = new ArrayList<String[]>();

        String line = null;
        if ((line = reader.readLine()) != null) {
            testKeys = line.split("\\|");
            while ((line = reader.readLine()) != null) {
                if("".equals(line)) {
                    break;
                }
                testValues.add(line.split("\\|"));
            }
        }

        int testValuesCount = 0;
        for (String[] testValue : testValues) {
            FixtureDefinition fd = new FixtureDefinition();
            fd.scenario = scenario;
            fd.fixture = fixture;
            fd.description = description;

            int count = 0;
            for (String key : testKeys) {
                fd.tests.put(key, testValues.get(testValuesCount)[count]);
                count++;
            }
            fds.add(fd);

            testValuesCount++;
        }

    }
}
