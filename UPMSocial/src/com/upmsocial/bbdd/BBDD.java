package com.upmsocial.bbdd;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.upmsocial.models.User;
import com.upmsocial.models.Friendship;



public class BBDD {

	// Función para establecer conexión con BBDD.
	private Connection UPMConnection () throws ClassNotFoundException, SQLException{

		Class.forName("com.mysql.jdbc.Driver");

		String serverName = "diegofpb.asuscomm.com:3306";
		String mydatabase = "RestBBDD";
		String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
		String username = "upmsocialapi";
		String password = "dieguito1";

		return DriverManager.getConnection(url, username, password);
	}

	// Devuelve todos los usuarios.
	public List<User> getUsers() throws ClassNotFoundException, SQLException{

		List<User> Usuarios = new ArrayList<User>();

		Connection con = UPMConnection();
		Statement sta = con.createStatement();
		ResultSet res = sta.executeQuery("SELECT * FROM RestBBDD.USERS");


		while (res.next()) {

			User Usuario = new User();

			Usuario.setName(res.getString(1));
			Usuario.setSurname(res.getString(2));
			Usuario.setUsername(res.getString(3));

			Usuarios.add(Usuario);

		}

		return Usuarios;
	}

	// Devuelve un usuario.
	public ResultSet getUser (String username) throws ClassNotFoundException, SQLException {

		Connection con = UPMConnection();
		Statement sta = con.createStatement();
		ResultSet res = sta.executeQuery("SELECT * FROM RestBBDD.USERS WHERE USERS.username = '"+username+"';");

		return res;

	}

	// Crea un usuario
	public Response addUser (User user, UriInfo uriInfo) throws ClassNotFoundException, SQLException{

		Connection con = UPMConnection();
		Statement sta = con.createStatement();
		try {

			// Verificamos que el Username no está vacó (Único obligatorio)
			if (user.getUsername()!=null){
				int res = sta.executeUpdate("INSERT INTO `RestBBDD`.`USERS` (`username`, `name`, `surname`)"
						+ " VALUES ('"+user.getUsername()+"', '"+user.getName()+"', '"+user.getSurname()+"');");
			}else{
				return Response.status(Response.Status.NOT_ACCEPTABLE).build();
			}



		} catch (SQLException e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}

		String uri = uriInfo.getAbsolutePath().toString() + "/" + user.getUsername();

		return Response.status(Response.Status.CREATED).header("Location", uri).build();
	}

	// Edita un usuario.
	public Response editUser (User user, UriInfo uriInfo) throws ClassNotFoundException, SQLException{

		Connection con = UPMConnection();
		Statement sta = con.createStatement();
		Statement sta2 = con.createStatement();


		ResultSet res = sta.executeQuery("SELECT * FROM RestBBDD.USERS WHERE USERS.username = '"+user.getUsername()+"';");

		if (!res.next()) {
			// Si no existe, lo creamos y devolvemos 201 con location,
			try {
				int res2  = sta2.executeUpdate("INSERT INTO `RestBBDD`.`USERS` (`username`, `name`, `surname`)"
						+ " VALUES ('"+user.getUsername()+"', '"+user.getName()+"', '"+user.getSurname()+"');");

			} catch (SQLException e) {
				System.out.println("INSERT INTO `RestBBDD`.`USERS` (`username`, `name`, `surname`)"
						+ " VALUES ('"+user.getUsername()+"', '"+user.getName()+"', '"+user.getSurname()+"');");
				return Response.status(Response.Status.NOT_ACCEPTABLE).build();
			}

			String uri = uriInfo.getAbsolutePath().toString() + "/" + user.getUsername();

			return Response.status(Response.Status.CREATED).header("Location", uri).build();


		}else{

			// Usuario existe. Modificamos datos y devolvemos 200 si OK.
			try {

				int res2 = sta2.executeUpdate("UPDATE `RestBBDD`.`USERS` SET"
						+ " `name`='"+user.getName()+"', `surname`='"+user.getSurname()+"' WHERE `username`='"+user.getUsername()+"';");

			} catch (SQLException e) {
				return Response.status(Response.Status.NOT_ACCEPTABLE).build();
			}


		}

		return Response.status(Response.Status.OK).build();

	}

	// Borra un usuario.
	public Response deleteUser (String username) throws ClassNotFoundException, SQLException{

		Connection con = UPMConnection();
		Statement sta = con.createStatement();
		Statement sta2 = con.createStatement();

		ResultSet res = sta.executeQuery("SELECT * FROM RestBBDD.USERS WHERE USERS.username = '"+username+"';");


		if (!res.next()) {
			// Si no existe, devolvemos 404.
			return Response.status(Response.Status.NOT_FOUND).build();

		}else{

			// Usuario existe. Borramos datos y devolvemos 200 si OK.
			try {

				int res2 = sta2.executeUpdate("DELETE FROM `RestBBDD`.`USERS` WHERE `username`='"+username+"';");

			} catch (SQLException e) {
				return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
			}

		}

		return Response.status(Response.Status.OK).build();
	}

	/* Funciones relacionadas con FRIENDSHIPS */

	// Crea una relación de amistad
	public Response createFriendship (String user1, String user2, UriInfo uriInfo) throws ClassNotFoundException, SQLException{

		Connection con = UPMConnection();
		Statement sta1 = con.createStatement();
		Statement sta2 = con.createStatement();
		Statement sta3 = con.createStatement();
		Statement sta4 = con.createStatement();

		ResultSet res1 = sta1.executeQuery("SELECT * FROM RestBBDD.USERS WHERE USERS.username = '"+user1+"';");
		ResultSet res2 = sta2.executeQuery("SELECT * FROM RestBBDD.USERS WHERE USERS.username = '"+user2+"';");


		if (!res1.next() || !res2.next()) {
			// Si no encuentra uno de los usuarios, damos fallo.
			return Response.status(Response.Status.NOT_FOUND).build();
		}else{

			ResultSet res3 = sta3.executeQuery("SELECT FRIENDS.friend_id FROM RestBBDD.FRIENDS WHERE"
					+ " (FRIENDS.id_user1 ='"+user1+"' AND FRIENDS.id_user2 = '"+user2+"')"
					+ " OR FRIENDS.id_user1 ='"+user2+"' AND FRIENDS.id_user2 = '"+user1+"';");

			if (!res3.next()) {
				// Si no encuentra la relacion de amistad, la creamos.
				int res4 = sta4.executeUpdate("INSERT INTO `RestBBDD`.`FRIENDS` (`id_user1`, `id_user2`)"
						+ " VALUES ('"+user1+"', '"+user2+"');");
			}else{
				return Response.status(Response.Status.FOUND).build();
			}

		}


		String uri = uriInfo.getAbsolutePath().toString();
		return Response.status(Response.Status.CREATED).header("Location", uri).build();
	}

	// Elimina una relación de amistad
	public Response deleteFriendship (String user1, String user2) throws ClassNotFoundException, SQLException{

		// DELETE FROM `RestBBDD`.`FRIENDS` WHERE `friend_id`='5';
		Connection con = UPMConnection();
		Statement sta = con.createStatement();
		Statement sta2 = con.createStatement();
		
		ResultSet res = sta.executeQuery("SELECT FRIENDS.friend_id FROM RestBBDD.FRIENDS WHERE"
				+ " FRIENDS.id_user1 ='"+user1+"' AND FRIENDS.id_user2 = '"+user2+"';");

		if (!res.next()) {
			// Si no existe, devolvemos 404 no existe dicha amistad.
			return Response.status(Response.Status.NOT_FOUND).build();
		}else{
			// Friendship existe. Borramos datos y devolvemos 200 si OK.
			try {
				int res2 = sta2.executeUpdate("DELETE FROM `RestBBDD`.`FRIENDS` WHERE `friend_id`='"+res.getString(1)+"';");
			} catch (SQLException e) {
				return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
			}

		}
		return Response.status(Response.Status.OK).build();
	}
	
	// Obtiene las relaciones de amistad en las que esta un usuario.
	public ResultSet getFriends (String username, int start, int end, String nameFilter) throws ClassNotFoundException, SQLException{
		
		Connection con = UPMConnection();
		Statement sta = con.createStatement();
		
		List<Friendship> Friendships = new ArrayList<Friendship>();

		if (nameFilter != null){
		
			ResultSet res = sta.executeQuery("SELECT * FROM RestBBDD.FRIENDS WHERE"
					+ " (FRIENDS.id_user1 = '"+username+"' OR FRIENDS.id_user2 = '"+username+"') AND (id_user2 LIKE '%"+nameFilter+"%' or id_user1 LIKE '%"+nameFilter+"%')"
					+ " LIMIT "+String.valueOf(end - start)+" OFFSET "+ String.valueOf(start) +";");
			return res;

		}else{
			ResultSet res = sta.executeQuery("SELECT * FROM RestBBDD.FRIENDS WHERE"
					+ " (FRIENDS.id_user1 ='"+username+"' OR FRIENDS.id_user2 = '"+username+"')"
							+ " LIMIT "+ String.valueOf(end - start) +" OFFSET "+ String.valueOf(start)+";");
			return res;

		}		
	}


}
