package com.vertafore.plm.fermenter.fixtures;


import org.junit.Ignore;

@Ignore //Annotation to appease intellij when running unit tests
public class ErrorFixture extends TestFixture {

	private Exception failureException;
	private String failureMessage;
	
	public ErrorFixture(String name) {
		super(name);
	}

	@Override
	public void executeTest() throws Exception {
		if(failureException != null) {
			throw failureException;
		} else if(failureMessage != null && !"".equals(failureMessage)) {
			fail(failureMessage);
		} else {
			fail("Failed to setup test fixture");
		}
	}

	public void setFailure(Exception exception) {

		this.failureException = exception;
	}
	public void setFailure(String message) {
		this.failureMessage = message;
	}

    public String getFailureMessage(){
        return failureMessage;
    }

}
