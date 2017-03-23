package com.upmsocial.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name = "tipo_post")
public class TipoPost {
	private int id;
	private String username;
	private Date date_post;
	private String url;
	private String description;

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
	public Date getDate_post() {
		return date_post;
	}
	public void setDate_post(Date date_post) {
		this.date_post = date_post;
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
	
	public TipoPost() {

    }

    public TipoPost(int id, String username, Date date_post, String url, String description) {
        super();
        this.id = id;
        this.username = username;
        this.date_post = date_post;
        this.url = url;
        this.description = description;
    }
    
    // Por qué constructor vacío ??

	// Tiene que tener id (int), username (string), date_post (timestamp), url
	// (varchar), description (String).

}
