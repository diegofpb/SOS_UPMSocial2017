package com.upmsocial;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/usuarios")
public class Usuarios {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response sayPlainTextHello() {
		String respuesta = "Hello Jersey";
	    return Response.status(Response.Status.OK).entity(respuesta).build();

	}

}

