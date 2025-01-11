package com.vertafore.plm.etl;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bhandy
 * Date: 7/31/14
 * Time: 11:30 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ETLRunner {
    void executeProcess(List<String> list);
}