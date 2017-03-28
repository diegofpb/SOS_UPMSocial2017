package com.upmsocial.bbdd;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.upmsocial.models.TipoPost;
import com.upmsocial.models.User;



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

	// Función para primer id libre post
	public int newIdPost() throws ClassNotFoundException, SQLException{

		Connection con = UPMConnection();
		Statement sta = con.createStatement();
		ResultSet res = sta.executeQuery("SELECT * FROM RestBBDD.POSTS");

		TipoPost Post = new TipoPost();

		while (res.next()) {

			Post.setId(res.getInt(1));
		}

		int i = Post.getId();

		return i++;


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
	public Response addUser(User user, UriInfo uriInfo) throws ClassNotFoundException, SQLException{

		Connection con = UPMConnection();
		Statement sta = con.createStatement();
		try {

			// Verificamos que el Username no está vacó (Único obligatorio)
			if (user.getUsername()!=null){
				int res = sta.executeUpdate("INSERT INTO `RestBBDD`.`USERS` (`username`, `nombre`, `surname`)"
						+ " VALUES ('"+user.getUsername()+"', '"+user.getName()+"', '"+user.getSurname()+"');");
			}else{
				return Response.status(Response.Status.NOT_ACCEPTABLE).build();
			}



		} catch (SQLException e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}

		String uri = uriInfo.getAbsolutePath().toString() + "usuarios/" + user.getUsername();

		return Response.status(Response.Status.CREATED).header("Location", uri).build();
	}

	// Edita un usuario.
	public ResultSet editUser (User user, UriInfo uriInfo) throws ClassNotFoundException, SQLException{

		Connection con = UPMConnection();
		try {
			Statement sta = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return null;
	}

	// Crea un post
	public Response addPost(TipoPost post, UriInfo uriInfo) throws ClassNotFoundException, SQLException{

		Connection con = UPMConnection();
		Statement sta = con.createStatement();
		try {

			int res = sta.executeUpdate("INSERT INTO `RestBBDD`.`POSTS` (`id`, `username`, "
					+ "`date_post`, `url`, `description`)"
					+ " VALUES ('"+post.getId()+"', '"+post.getUsername()+"', '"+post.getDate_post()+"', "
					+ "'"+post.getUrl()+"', '"+post.getDescription()+"');");

		} catch (SQLException e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}

		String uri = uriInfo.getAbsolutePath().toString() + "post/" + post.getUsername();

		return Response.status(Response.Status.CREATED).header("Location", uri).build();

	}

	// Listar post de un usuario
	public List<TipoPost> getPost(String username, int inicio, int cuantos,
			Date desde, Date hasta) throws ClassNotFoundException, SQLException{

		List<TipoPost> Posts = new ArrayList<TipoPost>();

		Connection con = UPMConnection();
		Statement sta = con.createStatement();

		TipoPost Post = new TipoPost();

		if(desde.equals(null) && hasta.equals(null)){
			ResultSet res = sta.executeQuery("SELECT * FROM RestBBDD.POSTS WHERE POSTS.username "
					+ "= '"+username+"'");
			while (res.next()) {

				for(int i=inicio; i<inicio+cuantos; i++){

					res.absolute(i);

					Post.setId(res.getInt(1));
					Post.setUsername(res.getString(2));
					Post.setDate_post(res.getDate(3));
					Post.setUrl(res.getString(4));
					Post.setDescription(res.getString(5));

					Posts.add(Post);
				}
			}
		}
		else if(!desde.equals(null) && hasta.equals(null)){
			ResultSet res = sta.executeQuery("SELECT * FROM RestBBDD.POSTS WHERE (POSTS.username "
					+ "= '"+username+"'" + "AND POSTS.date_post>= '"+desde+"' "
					+ "AND POSTS.date_post<= 'curdate()')");
			while (res.next()) {

				for(int i=inicio; i<inicio+cuantos; i++){

					res.absolute(i);

					Post.setId(res.getInt(1));
					Post.setUsername(res.getString(2));
					Post.setDate_post(res.getDate(3));
					Post.setUrl(res.getString(4));
					Post.setDescription(res.getString(5));

					Posts.add(Post);
				}
			}
		}
		else if(!desde.equals(null) && !hasta.equals(null)){
			ResultSet res = sta.executeQuery("SELECT * FROM RestBBDD.POSTS WHERE (POSTS.username "
					+ "= '"+username+"'" + "AND POSTS.date_post>= '"+desde+"' "
					+ "AND POSTS.date_post<= '"+hasta+"')");
			while (res.next()) {

				for(int i=inicio; i<inicio+cuantos; i++){

					res.absolute(i);

					Post.setId(res.getInt(1));
					Post.setUsername(res.getString(2));
					Post.setDate_post(res.getDate(3));
					Post.setUrl(res.getString(4));
					Post.setDescription(res.getString(5));

					Posts.add(Post);
				}
			}
		}
		else{
			ResultSet res = sta.executeQuery("SELECT * FROM RestBBDD.POSTS WHERE (POSTS.username "
					+ "= '"+username+"'" + "AND POSTS.date_post<= '"+hasta+"')");
			while (res.next()) {

				for(int i=inicio; i<inicio+cuantos; i++){

					res.absolute(i);

					Post.setId(res.getInt(1));
					Post.setUsername(res.getString(2));
					Post.setDate_post(res.getDate(3));
					Post.setUrl(res.getString(4));
					Post.setDescription(res.getString(5));

					Posts.add(Post);
				}
			}
		}
			
		return Posts;
	}
	
	// Borrar un post de un usuario
	public Response deletePost(int id)throws ClassNotFoundException, SQLException {
		
		Connection con = UPMConnection();
		Statement sta = con.createStatement();
		
		try {
			ResultSet comp = sta.executeQuery("SELECT * FROM RestBBDD.POSTS WHERE POST.id="
						+ "'"+id+"'");
			if (comp.next()){
				int res = sta.executeUpdate("DELETE FROM RestBBDD.POSTS WHERE POST.id="
						+ "'"+id+"'");
			}
			else
				return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		} catch (SQLException e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	
	// GET Xml
	public List<TipoPost> getXml(String username) throws ClassNotFoundException, SQLException {
		
		List<TipoPost> Posts = new ArrayList<TipoPost>();

		Connection con = UPMConnection();
		Statement sta = con.createStatement();
		ResultSet res = sta.executeQuery("SELECT * FROM RestBBDD.POSTS WHERE POSTS.username= '"+username+"'");
		//TipoPost Post = new TipoPost();
		TipoPost Xmlpost = new TipoPost();

		while (res.next()) {

			Xmlpost.setId(res.getInt(1));
			Xmlpost.setUsername(res.getString(2));
			Xmlpost.setDate_post(res.getDate(3));
			Xmlpost.setUrl(res.getString(4));
			Xmlpost.setDescription(res.getString(5));

			Posts.add(Xmlpost);

		}
		
		return Posts;
	}
	
}