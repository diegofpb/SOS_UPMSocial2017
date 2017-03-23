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


@Path("/saluda/{nombre}/{apellidos}")
public class Party {
	
	@Context
	private UriInfo uriInfo;
	

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String saludoHtml(@PathParam("nombre") String n, 
	                         @PathParam("apellidos") String a) {
	    return "<html>" + "<title>" + "Hola montiel" + "</title>" + "<body><h1>" +
	            "Hola " + n + " " + a + " " +
	            "</h1></body></html> ";
	}
	
}



