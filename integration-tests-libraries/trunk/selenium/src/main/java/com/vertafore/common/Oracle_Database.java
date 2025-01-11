package com.vertafore.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class Oracle_Database {
    private Connection connection;

    public Oracle_Database(String product) throws Exception{
        connection = connect(product);
    }

    public void executeUpdate(String sql) throws Exception{
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.commit();
    }

    public ResultSet executeSelect(String sql) throws Exception{
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery(sql);
        return results;
    }

    public Connection connect(String product) throws Exception{
        Class.forName("oracle.jdbc.OracleDriver");
        String host = Global_Common.getEnvironmentProperty("_oracle", product + ".test.host");
        String port = Global_Common.getEnvironmentProperty("_oracle", product + ".test.port");
        String sid = Global_Common.getEnvironmentProperty("_oracle", product + ".test.sid");
        String username = Global_Common.getEnvironmentProperty("_oracle", product + ".test.username");
        String password = Global_Common.getEnvironmentProperty("_oracle", product + ".test.username.password");
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":" + port + ":" + sid + "","" + username + "","" + password + "");
        return connection;
    }

    public void close() throws Exception{
        connection.close();
    }

} 