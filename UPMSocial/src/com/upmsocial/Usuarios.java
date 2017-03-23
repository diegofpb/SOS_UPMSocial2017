package com.upmsocial;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.upmsocial.bbdd.BBDD;


@Path("/usuarios")
public class Usuarios {
	
	@Context
	private UriInfo uriInfo;


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response sayPlainTextHello() throws SQLException {
		
		BBDD newQuery = new BBDD();
		List<Map<String, Object>> result = newQuery.ExecuteQuery("SELECT * FROM RestBBDD.USERS");
		
		String respuesta = "";
		
		for (Map<String, Object> map : result) {
			 
			respuesta = respuesta+(String) map.toString()+"\n";
		}
		
		
	    return Response.status(Response.Status.OK).entity(respuesta).
	    		header("Content Location", uriInfo.getAbsolutePath().toString()).build();

	}

}



