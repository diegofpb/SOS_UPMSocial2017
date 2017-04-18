package com.upmsocial;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import com.upmsocial.bbdd.BBDD;
import com.upmsocial.models.Friendship;


@Path("/users/{user_id}/friends")
public class Friends {
	
	@Context
	private UriInfo uriInfo;
	
	@Context
    Request request;
	
	@GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getFriends(@PathParam("user_id") String username,
    		@QueryParam("start") @DefaultValue("1") int start,
			@QueryParam("end") @DefaultValue("10") int end,
			@QueryParam("filter_by_name") String nameFilter) throws ClassNotFoundException, SQLException {
	
		
		BBDD bdconn = new BBDD();
		ResultSet res = bdconn.getFriends(username,start,end,nameFilter); 
		List<Friendship> Friendships = new ArrayList<Friendship>();
			
		while (res.next()){	
			Friendship Friendship = new Friendship();
			
			Friendship.setFriendship_id(res.getInt(1));
			Friendship.setId_user1(res.getString(2));
			Friendship.setId_user2(res.getString(3));	
			Friendships.add(Friendship);
		}
		
		if (Friendships.isEmpty()){
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		
		GenericEntity<List<Friendship>> entity = new GenericEntity<List<Friendship>>(Friendships) {};

	 	return Response.status(Response.Status.OK).entity(entity).build();
    }
	
	@POST
	@Path("/{username2}")
	public Response addFriend(@PathParam("user_id") String username1, @PathParam("username2") String username2) throws ClassNotFoundException, SQLException{
		BBDD bdconn = new BBDD();
		return bdconn.createFriendship(username1,username2,uriInfo);
	}
	
	@DELETE
	@Path("/{username2}")
	public Response deleteFriend(@PathParam("user_id") String username1, @PathParam("username2") String username2) throws ClassNotFoundException, SQLException{
		BBDD bdconn = new BBDD();
		// TODO: [ISSUE] No borra si la relación la ha creado tu amigo. Arreglar desde BBDD.
		return bdconn.deleteFriendship(username1,username2);
	}

	
	@GET
	@Path("/posts")
    @Produces(MediaType.APPLICATION_XML)
    public Response getFriendsPosts(@PathParam("user_id") String username,
    		@QueryParam("start") @DefaultValue("1") int start,
			@QueryParam("end") @DefaultValue("10") int end,
			@QueryParam("from") String d,
			@QueryParam("to") String h,
			@QueryParam("filter_by_text") String textFilter) throws ClassNotFoundException, SQLException {
	
		
		BBDD bdconn = new BBDD();
		ResultSet res = bdconn.getFriends(username,1,1000,null); 
		List<String> Friends = new ArrayList<String>();
		List<Posts> Posts = new ArrayList<Posts>();
			
		// Obtenemos en Friends una lista con todos los usernames de nuestros amigos.
		while (res.next()){	
			Friendship Friendship = new Friendship();
			Friendship.setFriendship_id(res.getInt(1));
			Friendship.setId_user1(res.getString(2));
			Friendship.setId_user2(res.getString(3));	
			
			if(Friendship.getId_user1().equals(username)){
				Friends.add(Friendship.getId_user2());
			}else{
				Friends.add(Friendship.getId_user1());
			}
		}
		
		// Si no tenemos amigos. Directamente devolvemos notfound.
		if (Friends.isEmpty()){
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		// Obtenemos todos los post de cada usuario, filtrado por el texto.
		for (String user : Friends) {
			
			//ResultSet res2 = bdconn.getPosts(user, 1, 1000, from, to, null); 

			
			System.out.println(user);
		}
		
		
		// Ordenamos con JAVA los posts .
		
		// Limitamos la salida de posts.
		
		
		// Devolvemos la lista limitada y filtrada.
	 	return Response.status(Response.Status.OK).entity(Posts).build();
    }
	
	

}



