package com.vertafore.plm.fermenter.fixtures;

import com.vertafore.plm.fermenter.FixtureDefinition;
import junit.framework.TestCase;

public abstract class TestFixture extends TestCase	{
    private FixtureDefinition fixtureDefinition;
	
	public TestFixture(String name) {
		super(name);
	}
	@Override
	public void runTest() throws Exception {
		executeTest();
	}
    public String getFormattedDescription() {
        String formatted = fixtureDefinition.description;
        for(String key : fixtureDefinition.tests.keySet()) {
            formatted = formatted.replace("<"+key+">", fixtureDefinition.tests.get(key));
        }
        return formatted;
    }
	public abstract void executeTest() throws Exception;

    public void setFixtureDefinition(FixtureDefinition fixtureDefinition) {
        this.fixtureDefinition = fixtureDefinition;
    }
}
