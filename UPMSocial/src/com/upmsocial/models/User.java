package com.upmsocial.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class User {

    private String name;
    private String surname;
    private String username;

    public String getName() {
        return name;
    }

    public void setName(String nombre) {
        this.name = nombre;
    }
   
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

    public User() {

    }

    public User(String name,String surname, String username) {
        super();
        this.name = name;
        this.surname = surname;
        this.username = username;
    }


}
