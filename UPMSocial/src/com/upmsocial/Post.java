package com.upmsocial;

import java.sql.SQLException;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.upmsocial.bbdd.BBDD;
import com.upmsocial.models.TipoPost;
import com.upmsocial.models.User;

@Path("/post")
public class Post {
	
	@Context
	private UriInfo uriInfo;
	
	 @POST
	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 public Response addPost(
	         @FormParam("username") String username,
	         @FormParam("date") Date date,
	         @FormParam("url") String url,
	         @FormParam("description") String description) throws ClassNotFoundException, SQLException {
		 
		 BBDD con = new BBDD();
		 int id = con.newIdPost();
		 
		 TipoPost post = new TipoPost(id, username, date, url, description);
	     
	     return con.addPost(post,uriInfo);
	 }

}
