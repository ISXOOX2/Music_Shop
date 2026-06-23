Music Shop - Arquitectura de Microservicios

Este proyecto consiste en una plataforma de instrumentos musicales basada en una arquitectura de microservicios.
Integrantes del Equipo: Isabel Cántaro y Andeison Belandria

Arquitectura del Sistema
El sistema está compuesto por 11 microservicios independientes,un servidor de descubrimiento y Eureka Server.

  1-Eureka Server: Servidor central donde todos los demás microservicios se registran automáticamente al encenderse para poder hablar entre ellos sin conocer sus direcciones IP reales.
  2-API Gateway:Recibe todas las peticiones desde el exterior (como Postman) y se encarga de redirigirlas al microservicio que corresponda.
  3-Producto: Administra el catálogo completo de la tienda, incluyendo la creación, categorías y detalles de los instrumentos musicales.
  4-Inventario: Se encarga de la gestión y el control de stock de todos los productos disponibles.
  5-Pedido: Administra las compras, genera las órdenes y conecta con otros servicios para verificar que la transacción sea válida.
  6-Pago: Procesa las transacciones financieras, valida los montos de las órdenes y registra si la boleta fue pagada con éxito.
  7-DevoluciónGarantía: Gestiona los procesos post-venta, solicitudes de devolución de productos, reembolsos y la aplicación de garantía de los instrumentos.
  8-Bodega: Controla la logística interna, el inventario de los almacenes.
  9-Cliente: Almacena y gestiona los datos personales, de los compradores de la tienda.
  10-Descuento: Administra las rebajas de precios y cupones especiales que se pueden aplicar a los productos durante una compra.
  11-Proveedor: Gestiona la información de las marcas y empresas distribuidoras que nos surten de instrumentos musicales.
  12-Sucursal: Administra los datos de las distintas tiendas físicas.
  13-Empleado: Controla la información del personal, los roles de los trabajadores y los accesos de los vendedores al sistema.

Instrucciones de Ejecución

Para levantar el proyecto en un entorno local, hay que seguir los siguientes pasos:

1. Clonar este repositorio en la máquina local.
2. Asegúrarse de tener el servidor MySQL encendido.
3. Abrir el proyecto en el IDE (IntelliJ IDEA).
4. Iniciar primero el proyecto “EurekaServerApplication”. Esperar a que la consola indique que está corriendo.
5. Iniciar los microservicios uno por uno.

Pruebas
Las pruebas de la API se pueden realizar utilizando la colección de Postman adjunta al proyecto.

## Configuración por Perfiles

El proyecto soporta perfiles de ejecución para dev y test:

### Ejecución local (desarrollo)
```bash
# El perfil dev se activa automáticamente
mvn spring-boot:run
```

### Ejecución con perfil específico
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

## Despliegue con Docker

### Construir y ejecutar con docker-compose
```bash
# Navegar a la raíz del proyecto
cd /ruta/al/proyecto

# Construir todas las imágenes y levantar servicios
docker-compose up -d

# Ver logs en tiempo real
docker-compose logs -f

# Ver estado de servicios
docker-compose ps

# Detener todos los servicios
docker-compose down

# Detener y eliminar volúmenes (borra BD)
docker-compose down -v
```

## Documentación API (Swagger)

Accede a la documentación interactiva en cada microservicio:

http://localhost:[PUERTO]/swagger-ui.html

### URLs por Microservicio:
- Producto: http://localhost:8086/swagger-ui.html
- Pedido: http://localhost:8089/swagger-ui.html
- Pago: http://localhost:8088/swagger-ui.html
- Inventario: http://localhost:8087/swagger-ui.html
- API Gateway: http://localhost:8080/swagger-ui.html
- (Todas las demás en puertos 8081-8091)

### Pruebas en Swagger:
1. Abre cualquier endpoint GET
2. Click en "Try it out"
3. Click en "Execute"
4. Verás la respuesta JSON

## Pruebas Unitarias

Ejecutar pruebas de un microservicio:
```bash
cd [microservicio]
mvn test
```

Microservicios con pruebas:
- producto: 5 tests ✅
- pedido: 6 tests ✅
- pago: 3 tests (incluye FeignException) ✅
- inventario: 5 tests ✅

Cobertura objetivo: 80% en capa de servicios

## Estructura de Puertos

| Servicio | Puerto | Tipo |
|----------|--------|------|
| Eureka | 8761 | Descubrimiento |
| API Gateway | 8080 | Enrutamiento |
| Producto | 8086 | Catálogo |
| Pedido | 8089 | Órdenes |
| Pago | 8088 | Transacciones |
| Inventario | 8087 | Stock |
| Bodega | 8081 | Almacenes |
| Cliente | 8082 | Clientes |
| Descuento | 8083 | Rebajas |
| DevolucionGarantía | 8084 | Post-venta |
| Empleado | 8085 | Personal |
| Proveedor | 8090 | Distribuidoras |
| Sucursal | 8091 | Tiendas |

## Stack Técnico

- **Java:** 25.0.1
- **Spring Boot:** 4.0.6
- **Spring Cloud:** Eureka, Gateway, OpenFeign
- **BD:** MySQL 8.0 (MariaDB 10.4.32)
- **ORM:** Hibernate 7.2.12 + Spring Data JPA
- **API Docs:** Springdoc OpenAPI 3.0.2 (Swagger)
- **Testing:** JUnit5 + Mockito
- **Build:** Maven 3.9
- **Containerización:** Docker + docker-compose

## Información de Contacto / Autores

- Isabel Cántaro (ISXOOX2)
- Andeison Belandria

Repositorio: https://github.com/ISXOOX2/Music_Shop.git