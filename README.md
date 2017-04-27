# API - SOS\_UPMSocial2017

## Users


| URI        		| http://localhost:8080/UPMSocial/api/v1/users/ | 
| ------------- 	|-------------	| 
| Método      	| GET 				| 
| Cuerpo 			| Ninguno			|  
| Devuelve      	| <ul><li>``200`` : OK y POX (usuarios/usuario+xml)</li></ul>|


| URI        		| http://localhost:8080/UPMSocial/api/v1/users/ | 
| ------------- 	|-------------			| 
| Método      	| POST 					|
| Cuerpo 			| POX (users/user+xml)	|  
| Devuelve      	| <ul><li>``201`` : Created y cabecera Location</li><li>``415`` : Unsupported Media Type</li></ul>|  


| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{username} 							| 
| ------------- 	|-------------	| 
| Método      	| GET 				| 
| Cuerpo 			| Ninguno			|  
| Devuelve      	| <ul><li>``200`` : OK y POX (usuarios/usuario+xml)</li><li>``404`` : Not Found</li></ul>|


| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{username} 									| 
| ------------- 	|-------------			| 
| Método      	| PUT 						|
| Cuerpo 			| POX (users/user+xml)	|  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``201`` : Created y cabecera Location *(1)</li><li>``400`` : Bad Request</li></ul>|  

* (1): En caso de no existir, el usuario se crea.


| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{username} | 
| ------------- 	|-------------			| 
| Método      	| DELETE 					|
| Cuerpo 			| POX (usuarios/user+xml)|  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``404`` : Not Found</li></ul>|  


## Friends

**GET** /users/{username}/friends
**POST** /users/{username}/friends/{username\_2}
**DELETE** /users/{username}/friends/{username\_2}

| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{username}/friends 							| 
| ------------- 	|-------------			| 
| Método      	| GET 						|
| Cuerpo 			| POX (posts/post+xml)	|  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``404`` : Not Found</li></ul>|


| URI        		| /users/{username}/friends/{username\_2} 					| 
| ------------- 	|-------------			| 
| Método      	| POST 					|
| Cuerpo 			| POX (posts/post+xml)	|  
| Devuelve      	| <ul><li>``201`` : Created y cabecera Location</li><li>``415`` : Unsupported Media Type</li></ul>|


| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{username}/friends 							| 
| ------------- 	|-------------			| 
| Método      	| DELETE 					|
| Cuerpo 			| Ninguno					|  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``404`` : Not Found</li></ul>|

## Posts

| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{username}/posts 							| 
| ------------- 	|-------------			| 
| Método      	| GET 						|
| Cuerpo 			| POX (posts/post+xml)	|  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``404`` : Not Found</li></ul>|


| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{username}/posts 							| 
| ------------- 	|-------------			| 
| Método      	| POST 					|
| Cuerpo 			| POX (posts/post+xml)	|  
| Devuelve      	| <ul><li>``201`` : Created y cabecera Location</li><li>``415`` : Unsupported Media Type</li></ul>| 

| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{username}/posts/count 				| 
| ------------- 	|-------------	| 
| Método      	| GET 				|
| Cuerpo 			| Ninguno			|  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``404`` : Not Found</li></ul>|


| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{username}/posts/{post\_id} 		| 
| ------------- 	|-------------	| 
| Método      	| GET 				|
| Cuerpo 			| Ninguno			|  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``404`` : Not Found</li></ul>|


| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{username}/posts/{post\_id} 		| 
| ------------- 	|-------------	| 
| Método      	| PUT 				|
| Cuerpo 			| POX (posts/post+xml)|  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``404`` : Not Found</li></ul>|


| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{username}/posts/{post\_id} 		| 
| ------------- 	|-------------	| 
| Método      	| DELETE			|
| Cuerpo 			| Ninguno          	|  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``404`` : Not Found</li></ul>|


| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{user_id}/friends/posts 	| 
| ------------- 	|-------------	| 
| Método      	| GET				|
| Cuerpo 			| Ninguno        |  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``404`` : Not Found</li></ul>|

