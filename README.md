# Microservicios con OpenFeign

Dos microservicios (Canchas y Reservas) en Spring Boot que se comunican entre sí usando OpenFeign.

## Qué es OpenFeign
Es una forma limpia de hacer que un microservicio consuma la API de otro. En vez de armar clientes HTTP a mano, defines una interfaz y Spring se encarga de todo el código por detrás.

## Cambios hechos en el proyecto
- **Canchas:** Quedó igual, solo provee los datos.
- **Reservas:** 
  - Se agregó la dependencia de OpenFeign al pom.xml.
  - Se activó con `@EnableFeignClients` en la clase principal.
  - Se creó la interfaz CanchaFeignClient que apunta directo al servicio de canchas.
  - Se agregaron llamadas a esta interfaz en la capa de servicios y dos endpoints nuevos en el controlador para probar que trae la data.

## Pasos para ejecutar
1. Levanta tu base de datos MySQL local (club_deportivo).
2. Levanta primero el servicio de canchas (corre en el 8081).
3. Levanta después el servicio de reservas (corre en el 8082).