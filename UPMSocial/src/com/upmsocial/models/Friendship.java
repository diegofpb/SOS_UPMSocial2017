package com.upmsocial.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "friendship")
public class Friendship {

	private int friend_id;
	private String id_user1;
	private String id_user2;


	 public int getFriend_id() {
		return friend_id;
	}

	public void setFriend_id(int friend_id) {
		this.friend_id = friend_id;
	}

	public String getId_user1() {
		return id_user1;
	}

	public void setId_user1(String id_user1) {
		this.id_user1 = id_user1;
	}

	public String getId_user2() {
		return id_user2;
	}

	public void setId_user2(String id_user2) {
		this.id_user2 = id_user2;
	}

	public Friendship() {
	}

	    public Friendship(int friend_id,String id_user1, String id_user2) {
	    	super();
	        this.friend_id = friend_id;
	        this.id_user1 = id_user1;
	        this.id_user2 = id_user2;
	    }

}
