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
        System.out.println("Peticion 0 [DELETE] Dando de baja los perfiles de prueba anteriores (user_test y user_test2) y demas...");
        Response del_res = target.path("api/v1/users/user_test")
                .request()
                .delete();
        
        System.out.println("Estado: " + del_res.getStatus());
        del_res.close();
        
        del_res = target.path("api/v1/users/user_test2")
                .request()
                .delete();
        System.out.println("Estado: " + del_res.getStatus());
        del_res.close();
        
        del_res = target.path("api/v1/users/arecio_test")
                .request()
                .delete();
        System.out.println("Estado: " + del_res.getStatus());
        del_res.close();
        
        del_res = target.path("api/v1/users/henry_test")
                .request()
                .delete();
        System.out.println("Estado: " + del_res.getStatus());
        del_res.close();
        
        del_res = target.path("api/v1/users/elcuqui_test")
                .request()
                .delete();
        System.out.println("Estado: " + del_res.getStatus());
        del_res.close();
        
        // Peticion 1 [POST] => Crear Usuario
        System.out.println("\nPeticion 1 [POST] crear 2 usuarios de test y 3 mas para otras pruebas...");
        User user_test = new User("User","Test","user_test");
        User user_test2 = new User("User2","Test2","user_test2");
        User user_test3 = new User("Antonio","Recio","arecio_test");
        User user_test4 = new User("Enrique","Pastor","henry_test");
        User user_test5 = new User("Amador","Rivas","elcuqui_test");

        

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
        
        response = target.path("api/v1/users")
                .request()
                .post(Entity.xml(user_test3));
        
        System.out.println("Estado: " + response.getStatus());
        if (response.getStatus()==201){
        	System.out.println("Location: " + response.getLocation()+"\n");
        }
        response.close();
        
        response = target.path("api/v1/users")
                .request()
                .post(Entity.xml(user_test4));
        
        System.out.println("Estado: " + response.getStatus());
        if (response.getStatus()==201){
        	System.out.println("Location: " + response.getLocation()+"\n");
        }
        response.close();
        
        
        response = target.path("api/v1/users")
                .request()
                .post(Entity.xml(user_test5));
        
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
        
        URI location_post_2 = response.getLocation();

        
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
        
        URI location_post_1 = response.getLocation();

        
        response.close();
        
        Post post_test4 = new Post();
        post_test4.setDate_post("2017-05-02");
        post_test4.setDescription("Mariscos Recio");
        post_test4.setUrl("http://MariscosRecio.es");
        post_test4.setUsername("arecio_test");
                
        response = target.path("api/v1/users/arecio_test/posts")
        		.request()
                .post(Entity.xml(post_test4));
         
        System.out.println("Estado: " + response.getStatus());

        if (response.getStatus()==201){
        	System.out.println("Location: " + response.getLocation());
        }
        
        response.close();
        
        
        // Peticion 4 [GET] => Obtener posts de un usuario (user_test).
        System.out.println("\nPeticion 4 [GET] Obtenemos los posts de un usuario (user_test)...");
        
        System.out.println(target.path("api/v1/users/user_test/posts")
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(String.class));
        
        
        
        // Peticion 5 [PUT] => Modificar un post de un usuario.
        System.out.println("\nPeticion 5 [PUT] => Modificar el post del primer usuario...");
        
        Post post_put_1 = new Post();
        post_put_1.setDate_post("2017-05-01");
        post_put_1.setDescription("La que se avecina");
        post_put_1.setUrl("http://lqsa.es");
        post_put_1.setUsername("user_test2");
        
        String[] segments = location_post_1.getPath().split("/");
        String idStr = segments[segments.length-1];
        int id = Integer.parseInt(idStr);
                
        response = target.path("api/v1/users/user_test2/posts/"+id)
        		.request()
                .put(Entity.xml(post_put_1));
         
        System.out.println("Estado: " + response.getStatus());

        response.close();
        
        
     // Peticion 6 [GET] => Obtener número posts de un usuario en un periodo (user_test).
        System.out.println("\nPeticion 6 [GET] Obtener número posts de un usuario en un periodo (user_test)...");
        
        System.out.println("Número de posts: " + target.path("api/v1/users/user_test/posts/count")
        		.queryParam("from", "2017-04-20")
        		.queryParam("to", "2017-05-20")
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(String.class));
        
        
        // Peticion 7 [DELETE] => Eliminar un post de un usuario.
      
        String[] segments2 = location_post_2.getPath().split("/");
        String idStr2 = segments2[segments2.length-1];
        int id2= Integer.parseInt(idStr2);
        
        
        System.out.println("\nPeticion 7 [DELETE] => Eliminar un post de un usuario...");
        response = target.path("api/v1/users/user_test/posts/"+id2)
        		.request()
                .delete();
        
        System.out.println("Estado: " + response.getStatus());
        response.close();

        // Peticion 8 [POST] => Agregar un amigo
        System.out.println("\nPeticion 8 [POST] => Agregar un amigo...");
        
        Friendship friendship_1 = new Friendship();
        friendship_1.setId_user1("user_test");
        friendship_1.setId_user2("user_test2");
                
        response = target.path("api/v1/users/user_test/friends/user_test2")
        		.request()
                .post(Entity.xml(friendship_1));
         
        System.out.println("Estado: " + response.getStatus());
        
        Friendship friendship_2 = new Friendship();
        friendship_2.setId_user1("arecio_test");
        friendship_2.setId_user2("henry_test");
      
        response = target.path("api/v1/users/arecio_test/friends/henry_test")
        		.request()
                .post(Entity.xml(friendship_2));
        
        System.out.println("Estado: " + response.getStatus());
        
        // Peticion 9 [GET] => Obtener lista de todos amigos
        System.out.println("\nPeticion 9 [GET] => Obtener lista de amigos...");
        
        System.out.println(target.path("api/v1/users/user_test/friends")
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(String.class));
        
        // Peticion 10 [GET] => Obtener lista de todos amigos con filtro de nombre para amigo inexistente
        System.out.println("\nPeticion 10 [GET] => Obtener lista de todos amigos con filtro de nombre para amigo inexistente (debe de dar 404)...");
        
        try {
        	System.out.println(target.path("api/v1/users/user_test/friends")
            		.queryParam("filter_by_name", "pepe")
                    .request()
                    .accept(MediaType.APPLICATION_XML)
                    .get(String.class));
        } catch (Exception error) {

        	System.out.println(error);

        }
        
        
        		
        // Peticion 11 [GET] => Obtener lista de todos amigos con filtro de nombre para amigo existente
        System.out.println("\nPeticion 11 [GET] => Obtener lista de todos amigos con filtro de nombre para amigo existente (user)...");
        
        System.out.println(target.path("api/v1/users/user_test/friends")
        		.queryParam("filter_by_name", "user")
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(String.class));
        
        
        // Peticion 12 [PUT] => Modificar datos del perfil de un usuario
        System.out.println("\nPeticion 12 [PUT] => Modificar datos del perfil de un usuario...");
        
        User user_put_1 = new User();
        user_put_1.setUsername("user_test");
        user_put_1.setName("New user Name");
        user_put_1.setSurname("This is my new surname");
        
  
        response = target.path("api/v1/users/user_test/")
        		.request()
                .put(Entity.xml(user_put_1));
         
        System.out.println("Estado: " + response.getStatus());

        response.close();
        
        // Peticion 13 [GET] => Obtener lista de todos los usuarios
        System.out.println("\nPeticion 13 [GET] => Obtener lista de todos los usuarios...");
        
        System.out.println(target.path("api/v1/users")
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(String.class));
        
        // Peticion 14 [GET] => Buscar posibles amigos "Buscar a antonio"
        System.out.println("\nPeticion 14 [GET] => Buscar posibles amigos \"Buscar a antonio\"...");
        
        System.out.println(target.path("api/v1/users")
        		.queryParam("filter_by_name", "antonio")
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(String.class));
        
        // Peticion 15 [GET] => Obtener la lista de posts publicados por amigos que contienen un determinado texto
        System.out.println("\nPeticion 15 [GET] => Obtener la lista de posts publicados por amigos (de user_test) que contienen un determinado texto...");
        
        System.out.println(target.path("api/v1/users/user_test/friends/posts")
        		.queryParam("filter_by_text", "avecina")
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(String.class));
        
        // Peticion 16 [GET] => Obtener la lista de posts publicados por amigos.
        System.out.println("\nPeticion 16 [GET] => Obtener la lista de posts publicados por amigos.");
        
        System.out.println(target.path("api/v1/users/henry_test/friends/posts")
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(String.class));
        
        // Peticion 17 [DELETE] => Borrar un amigo
        System.out.println("\nPeticion 17 [DELETE] => Borrar un amigo...");
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