package com.upmsocial;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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

@Path("/users/{user_id}/posts")
public class Posts {

	@Context
	private UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_XML)

	public Response getPosts(@PathParam ("user_id") String username, 
			@QueryParam("start") @DefaultValue("1") int start, 
			@QueryParam("end") @DefaultValue("10") int end,
			@QueryParam("from") String d,
			@QueryParam("to") String h
			) throws ClassNotFoundException, SQLException {

		BBDD bdconn = new BBDD();
		ResultSet res;
		
		if (d != null && h!=null){
			Timestamp from = Timestamp.valueOf(d + (" 00:00:00"));
			Timestamp to = Timestamp.valueOf(h + (" 00:00:00"));
			res = bdconn.getPosts(username, start, end, from, to, null);

		}else if(d != null && h==null){
			Timestamp from = Timestamp.valueOf(d + (" 00:00:00"));
			Timestamp to = new Timestamp(System.currentTimeMillis());
			res = bdconn.getPosts(username, start, end, from, to, null);
			
		}else if(d == null && h !=null){
			Timestamp from = new Timestamp(0);
			Timestamp to = Timestamp.valueOf(h + (" 00:00:00"));
			res = bdconn.getPosts(username, start, end, from, to, null);
			
		}else{
			Timestamp from = new Timestamp(0);
			Timestamp to = new Timestamp(System.currentTimeMillis());
			res = bdconn.getPosts(username, start, end, from, to, null);
			
		}

		List<Post> myPost = new ArrayList<Post>();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

		while (res.next()) {
			
			Post Post = new Post();

			Post.setId(res.getInt(1));
			Post.setUsername(res.getString(2));
			Post.setDate_post(sdf.format(res.getTimestamp(3)));
			Post.setUrl(res.getString(4));
			Post.setDescription(res.getString(5));

			myPost.add(Post);

		}

		if (myPost.isEmpty()){
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		GenericEntity<List<Post>> entity = new GenericEntity<List<Post>>(myPost) {};

		return Response.status(Response.Status.OK).entity(entity).build();
	}

	@GET
	@Path("/{post_id}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getPost(@PathParam ("user_id") String username, 
			@PathParam("post_id") int id
			) throws ClassNotFoundException, SQLException {

		BBDD bdconn = new BBDD();
		ResultSet res;
		
		res = bdconn.getPost(username, id);
			
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Post Post = new Post();
		
		if (!res.next()) {        
			return Response.status(Response.Status.NOT_FOUND).build();
		}else{
			Post.setId(res.getInt(1));
			Post.setUsername(res.getString(2));
			Post.setDate_post(sdf.format(res.getTimestamp(3)));
			Post.setUrl(res.getString(4));
			Post.setDescription(res.getString(5));				
		}

		return Response.status(Response.Status.OK).entity(Post).build();
	}
	
	@GET
	@Path("/count")
	public Response countPosts(@PathParam ("user_id") String username,
			@QueryParam("from") String d,
			@QueryParam("to") String h
			) throws ClassNotFoundException, SQLException {

		BBDD bdconn = new BBDD();
		ResultSet res;
		
		if (d != null && h!=null){
			Timestamp from = Timestamp.valueOf(d + (" 00:00:00"));
			Timestamp to = Timestamp.valueOf(h + (" 00:00:00"));
			res = bdconn.countPosts(username, from, to);

		}else if(d != null && h==null){
			Timestamp from = Timestamp.valueOf(d + (" 00:00:00"));
			Timestamp to = new Timestamp(System.currentTimeMillis());
			res = bdconn.countPosts(username, from, to);
			
		}else if(d == null && h !=null){
			Timestamp from = new Timestamp(0);
			Timestamp to = Timestamp.valueOf(h + (" 00:00:00"));
			res = bdconn.countPosts(username, from, to);
			
		}else{
			Timestamp from = new Timestamp(0);
			Timestamp to = new Timestamp(System.currentTimeMillis());
			res = bdconn.countPosts(username, from, to);
			
		}

		if (!res.next()) {        
			return Response.status(Response.Status.NOT_FOUND).build();
		}else{
			return Response.status(Response.Status.OK).entity(res.getString(1))
					.header("Location", uriInfo.getAbsolutePath().toString()).build();				
		}

	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response addPost(JAXBElement<Post> post) throws ClassNotFoundException, SQLException {

		BBDD con = new BBDD();
		//int id = con.newIdPost();		¿No lo estas usando?

		Post mypost = post.getValue();

		return con.addPost(mypost,uriInfo);
	}

	@DELETE
	@Path("/{post_id}")
	public Response deletePost(@PathParam("user_id") String username,
			@PathParam("post_id") int id) throws ClassNotFoundException, SQLException {
					
			BBDD bdconn = new BBDD();
			return bdconn.deletePost(username,id);
	}
	
	@PUT
	@Path("/{post_id}")
	public Response putPost(JAXBElement<Post> post,
			@PathParam("user_id") String username,
			@PathParam("post_id") int id) throws ClassNotFoundException, SQLException {

		Post mypost = post.getValue();	
		mypost.setUsername(username); // Forzamos para que no nos pasen otro usuario.

		if(!username.equals(mypost.getUsername())){

			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}

		BBDD bdconn = new BBDD();

		return bdconn.editPost(mypost, id, uriInfo);
	}


}
