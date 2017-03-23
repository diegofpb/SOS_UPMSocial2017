package com.upmsocial;

import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.upmsocial.bbdd.BBDD;
import com.upmsocial.models.TipoUser;


@Path("/usuarios")
public class Usuarios {
	
	@Context
	private UriInfo uriInfo;


	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<TipoUser> getUsers() throws SQLException, ClassNotFoundException {
		
		BBDD bdconn = new BBDD();
		List<TipoUser> users = bdconn.Usuarios();
		
	    return users;
	}

}



