package com.vertafore.plm.fermenter.etl.util;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ConnectionFactoryTest {

    @Test
    public void getFactoryTest(){
        ConnectionFactory factory = ConnectionFactory.getFactory();
        assertTrue("Connection factory is returned", ConnectionFactory.class.isInstance(factory));
    }

    @Test
    public void getConnectionTest() throws SQLException {
        ConnectionFactory factory = ConnectionFactory.getFactory();
        Connection connection = factory.getConnection("SRC_AE");
        assertNotNull("Connection object", connection);
        assertTrue("Connection is open", !connection.isClosed());
    }

    @Test
    public void getConnectionInvalidTest() {
        ConnectionFactory factory = ConnectionFactory.getFactory();
        Connection connection = factory.getConnection("invalid");
        assertNull("Connection object", connection);
    }

}
