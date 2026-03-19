Microservicios con Openfeign

Son dos microservicios (canchas y reservas) hechos en spring boot que se comunican usando openfeign.

Que es Openfeign
Es una forma mas limpia pa que un microservicio consuma la api de otro. en vez de armar los clientes http a mano, haces una interfaz y spring hace todo por detras.

Cambios que se hicieron:

Canchas: quedo igual, solo tira los datos.
Reservas:
Se le agrego la dependencia de openfeign al pom.xml
Se activo poniendo @EnableFeignClients en la clase principal
Se creo la interfaz canchafeignclient que le pega directo al servicio de canchas
Se metieron llamadas a esta interfaz en los servicios y dos endpoints nuevos en el controller pa probar que traiga los datos

Pasos para ejecutar

Levanta la base de datos mysql local (club_deportivo)

Levanta primero el servicio de canchas (corre en el 8081)

Levanta despues el de reservas (corre en el 8082)