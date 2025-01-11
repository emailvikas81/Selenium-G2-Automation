package com.vertafore.plm.etl.testing;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bhandy
 * Date: 7/31/14
 * Time: 11:42 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ETLTestRunner extends DBTestRunner {

    public abstract com.vertafore.plm.etl.ETLRunner getETLRunner();

    public void executeETL(List<String> args) {
        getETLRunner().executeProcess(args);
    }

}
