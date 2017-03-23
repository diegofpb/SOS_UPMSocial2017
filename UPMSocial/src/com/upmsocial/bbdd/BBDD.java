package com.upmsocial.bbdd;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BBDD {

	public List<Map<String, Object>> ExecuteQuery(String query){

		Connection connection = null;
		Statement statement1 = null;
		ResultSet resultSet = null;
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
	    Map<String, Object> row = null;


		try {

			// STEP 1: Cargar el JDBC driver. //
			Class.forName("com.mysql.jdbc.Driver");
			

			// STEP 2: Establecer la conexión con una BD //
			String serverName = "diegofpb.asuscomm.com:3306";
			String mydatabase = "RestBBDD";
			String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
			String username = "upmsocialapi";
			String password = "dieguito1";

			connection = DriverManager.getConnection(url, username, password);
			
			// STEP 3: ACCEDER A DATOS Y CONSULTAS DE LA BASE DE DATOS //
			statement1 = connection.createStatement();
			resultSet = statement1.executeQuery(query);
			
			ResultSetMetaData metaData = resultSet.getMetaData();
		    Integer columnCount = metaData.getColumnCount();

		    while (resultSet.next()) {
		        row = new HashMap<String, Object>();
		        for (int i = 1; i <= columnCount; i++) {
		            row.put(metaData.getColumnName(i), resultSet.getObject(i));
		        }
		        resultList.add(row);
		    }

			// STEP 4: Liberar Recursos y cerrar conexión //
			statement1.close();
			connection.close();
			resultSet.close();
			
		
			
		// EXCEPCIONES EN JBDC //
			
		} catch (SQLException se) {
			// se.printStackTrace();
			System.out.println("\n");
			System.err.println("Mensaje error : " + se.getMessage());
			System.err.println("Codigo error : " + se.getErrorCode());
			System.err.println("Estado SQL : " + se.getSQLState());
			System.out.println("\n");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Se produjo un error inesperado: " + e.getMessage());
		}

		finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (statement1 != null)
					statement1.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultList;
		
		
	}


}
