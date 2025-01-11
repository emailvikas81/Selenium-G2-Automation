package com.vertafore.plm.etl.testing;

import oracle.jdbc.pool.OracleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: bhandy
 * Date: 7/31/14
 * Time: 11:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionFactory {

    private static final ConnectionFactory FACTORY = new ConnectionFactory();

    private Map<String,DataSource> datasources = new HashMap<String,DataSource>();

    public Connection getConnection(String name) {
        Properties dataSourceProperties = ConfigFactory.getConfig();

        try {
            DataSource dataSource = datasources.get(name);
            if (dataSource == null) {
                datasources.put(name, dataSource = new OracleDataSource());
                ((OracleDataSource) dataSource).setURL(dataSourceProperties.getProperty(name + ".url"));
            }

            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }

        return null;
    }

    public static ConnectionFactory getFactory() {
        return FACTORY;
    }

}
