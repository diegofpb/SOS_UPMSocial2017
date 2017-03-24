package com.upmsocial;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;


import com.upmsocial.bbdd.BBDD;
import com.upmsocial.models.User;


@Path("/usuarios")
public class Usuarios {
	
	@Context
	private UriInfo uriInfo;
	
	@Context
    Request request;


	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<User> getUsers() throws ClassNotFoundException, SQLException  {
		
		BBDD bdconn = new BBDD();
		List<User> users = bdconn.getUsers();
	    return users;
	}
	
	@GET
	@Path("{username}")
    @Produces(MediaType.APPLICATION_XML)
	
    public Response getUser(@PathParam("username") String username) throws ClassNotFoundException, SQLException {
		
		BBDD bdconn = new BBDD();
		ResultSet res = bdconn.getUser(username); 
		
		User User = new User();
		 
		if (!res.next()) {        
			return Response.status(Response.Status.NOT_FOUND).build();
		}else{
			User.setName(res.getString(1));
			User.setSurname(res.getString(2));
			User.setUsername(res.getString(3));					
		}
		
        return Response.status(Response.Status.OK).entity(User)
        .header("Location", uriInfo.getAbsolutePath().toString()).build();
    }
	
	 @POST
	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 public Response addUser(
	         @FormParam("username") String username,
	         @FormParam("name") String name,
	         @FormParam("surname") String surname) throws ClassNotFoundException, SQLException {
	     User user = new User(name, surname, username);
	     BBDD con = new BBDD();
	     return con.addUser(user,uriInfo);
	 }
	
	@PUT
	@Path("{username}")
	@Consumes(MediaType.APPLICATION_XML)
    public Response putUser(JAXBElement<User> user) throws ClassNotFoundException, SQLException {
		
		User myuser = user.getValue();	
		
		if(myuser.getUsername() != uriInfo.getPathParameters().get(1).toString()){
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}
		
		BBDD bdconn = new BBDD();
		
        return bdconn.editUser(myuser,uriInfo);
    }
	
	

}



