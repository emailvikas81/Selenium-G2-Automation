package com.vertafore.plm.fermenter.etl.util;

//import oracle.jdbc.pool.OracleDataSource;

import oracle.jdbc.pool.OracleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
