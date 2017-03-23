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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.upmsocial.bbdd.BBDD;
import com.upmsocial.models.TipoUser;


@Path("/usuarios")
public class Usuarios {
	
	@Context
	private UriInfo uriInfo;


	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<TipoUser> getUsers() throws ClassNotFoundException, SQLException  {
		
		BBDD bdconn = new BBDD();
		List<TipoUser> users = bdconn.getUsers();
	    return users;
	}
	
	@GET
	@Path("{username}")
    @Produces(MediaType.APPLICATION_XML)
	
    public Response getUser(@PathParam("username") String username) throws ClassNotFoundException, SQLException {
		
		BBDD bdconn = new BBDD();
		ResultSet res = bdconn.getUser(username); 
		
		TipoUser User = new TipoUser();
		 
		if (!res.next()) {        
			return Response.status(Response.Status.BAD_REQUEST).build();
		}else{
			User.setNombre(res.getString(1));
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
	         @FormParam("nombre") String nombre,
	         @FormParam("surname") String surname) throws ClassNotFoundException, SQLException {
	     TipoUser user = new TipoUser(nombre, surname, username);
	     BBDD con = new BBDD();
	     return con.addUser(user,uriInfo);
	 }
	
	@PUT
	@Path("{username}")
    @Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
    public Response putUser(@PathParam("username") String username,
    		@PathParam("nombre") String nombre,
    		@PathParam("surname") String surname) throws ClassNotFoundException, SQLException {
		
		TipoUser User = new TipoUser();

		
		BBDD bdconn = new BBDD();
		ResultSet res = bdconn.editUser(User); 
		
		 
		if (!res.next()) {        
			return Response.status(Response.Status.BAD_REQUEST).build();
		}else{
			User.setNombre(res.getString(1));
			User.setSurname(res.getString(2));
			User.setUsername(res.getString(3));					
		}
		
        return Response.status(Response.Status.OK).entity(User)
        .header("Location", uriInfo.getAbsolutePath().toString()).build();
    }
	
	

}



