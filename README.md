# API - SOS\_UPMSocial2017
Práctica 1 de una API RESTFull de la asignatura Sistemas Orientados a Servicios (SOS) de la Escuela Técnica Superior de Ingenieros Informáticos (ETISIINF, antigua Facultad de Informática, FI) de la Universidad Politécnica de Madrid (UPM).

## Información
| Infomacion: |  |   
| ----------- | --------
| Titulación  | Grado de Ingeniería Informática. Plan 09.
| Año         | 2016-2017
| Materia     | Sistemas Orientados a Servicios
| Curso		 | 3º Curso
| Semestre    | 6º Semestre (Mañana)
| Proyecto    | Practica 1 - API RESTFull


## Autores
- Diego Fernández Peces-Barba [t110271]
- Gonzalo Montiel Penedo [t]

## Índice
[TOC]

## Diseño de la Base de Datos
> **Nota:**
> Dado que la practica se basa en el diseño de una API RESTfull, en la implementación de un servidor y cliente para dicha API, hemos creado sólo lo necesario para que funcione correctamente.

Parametros de la base de datos:
- URI de la base de datos: `URL:3306`
(Alojada en una Raspberry PI 2)
- Nombre de la base de datos: `RestBBDD`.
- Nombre del usuario: `upmsocialapi`.
- Contraseña del usuario: `dieguito1`.

Para la base de datos disponemos de tres tablas:
- **USERS** (Tabla con datos de los usuarios).
- **POSTS** (Tabla con posts de los usuarios).
- **FRIENDS** (Tabla con amistades de los usuarios).

### Esquema E/R
El esquema de Entidad Relación diseñado para la base de datos de la práctica es el siguiente:

Entidad / Relación de la base de datos:

![Entidad / Relación](http://i.imgur.com/5StkUbT.png)

### Tabla USERS
Tabla donde se guardaran los usuarios de la plataforma.

Los atributos de la tabla serían los siguientes:
| username | name | surname |
|:-:|:-:|:-:|
| VARCHAR | VARCHAR | VARCHAR |

### Tabla POSTS
Tabla donde se guardaran los post o mensajes que creen los usuarios de la plataforma.

Los atributos de la tabla serían los siguientes:
| id | username | date_post | url | description |
|:-:|:-:|:-:|:-:|:-:|
| INT | VARCHAR | TIMESTAMP | VARCHAR | VARCHAR |

### Tabla FRIENDS
Relacion N:M de usuarios con usuarios. Nos hemos basado en el estilo de Twitter, de que usuarios siguen a usuarios, para mayor simpleza, siendo un usuario amigo de uno, pero este no es necesario que sea amigo del primero.

Los atributos de la tabla serían los siguientes:
| friend_id | id_user1 | id_user2 |
|:-:|:-:|:-:|
| INT | VARCHAR | VARCHAR |

### Código SQL de creación de la base de datos
> **Nota:**
> La base de datos no tiene por que estar vacía. El cliente funciona tanto si hay datos ya introducidos como si no.

Código:

``` MYSQL

CREATE SCHEMA IF NOT EXISTS `RestBBDD` DEFAULT CHARACTER SET utf8 ;
USE `RestBBDD` ;

----------------------------------------
--            TABLA USERS
----------------------------------------
DROP TABLE IF EXISTS `USERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USERS` (
  `username` varchar(20) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `surname` varchar(30) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


----------------------------------------
--            TABLA POSTS
----------------------------------------
DROP TABLE IF EXISTS `POSTS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `POSTS` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `date_post` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `url` varchar(100) NOT NULL,
  `description` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `username` (`username`),
  CONSTRAINT `POSTS_ibfk_1` FOREIGN KEY (`username`) REFERENCES `USERS` (`username`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=150 DEFAULT CHARSET=latin1;


----------------------------------------
--            TABLA FRIENDS
----------------------------------------
DROP TABLE IF EXISTS `FRIENDS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FRIENDS` (
  `friend_id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user1` varchar(20) NOT NULL,
  `id_user2` varchar(20) NOT NULL,
  PRIMARY KEY (`friend_id`),
  UNIQUE KEY `friend_id` (`friend_id`),
  KEY `id_user1` (`id_user1`),
  KEY `id_user2` (`id_user2`),
  CONSTRAINT `FRIENDS_ibfk_1` FOREIGN KEY (`id_user1`) REFERENCES `USERS` (`username`),
  CONSTRAINT `FRIENDS_ibfk_2` FOREIGN KEY (`id_user2`) REFERENCES `USERS` (`username`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=latin1;


```

## Diseño de las URIs

### Users

#### **[GET]** ~/users
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/ | 
| ------------- 	|-------------	| 
| Método      	| GET 				| 
| Cuerpo 			| Ninguno		|  
| Devuelve      	| <ul><li>``200`` : OK y POX (usuarios/usuario+xml)</li></ul>|


#### **[POST]** ~/users
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/ | 
| ------------- 	|-------------			| 
| Método      	| POST 					|
| Cuerpo 			| POX (users/user+xml)	|  
| Devuelve      	| <ul><li>``201`` : Created y cabecera Location</li><li>``415`` : Unsupported Media Type</li></ul>|  


#### **[GET]** ~/users/{username}
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{username} 							| 
| ------------- 	|-------------	| 
| Método      	| GET 				| 
| Cuerpo 			| Ninguno			|  
| Devuelve      	| <ul><li>``200`` : OK y POX (usuarios/usuario+xml)</li><li>``404`` : Not Found</li></ul>|


#### **[PUT]** ~/users/{username}
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{username} 									| 
| ------------- 	|-------------			| 
| Método      	| PUT 						|
| Cuerpo 			| POX (users/user+xml)	|  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``201`` : Created y cabecera Location *(1)</li><li>``400`` : Bad Request</li></ul>|  

* (1): En caso de no existir, el usuario se crea.


#### **[DELETE]** ~/users/{username}
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{username} | 
| ------------- 	|-------------			| 
| Método      	| DELETE 					|
| Cuerpo 			| POX (usuarios/user+xml)|  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``404`` : Not Found</li></ul>|  


### Friends


#### **[GET]** ~/users/{username}/friends
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{username}/friends 							| 
| ------------- 	|-------------			| 
| Método      	| GET 						|
| Cuerpo 			| POX (posts/post+xml)	|  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``404`` : Not Found</li></ul>|


#### **[POST]** ~/users/{username}/friends/{username\_2}
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{username}/friends/{username\_2} 					| 
| ------------- 	|-------------			| 
| Método      	| POST 					|
| Cuerpo 			| POX (posts/post+xml)	|  
| Devuelve      	| <ul><li>``201`` : Created y cabecera Location</li><li>``415`` : Unsupported Media Type</li></ul>|


#### **[DELETE]** ~/users/{username}/friends/{username\_2}
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{username}/friends/{username\_2} 							| 
| ------------- 	|-------------			| 
| Método      	| DELETE 					|
| Cuerpo 			| Ninguno					|  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``404`` : Not Found</li></ul>|

### Posts

#### **[GET]** ~/users/{username}/posts}
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{username}/posts 							| 
| ------------- 	|-------------			| 
| Método      	| GET 						|
| Cuerpo 			| POX (posts/post+xml)	|  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``404`` : Not Found</li></ul>|


#### **[POST]** ~/users/{username}/posts}
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{username}/posts 							| 
| ------------- 	|-------------			| 
| Método      	| POST 					|
| Cuerpo 			| POX (posts/post+xml)	|  
| Devuelve      	| <ul><li>``201`` : Created y cabecera Location</li><li>``415`` : Unsupported Media Type</li></ul>| 


#### **[GET]** ~/users/{username}/posts/count}
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{username}/posts/count 				| 
| ------------- 	|-------------	| 
| Método      	| GET 				|
| Cuerpo 			| Ninguno			|  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``404`` : Not Found</li></ul>|


#### **[GET]** ~/users/{username}/posts/{post\_id}
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{username}/posts/{post\_id} 		| 
| ------------- 	|-------------	| 
| Método      	| GET 				|
| Cuerpo 			| Ninguno			|  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``404`` : Not Found</li></ul>|


#### **[PUT]** ~/users/{username}/posts/{post\_id}
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{username}/posts/{post\_id} 		| 
| ------------- 	|-------------	| 
| Método      	| PUT 				|
| Cuerpo 			| POX (posts/post+xml)|  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``404`` : Not Found</li></ul>|


#### **[DELETE]** ~/users/{username}/posts/{post\_id}
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{username}/posts/{post\_id} 		| 
| ------------- 	|-------------	| 
| Método      	| DELETE			|
| Cuerpo 			| Ninguno          	|  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``404`` : Not Found</li></ul>|


#### **[GET]** ~/users/{user_id}/friends/posts
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{user_id}/friends/posts 	| 
| ------------- 	|-------------	| 
| Método      	| GET				|
| Cuerpo 			| Ninguno       |  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``404`` : Not Found</li></ul>|

## Pruebas API RESTfull

La API RESTfull se testeo mediante la aplicación POSTMAN. Se realizaban pruebas de cada metodo por separado. Al finalizar toda la API, se lanzó el cliente.


