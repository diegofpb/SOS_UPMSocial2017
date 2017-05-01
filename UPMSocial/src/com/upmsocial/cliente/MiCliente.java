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
        
        // Peticion 0 [DELETE] => Borrar usuarios de la red social para comenzar de 0.
        System.out.println("Peticion 0 [DELETE] Borrar datos de la red (user_test y user_test2)...");
        Response del_res = target.path("api/v1/users/user_test")
                .request()
                .delete();
        
        System.out.println("Estado: " + del_res.getStatus());
        del_res.close();
        
        del_res = target.path("api/v1/users/user_test2")
                .request()
                .delete();
        System.out.println("Estado: " + del_res.getStatus() +"\n");
        del_res.close();
        
        // Peticion 1 [POST] => Crear Usuario
        System.out.println("Peticion 1 [POST] crear 2 usuarios de test...");
        User user_test = new User("User","Test","user_test");
        User user_test2 = new User("User2","Test2","user_test2");

        Response response = target.path("api/v1/users")
                .request()
                .post(Entity.xml(user_test));
        
        System.out.println("Estado: " + response.getStatus());
        if (response.getStatus()==201){
        	System.out.println("Location: " + response.getLocation()+"\n");
        }
        response.close();
        
        response = target.path("api/v1/users")
                .request()
                .post(Entity.xml(user_test2));
        
        System.out.println("Estado: " + response.getStatus());
        if (response.getStatus()==201){
        	System.out.println("Location: " + response.getLocation()+"\n");
        }
        response.close();
        
        // Peticion 2 [GET] => Obtener dicho usuario.
        System.out.println("\nPeticion 2 (1/2) [GET] Obtenemos los datos del primer usuario creado ahora mismo...");
        User user_test_verify = target.path("api/v1/users/user_test")
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(User.class);
        
        System.out.println(	"Name: "+user_test_verify.getName()+"\n"+
        					"Surname: "+user_test_verify.getSurname()+"\n"+
        					"Username: "+user_test_verify.getUsername()+"\n");
        
        System.out.println("\nPeticion 2 (2/2) [GET] Obtenemos los datos del segundo usuario creado ahora mismo...");
        User user_test_verify2 = target.path("api/v1/users/user_test2")
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(User.class);
        
        System.out.println(	"Name: "+user_test_verify2.getName()+"\n"+
        					"Surname: "+user_test_verify2.getSurname()+"\n"+
        					"Username: "+user_test_verify2.getUsername()+"\n");
        
        
        // Peticion 3 [POST] => Crear un post de dicho usuario.
        System.out.println("\nPeticion 3 (1/3) [POST] => Crear un post del primer usuario...");
        
        Post post_test1 = new Post();
        post_test1.setDate_post("2017-04-27");
        post_test1.setDescription("Google Website");
        post_test1.setUrl("http://google.es");
        post_test1.setUsername("user_test");
                
        response = target.path("api/v1/users/user_test/posts")
        		.request()
                .post(Entity.xml(post_test1));
         
        System.out.println("Estado: " + response.getStatus());

        
        if (response.getStatus()==201){
        	System.out.println("Location: " + response.getLocation());
        }
        
        URI location_post_1 = response.getLocation();
        
        
        response.close();
        
        
        System.out.println("\nPeticion 3 (2/3) [POST] => Crear segundo post del primer usuario...");
        
        Post post_test3 = new Post();
        post_test3.setDate_post("2017-04-27");
        post_test3.setDescription("Tuentis Website");
        post_test3.setUrl("http://tuenti.es");
        post_test3.setUsername("user_test");
                
        response = target.path("api/v1/users/user_test/posts")
        		.request()
                .post(Entity.xml(post_test3));
         
        System.out.println("Estado: " + response.getStatus());

        
        if (response.getStatus()==201){
        	System.out.println("Location: " + response.getLocation());
        }
        
        response.close();
        
        System.out.println("\nPeticion 3 (3/3) [POST] => Crear un post del segundo usuario...");
        
        Post post_test2 = new Post();
        post_test2.setDate_post("2017-04-27");
        post_test2.setDescription("Facebook Website");
        post_test2.setUrl("http://facebook.es");
        post_test2.setUsername("user_test2");
                
        response = target.path("api/v1/users/user_test2/posts")
        		.request()
                .post(Entity.xml(post_test2));
         
        System.out.println("Estado: " + response.getStatus());

        if (response.getStatus()==201){
        	System.out.println("Location: " + response.getLocation());
        }
        
        response.close();
        
        
        
        // Peticion 4 [GET] => Obtener posts de un usuario (user_test).
        System.out.println("\nPeticion 4 (1/2) [GET] Obtenemos los posts del cliente...");
        
        System.out.println(target.path("api/v1/users/user_test/posts")
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(String.class));
        
        
        
        // Peticion 5 [PUT] => Modificar un post de un usuario.
        System.out.println("\nPeticion 5 [PUT] => Modificar el post del primer usuario...");
        
        Post post_put_1 = new Post();
        post_put_1.setDate_post("2017-05-01");
        post_put_1.setDescription("Post Modification");
        post_put_1.setUrl("http://modificationpost.es");
        post_put_1.setUsername("user_test");
        
        String[] segments = location_post_1.getPath().split("/");
        String idStr = segments[segments.length-1];
        int id = Integer.parseInt(idStr);
                
        response = target.path("api/v1/users/user_test/posts/"+id)
        		.request()
                .put(Entity.xml(post_put_1));
         
        System.out.println("Estado: " + response.getStatus());

        response.close();
        
        
        
        // Peticion 6 [DELETE] => Eliminar un post de un usuario.
      
        System.out.println("\nPeticion 6 [DELETE] => Eliminar un post de un usuario...");
        response = target.path("api/v1/users/user_test/posts/"+id)
        		.request()
                .delete();
        
        System.out.println("Estado: " + response.getStatus());
        response.close();

        // Peticion 7 [POST] => Agregar un amigo
        System.out.println("\nPeticion 7 [POST] => Agregar un amigo...");
        
        Friendship friendship_1 = new Friendship();
        friendship_1.setId_user1("user_test");
        friendship_1.setId_user2("user_test2");
                
        response = target.path("api/v1/users/user_test/friends/user_test2")
        		.request()
                .post(Entity.xml(friendship_1));
         
        System.out.println("Estado: " + response.getStatus());
      
       
        // Peticion 8 [DELETE] => Borrar un amigo
        System.out.println("\nPeticion 8 [DELETE] => Borrar un amigo...");
        response = target.path("api/v1/users/user_test/friends/user_test2")
        		.request()
                .delete();
        
        System.out.println("Estado: " + response.getStatus());
        response.close();
        
        
        
        
        // FIN DEL CLIENTE
        System.out.println("\nFIN DEL CLIENTE\n");


    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/UPMSocial/").build();
    }

}