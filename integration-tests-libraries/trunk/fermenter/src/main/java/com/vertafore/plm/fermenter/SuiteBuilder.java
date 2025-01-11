package com.vertafore.plm.fermenter;

import com.vertafore.plm.fermenter.fixtures.ErrorFixture;
import com.vertafore.plm.fermenter.fixtures.TestFixture;
import com.vertafore.plm.fermenter.scanners.TestsScanner;
import junit.framework.TestSuite;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.AllTests;

import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@RunWith(AllTests.class)
@Ignore
public class SuiteBuilder {

	public TestSuite buildSuite(TestsScanner scanner) throws Exception {
		ArrayList<FixtureDefinition> fds = scanner.getFixtureTestDefinitions();

		TestSuite suite = new TestSuite();

		int count = 0;
		String lastFixtureName = null;
		for (FixtureDefinition fd : fds) {
			try {
				if(!fd.fixture.equals(lastFixtureName)) {
					lastFixtureName = fd.fixture;
					count = 0;
				}
				
				Class test = Class.forName(fd.fixture);
				Class[] paramTypes = new Class[] { String.class };
				Object[] paramValues = new String[] { fd.scenario + " " + count };
	
				Constructor con = test.getConstructor(paramTypes);
	
				Object testObj = con.newInstance(paramValues);

                ((TestFixture)testObj).setFixtureDefinition(fd);
	
				for (String fieldName : fd.tests.keySet()) {
					java.lang.reflect.Field field = test.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    if(field.getType().isPrimitive()) {
                        if(Boolean.TYPE == field.getType()) {
                            field.set(testObj, "true".equalsIgnoreCase(fd.tests.get(fieldName)));
                        } else if(Double.TYPE == field.getType()) {
                            field.set(testObj, Double.parseDouble(fd.tests.get(fieldName)));
                        } else if(Integer.TYPE == field.getType()) {
                            field.set(testObj, Integer.parseInt(fd.tests.get(fieldName)));
                        }
                    } else if(field.getType() == java.util.Date.class) {
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        field.set(testObj, sdf.parse(fd.tests.get(fieldName)));
                    } else {
                        field.set(testObj, fd.tests.get(fieldName));
                    }
                }
				suite.addTest((junit.framework.Test) testObj);
			} catch(ClassNotFoundException cnfe) {
				ErrorFixture error = new ErrorFixture(fd.scenario+ " "+ count);
				error.setFailure("Unable to locate the fixture class to setup test: "+fd.fixture);
				suite.addTest(error);
			} catch(NoSuchFieldException nsfe) {
                nsfe.printStackTrace();
				ErrorFixture error = new ErrorFixture(fd.scenario+ " "+ count);
				error.setFailure("Missing public property to setup test: "+nsfe.getMessage());
				suite.addTest(error);
			} catch(IllegalArgumentException iae) {
				ErrorFixture error = new ErrorFixture(fd.scenario+ " "+ count);
				error.setFailure("Trying to set property of incorrect type: "+iae.getMessage());
				suite.addTest(error);
			}
			count++;
		}

		return suite;
	}

}
