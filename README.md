# API - SOS\_UPMSocial2017

## Usuarios


| URI        		| http://localhost:8080/UPMSocial/api/v1/usuarios/ | 
| ------------- 	|-------------	| 
| Método      	| GET 				| 
| Cuerpo 			| Ninguno			|  
| Devuelve      	| <ul><li>200 : OK y POX (usuarios/usuario+xml)</li></ul>|


| URI        		| http://localhost:8080/UPMSocial/api/v1/usuarios/ | 
| ------------- 	|-------------	| 
| Método      	| POST 			|
| Cuerpo 			| POX (usuarios/usuario+xml)|  
| Devuelve      	| <ul><li>201 : Created y cabecera Location</li><li>415 : Unsupported Media Type</li></ul>|  


| URI        		| http://localhost:8080/UPMSocial/api/v1/usuarios/{usuario} | 
| ------------- 	|-------------	| 
| Método      	| PUT 			|
| Cuerpo 			| POX (usuarios/usuario+xml)|  
| Devuelve      	| <ul><li>200 : OK</li><li>201 : Created y cabecera Location *(1)</li><li>400 : Bad Request</li></ul>|  

* (1): En caso de no existir, el usuario se crea.


| URI        		| http://localhost:8080/UPMSocial/api/v1/usuarios/{usuario} | 
| ------------- 	|-------------	| 
| Método      	| DELETE 			|
| Cuerpo 			| POX (usuarios/usuario+xml)|  
| Devuelve      	| <ul><li>200 : OK</li><li>404 : Not Found</li></ul>|  

## Posts

| URI        		| http://localhost:8080/UPMSocial/api/v1/posts/{usuario} | 
| ------------- 	|-------------	| 
| Método      	| GET 			|
| Cuerpo 			| POX (posts/post+xml)|  
| Devuelve      	| <ul><li>200 : OK</li><li>404 : Not Found</li></ul>|  

## Friendships