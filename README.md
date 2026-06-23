MUSIC SHOP - ARQUITECTURA DE MICROSERVICIOS

Plataforma de venta de instrumentos musicales desarrollada con una arquitectura de microservicios usando Spring Boot, Spring Cloud Gateway y Eureka Server.

Integrantes del equipo: Isabel Cántaro y Andeison Belandria


ARQUITECTURA DEL SISTEMA

El sistema está compuesto por 11 microservicios de negocio, un API Gateway y un Eureka Server.

1. Eureka Server, puerto 8761: Registro y descubrimiento de servicios.
2. API Gateway, puerto 8080: Punto único de entrada, enruta las peticiones.
3. Bodega, puerto 8081: Logística e inventario de almacenes.
4. Cliente, puerto 8082: Datos personales de los compradores.
5. Descuento, puerto 8083: Rebajas de precios y cupones.
6. DevoluciónGarantía, puerto 8084: Devoluciones, reembolsos y garantías.
7. Empleado, puerto 8085: Personal, roles y accesos.
8. Producto, puerto 8086: Catálogo de instrumentos musicales.
9. Inventario, puerto 8087: Control de stock disponible y reservado.
10. Pago, puerto 8088: Procesamiento de transacciones financieras.
11. Pedido, puerto 8089: Órdenes y compras.
12. Proveedor, puerto 8090: Marcas y empresas distribuidoras.
13. Sucursal, puerto 8091: Tiendas físicas.




Todas las peticiones externas se hacen a través del Gateway en http://localhost:8080


DOCUMENTACION SWAGGER

Cada microservicio expone su documentación interactiva en:
http://localhost:{puerto}/swagger-ui.html


EJECUCION LOCAL (SIN DOCKER)

1. Clonar este repositorio.
2. Tener un servidor MySQL corriendo en localhost:3306 con la base music_shop creada.
3. Abrir el proyecto en IntelliJ IDEA.
4. Iniciar primero EurekaServerApplication. Esperar a que la consola indique que está corriendo.
5. Iniciar el ApiGatewayApplication.
6. Iniciar el resto de los microservicios en cualquier orden.
7. Verificar que todos se registraron correctamente en http://localhost:8761


EJECUCION CON DOCKER (REMOTA/CONTENEDORES)

Requisitos: tener Docker y Docker Compose instalados.

1. Clonar este repositorio.
2. Desde la raíz del proyecto (Music_Shop/), ejecutar el comando:
   docker-compose up --build
3. Esto levantará automáticamente: una instancia de MySQL, Eureka Server, API Gateway, y los 11 microservicios de negocio.
4. Verificar el registro de servicios en http://localhost:8761
5. Acceder a la API a través del Gateway en http://localhost:8080
6. Para detener todo, ejecutar el comando:
   docker-compose down


PRUEBAS

Las pruebas unitarias se ejecutan con el comando mvn test dentro de cada microservicio.
Las pruebas de la API se pueden realizar utilizando la colección de Postman adjunta al proyecto.

