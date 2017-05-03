package com.upmsocial.models;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "post")
public class Post {
	private int id;
	private String username;
	private String date_post;
	private String url;
	private String description;

	@XmlAttribute
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDate_post() {
		return date_post;
	}
	public void setDate_post(String string) {
		this.date_post = string;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Post() {

    }

    public Post(int id, String username, String date_post, String url, String description) {
        super();
        this.id = id;
        this.username = username;
        this.date_post = date_post;
        this.url = url;
        this.description = description;
    }
    
}
