package com.vertafore.plm.fermenter.fixtures;

import com.vertafore.plm.fermenter.FixtureDefinition;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;


/**
 * Created with IntelliJ IDEA.
 * User: deaconda
 * Date: 8/22/14
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestFixtureTest {

    @Test
    public void testGetFormattedDescription() {
        MyTestFixture testIt = new MyTestFixture("MyTestFixture");

        HashMap<String, String> values = new HashMap<String, String>();
        values.put("first", "123");
        values.put("second", "456");
        FixtureDefinition definition = new FixtureDefinition();
        definition.tests = values;
        definition.description = "Given I <first>, then <second>";
        testIt.setFixtureDefinition(definition);

        assertEquals("Given I 123, then 456", testIt.getFormattedDescription());
    }

    private class MyTestFixture extends TestFixture {
        public MyTestFixture(String name){super(name);}

        public String first;
        public String second;

        @Override
        public void executeTest(){}
    }
}
