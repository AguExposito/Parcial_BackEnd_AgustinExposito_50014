# Parcial_BackEnd_AgustinExposito_50014

NIVEL 2
Primero vamos a necesitar abrir Postman o JMeter, voy  a hacer el ejemplo con JMeter dado que el nivel 3 se hacer exclusivamente con JMeter.
primero necesitamos crear un thread group, dentro de thread group debemos crear un HTTP Request y hacer lo siguiente:
1- a) poner en protocol [http]: https
b) poner en Server Name or IP: parcial-backend-agustinexposito-50014.onrender.com (API URL)
c) abrir el desplegable y seleccionar POST
d) poner en path: /mutant
e) cambiar a la pestaña Body Data y agregar info para hacer el POST ej:
{ 
“dna”:["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}

Una vez hecho esto debemos crear un HTTP Head Manager ----> le damos al botón Add ----> name:Content-Type, value:application/json

Luego vamos a crear un view results tree para poder visualizar los datos
podemos darle play y dependiendo de la secuencia de adn ingresado determinará si es mutante o no (codigo 200 o 403)


NIVEL 3
En JMeter dentro de thread group debemos crear un HTTP Request nuevo y hacer lo siguiente:
2- a) poner en protocol [http]: https
b) poner en Server Name or IP: parcial-backend-agustinexposito-50014.onrender.com (API URL)
c) abrir el desplegable y seleccionar GET
d) poner en path: /stats

Antes de darle play podemos modificar los valores Number of Threads y Loop Count de Thread Group para controlar la cantidad de peticiones y usuarios
Adicionalmente puedes crear un Summary Report para visualizar datos extras
Hay que tener en cuenta que solo se permite un registro por ADN
Luego debemos darle a play y observar los resultados hasta alcanzar el deseado ej:
{“count_mutant_dna”:40, “count_human_dna”:100: “ratio”:0.4}
