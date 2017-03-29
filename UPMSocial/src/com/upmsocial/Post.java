package com.upmsocial;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import com.upmsocial.bbdd.BBDD;
import com.upmsocial.models.TipoPost;
import com.upmsocial.models.User;

@Path("/posts")
public class Post {

	@Context
	private UriInfo uriInfo;

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response addPost(JAXBElement<TipoPost> post) throws ClassNotFoundException, SQLException {

		BBDD con = new BBDD();
		int id = con.newIdPost();

		TipoPost mypost = post.getValue();

		return con.addPost(mypost,uriInfo);
	}
	
/*	<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	<tipoPosts>
	    <tipo_post>
	        <date_post/>
	        <id>1</id>
	        <url></url>
	        <username>monty030</username>
	    </tipo_post>
	</tipoPosts>
*/
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_XML)

	public Response getPost(@PathParam ("username") String username, 
			@QueryParam("inicio") @DefaultValue("1") String i, 
			@QueryParam("cuantos") @DefaultValue("10") String q
			//@QueryParam("desde") @DefaultValue("null") Date d,
			//@QueryParam("hasta") @DefaultValue("null") Date h
			) throws ClassNotFoundException, SQLException {
		
		BBDD bdconn = new BBDD();
		int inicio = Integer.parseInt(i);
		int quant = Integer.parseInt(q);
		//java.sql.Date sqlDateDesde = new java.sql.Date(d.getTime());
		//java.sql.Date sqlDateHasta = new java.sql.Date(h.getTime());
		List<TipoPost> myposts = bdconn.getPost(username, inicio, quant, null, null);
				
		return Response.status(Response.Status.OK).entity(myposts)
				.header("Location", uriInfo.getAbsolutePath().toString()).build();
	}
	
	@GET
	@Path("/{username}")
	public List<TipoPost> getxml(@PathParam("username") String username) 
			throws ClassNotFoundException, SQLException {
		BBDD bdconn = new BBDD();
		List<TipoPost> xmlpost = bdconn.getXml(username);
		return xmlpost;
	}
	
	@GET
	@Path("/{username}/cont")
	public int contPost(@PathParam("username") String username
						//@QueryParam("desde") @DefaultValue("null") Date d,
						//@QueryParam("hasta") @DefaultValue("null") Date h
						) throws ClassNotFoundException, SQLException {
		return 0;
	}
	
	@DELETE
	@Path("/{username}")
	public Response deletePost(@PathParam("username") String username,
			@PathParam("id") int id) throws ClassNotFoundException, SQLException {
		
		BBDD bdconn = new BBDD();
		return bdconn.deletePost(id);
	}
	

}
