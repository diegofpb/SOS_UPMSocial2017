package com.upmsocial.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tipo_friends")
public class TipoFriends {

	private int friend_id;
	private int id_user1;
	private int id_user2;


	 public int getFriend_id() {
		return friend_id;
	}

	public void setFriend_id(int friend_id) {
		this.friend_id = friend_id;
	}

	public int getId_user1() {
		return id_user1;
	}

	public void setId_user1(int id_user1) {
		this.id_user1 = id_user1;
	}

	public int getId_user2() {
		return id_user2;
	}

	public void setId_user2(int id_user2) {
		this.id_user2 = id_user2;
	}

	public TipoFriends() {

	    }

	    public TipoFriends(int friend_id,int id_user1, int id_user2) {
	    	super();
	        this.friend_id = friend_id;
	        this.id_user1 = id_user1;
	        this.id_user2 = id_user2;
	    }

}
