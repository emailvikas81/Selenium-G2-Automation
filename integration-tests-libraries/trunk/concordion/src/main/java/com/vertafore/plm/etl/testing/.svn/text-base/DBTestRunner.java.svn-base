package com.vertafore.plm.etl.testing;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class for the ConcordionRunner to use for retrieving data to validate.
 *
 * @author bhandy
 */
@RunWith(ConcordionRunner.class)
public class DBTestRunner {

    private static final Pattern PROPERTY_NOT_NULL_PATTERN = Pattern.compile("([^ ]+)");

    public Connection getConnection(String name) throws SQLException {
        ConnectionFactory factory = ConnectionFactory.getFactory();
        Connection connection = factory.getConnection(name);

        if (connection == null) {
            throw new RuntimeException(String.format("Could not retrieve connection for datasource %s", name));
        }

        return connection;
    }

    public Object deleteFromTable(String dataSourceName, String tableName) {
        Connection connection = null;
        Statement statement = null;
        boolean rollback = false;
        try {
            connection = getConnection(dataSourceName);
            statement = connection.createStatement();
            statement.executeUpdate(String.format("delete from %s", tableName));
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            rollback = connection != null;
        } finally {
            if (rollback) {
                rollbackConnection(connection);
            }

            close(statement);
            close(connection);
        }

        return new Object();
    }

    public Object insertData(String dataSourceName, String insertStatement, List parameters) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean rollback = false;

        try {
            connection = getConnection(dataSourceName);
            statement = connection.prepareStatement(insertStatement);

            setParametersOnStatement(statement, parameters);

            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            rollback = connection != null;
        } finally {
            if (rollback) {
                rollbackConnection(connection);
            }

            close(statement);
            close(connection);
        }

        return new Object();
    }

    public List<Map<String,Object>> getRows(String dataSourceName, String sqlStatement) {
        return getRows(dataSourceName, sqlStatement, Collections.emptyList());
    }

    public List<Map<String,Object>> getRows(String dataSourceName, String sqlStatement, List parameters) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Map<String,Object>> rows = new ArrayList<Map<String, Object>>();

        try {
            connection = getConnection(dataSourceName);
            statement = connection.prepareStatement(sqlStatement);

            setParametersOnStatement(statement, parameters);

            resultSet = statement.executeQuery(sqlStatement);
            List<String> columns = getColumnNames(resultSet);

            while (resultSet.next()) {
                Map<String,Object> row = new HashMap<String, Object>();
                for (String column : columns) {
                    row.put(column, resultSet.getObject(column));
                }

                rows.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }

        return rows;
    }

    public Object checkNotNull(Object value, String property) {
        if (! (value instanceof Map)) {
            throw new RuntimeException("Expected a Map as the value.");
        }
        Matcher propertyMatcher = PROPERTY_NOT_NULL_PATTERN.matcher(property);
        if (propertyMatcher.find()) {
            property = propertyMatcher.group(1).toLowerCase();
        }


        return ((Map) value).get(property) != null;
    }

    public Object executeStoredProcedure(String datasource, String sp_name, List args, String exceptionString) {
        return new Object();
    }

    private void setParametersOnStatement(PreparedStatement statement, List parameters) throws SQLException {
        int parameterIndex = 1;
        for (Object parameter : parameters) {
            statement.setObject(parameterIndex++, parameter);
        }
    }

    private List<String> getColumnNames(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        List<String> columns = new ArrayList<String>();

        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            columns.add(metaData.getColumnName(i).toLowerCase());
        }

        return columns;
    }

    private void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    private void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    private void close(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    private void rollbackConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

}
