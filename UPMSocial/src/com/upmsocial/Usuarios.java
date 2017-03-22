package com.upmsocial;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Path("/usuarios")
public class Usuarios {
	
	@Context
	private UriInfo uriInfo;


	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response sayPlainTextHello() {
		String respuesta = "Hello Jersey";
	    return Response.status(Response.Status.OK).entity(respuesta).
	    		header("Content Location", uriInfo.getAbsolutePath().toString()).build();

	}

}

