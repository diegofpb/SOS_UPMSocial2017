package com.upmsocial;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;


import com.upmsocial.bbdd.BBDD;
import com.upmsocial.models.Friendship;
import com.upmsocial.models.User;


@Path("/usuarios")
public class Usuarios {

	@Context
	private UriInfo uriInfo;

	@Context
	Request request;

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getUsers() throws ClassNotFoundException, SQLException  {

		List<User> Users = new ArrayList<User>();

		BBDD bdconn = new BBDD();
		ResultSet res = bdconn.getUsers();

		while (res.next()) {
			User Usuario = new User();

			Usuario.setName(res.getString(1));
			Usuario.setSurname(res.getString(2));
			Usuario.setUsername(res.getString(3));

			Users.add(Usuario);
		}

		if (Users.isEmpty()){
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		GenericEntity<List<User>> entity = new GenericEntity<List<User>>(Users) {};

		return Response.status(Response.Status.OK).entity(entity).build();

	}

	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_XML)

	public Response getUser(@PathParam("username") String username) throws ClassNotFoundException, SQLException {

		BBDD bdconn = new BBDD();
		ResultSet res = bdconn.getUser(username); 

		User User = new User();

		if (!res.next()) {        
			return Response.status(Response.Status.NOT_FOUND).build();
		}else{
			User.setUsername(res.getString(1));
			User.setName(res.getString(2));
			User.setSurname(res.getString(3));					
		}

		return Response.status(Response.Status.OK).entity(User)
				.header("Location", uriInfo.getAbsolutePath().toString()).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response addUser(JAXBElement<User> user) throws ClassNotFoundException, SQLException {

		User myuser = user.getValue();
		BBDD con = new BBDD();
		return con.addUser(myuser,uriInfo);
	}

	@PUT
	@Path("/{username}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response putUser(JAXBElement<User> user, @PathParam("username") String username) throws ClassNotFoundException, SQLException {

		User myuser = user.getValue();	

		if(!username.equals(myuser.getUsername())){

			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}

		BBDD bdconn = new BBDD();

		return bdconn.editUser(myuser,uriInfo);
	}

	@DELETE
	@Path("/{username}")
	public Response deleteUser(@PathParam("username") String username) throws ClassNotFoundException, SQLException {

		BBDD bdconn = new BBDD();

		return bdconn.deleteUser(username);
	}



}



