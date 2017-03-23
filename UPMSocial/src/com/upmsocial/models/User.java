package com.upmsocial.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class User {

    private String nombre;
    private String surname;
    private String username;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public User(String nombre,String surname, String username) {
        super();
        this.nombre = nombre;
        this.surname = surname;
        this.username = username;
    }


}
