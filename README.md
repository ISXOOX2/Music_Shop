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
