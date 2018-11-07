/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.triggle.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jose.alvarez
 */
public class SqlExpressions {

    private String DB_DRIVER = "org.postgresql.Driver";
    private String DB_CONNECTION = "jdbc:postgresql://dbpg-triggle-dvlp.axises.pri:5432/triggle";
    private String DB_USER = "triggle_app";
    private String DB_PASSWORD = "x21c2jzbet";

    private String strEnvironment = null;
    private String strQuery = null;
    private String strFields = null;
    private int strNumberFields = 0;

    public SqlExpressions() {
    }

    public Connection getDBConnection() {

        Connection dbConnection = null;

        try {

            Class.forName(DB_DRIVER);

            DriverManager.registerDriver(new org.postgresql.Driver());
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;

        } catch (SQLException | ClassNotFoundException e) {

            ManageMessages.printMessage(2, "Error executing login: " + e.toString(), this.getClass().getName());
            e.printStackTrace();

        }

        return dbConnection;

    }

    public String sqlExpressionResult() throws Exception {

        Connection dbConnection = null;
        Statement stmt = null;
        ResultSet rs = null;
        String result = "nok";
        int count = 0;

        strQuery = this.getQuery();
        strNumberFields = this.getNumberFields();
        strFields = this.getFields();

        try {

            if ("DVLP-TRIGGLE".equals(getEnvironment())) {

                DB_DRIVER = "org.postgresql.Driver";
                DB_CONNECTION = "jdbc:postgresql://dbpg-triggle-dvlp.axises.pri:5432/triggle";
                DB_USER = "triggle_app";
                DB_PASSWORD = "x21c2jzbet";
            }

            dbConnection = getDBConnection();

            if (strQuery.contains("UPDATE") || strQuery.contains("DELETE")) {

                stmt = dbConnection.createStatement();
                count = stmt.executeUpdate(strQuery);
                result = Integer.toString(count);


            } else {

                ManageMessages.printMessage(1, "Query  : " + strQuery, this.getClass().getName());
                stmt = dbConnection.createStatement();
                rs = stmt.executeQuery(strQuery);
               

                while (rs.next()) {
                    result = rs.getString(strFields);
                    ManageMessages.printMessage(1, "Query result  : " + result, this.getClass().getName());
                }
            }

        } catch (SQLException ex) {

            ManageMessages.printMessage(2, "Error executing login: " + ex.toString(), this.getClass().getName());
            ex.printStackTrace();

        } finally {

            try {

                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (dbConnection != null) {
                    dbConnection.close();
                }

            } catch (SQLException ex) {

                ManageMessages.printMessage(2, "Error executing login: " + ex.toString(), this.getClass().getName());
                ex.printStackTrace();
            }

        }

        return result;

    }

    public String getEnvironment() {
        return strEnvironment;
    }

    public void setEnvironment(String strEnvironment) {

        this.strEnvironment = strEnvironment;
    }

    public int getNumberFields() {
        return strNumberFields;
    }

    public void setNumberFields(int strNumberFields) {

        this.strNumberFields = strNumberFields;
    }

    public String getQuery() {
        return strQuery;
    }

    public void setQuery(String strQuery) {

        this.strQuery = strQuery;
    }

    public String getFields() {
        return strFields;
    }

    public void setFields(String strFields) {
        this.strFields = strFields;
    }

}
