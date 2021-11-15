# API hecha con SpringBoot donde se gestionan Usuarios que se encuentran en una base de datos

### Para usarla descargar el proyecto de spring y utilizar la coleccion de postman que se adjunta al mismo

* Primero se debe hacer un POST al endpoint /login, poniendo en el body el usuario y contraseña correcta, los mismos se puede configurar desde el archivo application.poperties, una vez hecho el POST este nos devolvera un token
* Una vez copiado ese token se podra utilizar las demas funciones de la api pegando el token como header (Authorization)

* * Para Insertar un usuario nuevo se debe hacer un POST al endpoint /User y pasarle en el body como JSON el user que queramos
* * El formato de los usuarios es el siguiente: 
``` JSON
{
"name":"Joe",
"surname":"Doe",
"dni":"00000000",
"birthDate":"1990-01-01",
"location":"XXX",
"phoneNumber":"XXXXXXXXX",
"email":"d@d.d"
}
```

* * Para hacer una busqueda de un Usuario en especifico se debe hacer un GET al endpoint /User y pasarle como parametro el dni del usuario que se quiere buscar
* * Para eliminar un Usuario de la base de datos se debe hacer un DELETE al endpoint /User y pasarle como parametro el dni del usuario que se quiere borrar
* * Para obtener un listado de Usuarios se debe hacer un GET al endpoint /AllUsers pasandole un indice de comienzo y de fin para el paginado, tambien se puede optar
    por filtrar la busqueda por dni o por nombre y apellido
* * Para Actualizar un Usuraio Existente se debe hacer un POST al endpoint /existingUser pasandole en el body la nueva forma que tendria el Usuario que se quiere actualizar
