package com.upmsocial;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.upmsocial.models.Post;

@Path("/users/{user_id}/friends")
public class Friends {

	@Context
	private UriInfo uriInfo;

	@Context
	Request request;

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getFriends(@PathParam("user_id") String username, @QueryParam("start") @DefaultValue("1") int start,
			@QueryParam("end") @DefaultValue("10") int end, @QueryParam("filter_by_name") String nameFilter)
					throws ClassNotFoundException, SQLException {

		BBDD bdconn = new BBDD();
		ResultSet res = bdconn.getFriends(username, start, end, nameFilter);
		List<Friendship> Friendships = new ArrayList<Friendship>();

		while (res.next()) {
			Friendship Friendship = new Friendship();

			Friendship.setFriendship_id(res.getInt(1));
			Friendship.setId_user1(res.getString(2));
			Friendship.setId_user2(res.getString(3));
			Friendships.add(Friendship);
		}

		if (Friendships.isEmpty()) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		GenericEntity<List<Friendship>> entity = new GenericEntity<List<Friendship>>(Friendships) {
		};

		return Response.status(Response.Status.OK).entity(entity).build();
	}

	@POST
	@Path("/{username2}")
	public Response addFriend(@PathParam("user_id") String username1, @PathParam("username2") String username2)
			throws ClassNotFoundException, SQLException {
		BBDD bdconn = new BBDD();
		return bdconn.createFriendship(username1, username2, uriInfo);
	}

	@DELETE
	@Path("/{username2}")
	public Response deleteFriend(@PathParam("user_id") String username1, @PathParam("username2") String username2)
			throws ClassNotFoundException, SQLException {
		BBDD bdconn = new BBDD();
		// TODO: [ISSUE] No borra si la relación la ha creado tu amigo. Arreglar
		// desde BBDD.
		return bdconn.deleteFriendship(username1, username2);
	}

	@GET
	@Path("/posts")
	@Produces(MediaType.APPLICATION_XML)
	public Response getFriendsPosts(@PathParam("user_id") String username,
			@QueryParam("start") @DefaultValue("1") int start, @QueryParam("end") @DefaultValue("10") int end,
			@QueryParam("from") String d, @QueryParam("to") String h, @QueryParam("text_to_search") String textToSearch)
					throws ClassNotFoundException, SQLException {

		BBDD bdconn = new BBDD();
		ResultSet res = bdconn.getFriends(username, 1, 1000, null);
		List<String> friends = new ArrayList<String>();
		List<Post> posts = new ArrayList<Post>();

		// Obtenemos en Friends una lista con todos los usernames de nuestros
		// amigos.
		while (res.next()) {
			Friendship Friendship = new Friendship();
			Friendship.setFriendship_id(res.getInt(1));
			Friendship.setId_user1(res.getString(2));
			Friendship.setId_user2(res.getString(3));

			if (Friendship.getId_user1().equals(username)) {
				friends.add(Friendship.getId_user2());
			} else {
				friends.add(Friendship.getId_user1());
			}
		}

		// Si no tenemos amigos. Directamente devolvemos notfound.
		if (friends.isEmpty()) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		// Obtenemos todos los post de cada usuario, filtrado por el texto.
		for (String user : friends) {

			ResultSet res2;

			if (d != null && h!=null){
				Timestamp from = Timestamp.valueOf(d + (" 00:00:00"));
				Timestamp to = Timestamp.valueOf(h + (" 00:00:00"));
				res2 = bdconn.getPosts(user, 1, 1000, from, to, textToSearch);

			}
			else if(d == null && h!=null){
				Timestamp to = Timestamp.valueOf(h + (" 00:00:00"));
				res2 = bdconn.getPosts(user, 1, 1000, null, to, textToSearch);
			}
			else if(d != null && h ==null){
				Timestamp from = Timestamp.valueOf(d + (" 00:00:00"));
				res2 = bdconn.getPosts(user, 1, 1000, from, null, textToSearch);
			}
			else
				res2 = bdconn.getPosts(user, 1, 1000, null, null, textToSearch);
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
				while (res2.next()) {
					Post post = new Post();
					post.setId(res2.getInt(1));
					post.setUsername(res2.getString(2));
					post.setDate_post(sdf.format(res2.getTimestamp(3)));
					post.setUrl(res2.getString(4));
					post.setDescription(res2.getString(5));
	
					posts.add(post);
				}


			}

		// Si no hemos obtenido posts, Directamente devlvemos notfound.
		if (posts.isEmpty()) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		// Ordenamos con JAVA los posts .

		Collections.sort(posts, new Comparator<Post>() {

			@Override
			public int compare(Post p1, Post p2) {

				return p1.getDate_post().compareTo(p2.getDate_post());

			}
		});

		Collections.reverse(posts);

		List<Post> finalPosts = new ArrayList<Post>();
		if(start <= posts.size()){
			end = Math.min(end, posts.size()+1);
			// Limitamos la salida de posts.
			finalPosts = posts.subList(start - 1, end - 1);
		}
		// Devolvemos la lista limitada y filtrada.
		GenericEntity<List<Post>> entity = new GenericEntity<List<Post>>(finalPosts) {};

		return Response.status(Response.Status.OK).entity(entity).build();
	}

}
