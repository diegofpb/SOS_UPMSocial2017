package com.upmsocial;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import com.upmsocial.bbdd.BBDD;
import com.upmsocial.models.Post;
import com.upmsocial.models.User;

@Path("/posts")
public class Posts {

	@Context
	private UriInfo uriInfo;

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response addPost(JAXBElement<Post> post) throws ClassNotFoundException, SQLException {

		BBDD con = new BBDD();
		int id = con.newIdPost();

		Post mypost = post.getValue();

		return con.addPost(mypost,uriInfo);
	}
	
/*	<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	<Posts>
	    <Post>
	        <date_post>2017-03-26 14:08:17</date_post>
	        <description>Prueba POST</description>
	        <id>2</id>
	        <url>http://www.youtube.com</url>
	        <username>monty030</username>
	    </Post>
	</Posts>
*/
	
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_XML)

	public Response getPost(@PathParam ("username") String username, 
			@QueryParam("inicio") @DefaultValue("0") String i, 
			@QueryParam("cuantos") @DefaultValue("10") String q
			//@QueryParam("desde") @DefaultValue("null") Date d,
			//@QueryParam("hasta") @DefaultValue("null") Date h
			) throws ClassNotFoundException, SQLException {
		
		BBDD bdconn = new BBDD();
		int inicio = Integer.parseInt(i);
		int quant = Integer.parseInt(q);
		//java.sql.Date sqlDateDesde = new java.sql.Date(d.getTime());
		//java.sql.Date sqlDateHasta = new java.sql.Date(h.getTime());
		ResultSet res = bdconn.getPost(username, inicio, quant, null, null);
		Post Post = new Post();
		List<Post> myPost = new ArrayList<Post>();
		
		while (res.next()) {
				
				Post.setId(res.getInt(1));
				Post.setUsername(res.getString(2));
				Post.setDate_post(res.getDate(3));
				Post.setUrl(res.getString(4));
				Post.setDescription(res.getString(5));

				myPost.add(Post);

		}
		
		if (myPost.isEmpty()){
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		GenericEntity<List<Post>> entity = new GenericEntity<List<Post>>(myPost) {};
				
		return Response.status(Response.Status.OK).entity(entity)
				.header("Location", uriInfo.getAbsolutePath().toString()).build();
	}
	
/*	@GET
	@Path("/{username}")
	public List<Post> getxml(@PathParam("username") String username) 
			throws ClassNotFoundException, SQLException {
		BBDD bdconn = new BBDD();
		List<Post> xmlpost = bdconn.getXml(username);
		return xmlpost;
	}*/
	
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
