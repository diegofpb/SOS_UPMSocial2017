package com.upmsocial;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;


import com.upmsocial.bbdd.BBDD;
import com.upmsocial.models.Friendship;
import com.upmsocial.models.User;


@Path("/friends")
public class Friends {
	
	@Context
	private UriInfo uriInfo;
	
	@Context
    Request request;
	
	@GET
	@Path("/{username}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getFriends(@PathParam("username") String username) throws ClassNotFoundException, SQLException {
		
		BBDD bdconn = new BBDD();
		ResultSet res = bdconn.getFriends(username); 
		List<Friendship> Friendships = new ArrayList<Friendship>();
		
		if (!res.next()) {        
			return Response.status(Response.Status.NOT_FOUND).build();
		}else{
			while (res.next()){
				
				Friendship Friendship = new Friendship();
				Friendship.setFriend_id(res.getInt(1));
				Friendship.setId_user1(res.getString(2));
				Friendship.setId_user2(res.getString(3));	
				Friendships.add(Friendship);
			}
		}
		
		GenericEntity<List<Friendship>> entity = new GenericEntity<List<Friendship>>(Friendships) {};

	 	return Response.status(Response.Status.OK).entity(entity)
		        .header("Location", uriInfo.getAbsolutePath().toString()).build();
    }
	
	@POST
	@Path("/{username1}/{username2}")
	public Response addFriend(@PathParam("username1") String username1, @PathParam("username2") String username2) throws ClassNotFoundException, SQLException{
		BBDD bdconn = new BBDD();
		return bdconn.createFriendship(username1,username2,uriInfo);
	}
	
	@DELETE
	@Path("/{username1}/{username2}")
	public Response deleteFriend(@PathParam("username1") String username1, @PathParam("username2") String username2) throws ClassNotFoundException, SQLException{
		BBDD bdconn = new BBDD();
		return bdconn.deleteFriendship(username1,username2);
	}

	
	

}



