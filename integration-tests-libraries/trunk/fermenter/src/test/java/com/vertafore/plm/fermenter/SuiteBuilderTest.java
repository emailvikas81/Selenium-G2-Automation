package com.vertafore.plm.fermenter;

import com.vertafore.plm.fermenter.fixtures.ErrorFixture;
import com.vertafore.plm.fermenter.fixtures.unittestfixtures.HappyPathSuiteBuilderTestFixture;
import com.vertafore.plm.fermenter.scanners.TestsScanner;
import junit.framework.TestSuite;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: deaconda
 * Date: 8/25/14
 * Time: 5:30 PM
 * To change this template use File | Settings | File Templates.
 */

public class SuiteBuilderTest {
    private static final double DELTA = 1e-15;
    private TestsScanner scannerForSuiteBuilderTest = new TestsScanner() {

        @Override
        public ArrayList<FixtureDefinition> getFixtureTestDefinitions() {
            ArrayList<FixtureDefinition> testDefinitions = new ArrayList<>();
            testDefinitions.add(buildFixtureDefinitionForHappyPath("0", "com.vertafore.plm.fermenter.fixtures.unittestfixtures.HappyPathSuiteBuilderTestFixture"));
            testDefinitions.add(buildFixtureDefinitionForOtherPaths("1", "com.vertafore.plm.fermenter.fixtures.unittestfixtures.ClassNotFoundExceptionTestFixture"));
            testDefinitions.add(buildFixtureDefinitionForOtherPaths("2", "com.vertafore.plm.fermenter.fixtures.unittestfixtures.NoSuchFieldExceptionSuiteBuilderTestFixture"));
            testDefinitions.add(buildFixtureDefinitionForOtherPaths("3", "com.vertafore.plm.fermenter.fixtures.unittestfixtures.IllegalArgumentExceptionSuiteBuilderTestFixture"));
            return testDefinitions;  //To change body of implemented methods use File | Settings | File Templates.
        }
        private FixtureDefinition buildFixtureDefinitionForHappyPath(String definitionIdentifier,String fixtureName){
            FixtureDefinition definition = new FixtureDefinition();
            definition.scenario = String.format("Scenario value %s", definitionIdentifier);
            definition.fixture = fixtureName;
            definition.description = String.format("Description value %s", definitionIdentifier);
            definition.tests.put("key1",String.format("StringValue%s-1",definitionIdentifier));
            definition.tests.put("key2",String.format("StringValue%s-2",definitionIdentifier));
            definition.tests.put("key3",String.format("StringValue%s-3",definitionIdentifier));
            definition.tests.put("key4", "true");
            definition.tests.put("key5", "123.4");
            definition.tests.put("key6", "567");
            definition.tests.put("key7", "09/02/2014");
            return definition;
        }
        private FixtureDefinition buildFixtureDefinitionForOtherPaths(String definitionIdentifier,String fixtureName){
            FixtureDefinition definition = new FixtureDefinition();
            definition.scenario = String.format("Scenario value %s", definitionIdentifier);
            definition.fixture = fixtureName;
            definition.description = String.format("Description value %s", definitionIdentifier);
            definition.tests.put("key1",String.format("StringValue%s-1",definitionIdentifier));
            definition.tests.put("key2",String.format("StringValue%s-2",definitionIdentifier));
            definition.tests.put("key3",String.format("StringValue%s-3",definitionIdentifier));
            return definition;
        }
    };

    @Test
    public void buildSuiteTest() throws Exception {
        SuiteBuilder testSuiteBuilder = new SuiteBuilder();
        HappyPathSuiteBuilderTestFixture testValid;
        ErrorFixture testError;
        TestSuite testSuite = testSuiteBuilder.buildSuite(scannerForSuiteBuilderTest);
        assertEquals(4, testSuite.countTestCases());

        testValid = (HappyPathSuiteBuilderTestFixture) testSuite.testAt(0);
        assertTrue(testValid.getClass().getName().contains("HappyPathSuiteBuilderTestFixture"));
        assertTrue(testValid.key3.equals("StringValue0-3"));
        assertEquals(true, testValid.key4);
        assertEquals(123.4, testValid.key5, DELTA);
        assertEquals(567,testValid.key6);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        assertEquals("09/02/2014",sdf.format(testValid.key7));

        testError = (ErrorFixture) testSuite.testAt(1);
        assertTrue(testError.getClass().getName().contains("ErrorFixture"));
        assertTrue(testError.getFailureMessage().contains("Unable to locate the fixture class to setup test:"));

        testError = (ErrorFixture) testSuite.testAt(2);
        assertTrue(testError.getClass().getName().contains("ErrorFixture"));
        assertTrue(testError.getFailureMessage().contains("Missing public property to setup test:"));

        testError = (ErrorFixture) testSuite.testAt(3);
        assertTrue(testError.getClass().getName().contains("ErrorFixture"));
        assertTrue(testError.getFailureMessage().contains("Trying to set property of incorrect type:"));
    }
}
