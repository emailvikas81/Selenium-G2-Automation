package com.vertafore.plm.fermenter.fixtures.unittestfixtures;

import com.vertafore.plm.fermenter.fixtures.TestFixture;
import org.junit.Ignore;

import java.util.Date;

@Ignore
public class HappyPathSuiteBuilderTestFixture extends TestFixture {

	private String key1;
	private String key2;
	public String key3;
    public boolean key4;
    public double key5;
    public int key6;
    public Date key7;

	public HappyPathSuiteBuilderTestFixture(String name) {
		super(name);
	}

	@Override
	public void executeTest() throws Exception {

	}
}
