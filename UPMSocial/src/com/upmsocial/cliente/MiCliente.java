package com.upmsocial.cliente;

import org.glassfish.jersey.client.ClientConfig;

import com.upmsocial.models.*;

import java.net.URI;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;


public class MiCliente {

    public static void main(String[] args) throws Exception{

        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(getBaseURI());

        
        /*
        * [OK] Crear un usuario nuevo 
		* Publicar un post nuevo (o varios) 
		* Obtener mis posts usando los  filtros disponibles  (en este caso, el cliente debe 
		poder  optar  por  obtener  la  lista  de  identificadores  de  esos  posts  o  bien 
		directamente el contenido de dichos posts) 
		* Modificar un post 
		* Borrar un post 
		* Buscar posibles amigos entre los usuarios 
		* Agregar un amigo 
		* Eliminar a un amigo 
		* Obtener la lista de amigos usando los filtros disponibles 
		* Consultar número de posts publicados por mí en un periodo 
		* Obtener la lista de usuarios 
		* Modificar los datos de nuestro perfil 
		* Darse de baja de la red social 
		* Obtener la lista de posts publicados por amigos que contienen un determinado 
			texto 
         */
        
        
        
        // Peticion 1 [POST] => Crear Usuario
        System.out.println("Peticion 1 [POST] crear usuario de test...");
        User user_test = new User("User","Test","user_test");
        Response response = target.path("api/v1/users")
                .request()
                .post(Entity.xml(user_test));
        
        System.out.println("Estado: " + response.getStatus());
        if (response.getStatus()==201){
        	System.out.println("Location: " + response.getLocation());
        }
        response.close();
        
        // Peticion 2 [GET] => Obtener dicho usuario.
        System.out.println("\nPeticion 2 [GET] Obtenemos datos del usuario creado ahora mismo...");
        User user_test_verify = target.path("api/v1/users/user_test")
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(User.class);
        
        System.out.println(	"Name: "+user_test_verify.getName()+"\n"+
        					"Surname: "+user_test_verify.getSurname()+"\n"+
        					"Username: "+user_test_verify.getUsername()+"\n");
        
        
        // Peticion 3 [POST] => Crear un post de dicho usuario.
        System.out.println("\nPeticion 3 [POST] => Crear un post de dicho usuario...");
        
        Post post_test = new Post();
        post_test.setDate_post("2017-04-27");
        post_test.setDescription("Googles Website");
        post_test.setUrl("http://google.es");
        post_test.setUsername("user_test");
        
        //System.out.println("Aqui tu post: " + post_test.getDate_post() + post_test.getDescription() + post_test.getUrl() + post_test.getUsername());
        
        Response response1 = target.path("api/v1/users/user_test/posts")
        		.request()
                .post(Entity.xml(post_test));
         
       
        System.out.println("Estado: " + response1.getStatus());

        if (response1.getStatus()==201){
        	System.out.println("Location: " + response1.getLocation());
        }
        
        response1.close();
        
        
        
        System.out.println("\nFIN DEL CLIENTE\n");


    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/UPMSocial/").build();
    }

}