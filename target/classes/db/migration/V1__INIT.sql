SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Base de datos: `music_shop`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `bodega`
--

CREATE TABLE `bodega` (
                          `id_bodega` Integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
                          `sucursal_id` Integer NOT NULL,
                          `nombre` varchar(100) NOT NULL,
                          `capacidad_maxima` Integer NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
                           `id_cliente` Integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
                           `rut` varchar(12) NOT NULL,
                           `nombre_completo` varchar(100) NOT NULL,
                           `email` varchar(200) NOT NULL,
                           `password_hash` varchar(300) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `descuento`
--

CREATE TABLE `descuento` (
                             `id_descuento` Integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
                             `codigo` varchar(100) NOT NULL,
                             `porcentaje` Double NOT NULL,
                             `fecha_expiracion` DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `devolucion_garantia`
--

CREATE TABLE `devolucion_garantia` (
                                       `id_devolucion_garantia` Integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
                                       `pedido_id` Integer NOT NULL,
                                       `tipo_solicitud` varchar(50) NOT NULL,
                                       `motivo` varchar(300) NOT NULL,
                                       `estado_resolucion` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado`
--

CREATE TABLE `empleado` (
                            `id_empleado` Integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
                            `cliente_id` Integer NOT NULL,
                            `sucursal_id` Integer NOT NULL,
                            `rut` varchar(12) NOT NULL,
                            `cargo` varchar(200) NOT NULL,
                            `salario` INTEGER NOT NULL,
                            `nombre_completo` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inventario`
--

CREATE TABLE `inventario` (
                              `id_inventario` Integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
                              `producto_id` Integer NOT NULL,
                              `bodega_id` Integer NOT NULL,
                              `cantidad` Integer NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pago`
--

CREATE TABLE `pago` (
                        `id_pago` Integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
                        `pedido_id` Integer NOT NULL,
                        `monto_pagado` INTEGER NOT NULL,
                        `metodo_pago` varchar(50) NOT NULL,
                        `fecha_pago` DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedido`
--

CREATE TABLE `pedido` (
                          `id_pedido` Integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
                          `cliente_id` Integer NOT NULL,
                          `descuento_id` Integer DEFAULT NULL,
                          `fecha_emision` DATETIME NOT NULL,
                          `total_final` INTEGER NOT NULL,
                          `estado_pedido` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
                            `id_producto` Integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
                            `proveedor_id` Integer NOT NULL,
                            `nombre` varchar(200) NOT NULL,
                            `formato` varchar(100) NOT NULL,
                            `precio` INTEGER NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedor`
--

CREATE TABLE `proveedor` (
                             `id_proveedor` Integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
                             `rut` varchar (12) NOT NULL,
                             `razon_social` varchar(200) DEFAULT NULL,
                             `email` varchar(200) NOT NULL,
                             `telefono` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sucursal`
--

CREATE TABLE `sucursal` (
                            `id_sucursal` Integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
                            `nombre` varchar(100) NOT NULL,
                            `direccion` varchar(200) NOT NULL,
                            `telefono` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `bodega`
--
ALTER TABLE `bodega`
    ADD KEY `Bodega_Sucursal_FK` (`sucursal_id`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
    ADD UNIQUE KEY `Cliente_Email_UQ` (`email`),
    ADD UNIQUE KEY `Cliente_Rut_UQ` (`rut`);

--
-- Indices de la tabla `descuento`
--
ALTER TABLE `descuento`
    ADD UNIQUE KEY `Descuento__id_descuento` (`id_descuento`),
    ADD UNIQUE KEY `Descuento_Codigo_UQ` (`codigo`);

--
-- Indices de la tabla `devolucion_garantia`
--
ALTER TABLE `devolucion_garantia`
    ADD KEY `Devolucion_Pedido_FK` (`pedido_id`);

--
-- Indices de la tabla `empleado`
--
ALTER TABLE `empleado`
    ADD KEY `Empleado_Sucursal_FK` (`sucursal_id`),
    ADD KEY `Empleado_Cliente_FK` (`cliente_id`);

--
-- Indices de la tabla `inventario`
--
ALTER TABLE `inventario`
    ADD KEY `Inventario_Bodega_FK` (`bodega_id`),
    ADD KEY `Inventario_Producto_FK` (`producto_id`);

--
-- Indices de la tabla `pago`
--
ALTER TABLE `pago`
    ADD KEY `Pago_Pedido_FK` (`pedido_id`);

--
-- Indices de la tabla `pedido`
--
ALTER TABLE `pedido`
    ADD KEY `Pedido_Descuento_FK` (`descuento_id`),
    ADD KEY `Pedido_Cliente_FK` (`cliente_id`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
    ADD KEY `Producto_Proveedor_FK` (`proveedor_id`);

--
-- Indices de la tabla `proveedor`
--
ALTER TABLE `proveedor`
    ADD UNIQUE KEY `Proveedor__email` (`email`),
    ADD UNIQUE KEY `Proveedor_Rut_UQ` (`rut`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `bodega`
--
ALTER TABLE `bodega`
    ADD CONSTRAINT `Bodega_Sucursal_FK` FOREIGN KEY (`sucursal_id`) REFERENCES `sucursal` (`id_sucursal`);

--
-- Filtros para la tabla `devolucion_garantia`
--
ALTER TABLE `devolucion_garantia`
    ADD CONSTRAINT `Devolucion_Pedido_FK` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`id_pedido`);

--
-- Filtros para la tabla `empleado`
--
ALTER TABLE `empleado`
    ADD CONSTRAINT `Empleado_Cliente_FK` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id_cliente`),
    ADD CONSTRAINT `Empleado_Sucursal_FK` FOREIGN KEY (`sucursal_id`) REFERENCES `sucursal` (`id_sucursal`);

--
-- Filtros para la tabla `inventario`
--
ALTER TABLE `inventario`
    ADD CONSTRAINT `Inventario_Bodega_FK` FOREIGN KEY (`bodega_id`) REFERENCES `bodega` (`id_bodega`),
    ADD CONSTRAINT `Inventario_Producto_FK` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id_producto`);

--
-- Filtros para la tabla `pago`
--
ALTER TABLE `pago`
    ADD CONSTRAINT `Pago_Pedido_FK` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`id_pedido`);

--
-- Filtros para la tabla `pedido`
--
ALTER TABLE `pedido`
    ADD CONSTRAINT `Pedido_Cliente_FK` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id_cliente`),
    ADD CONSTRAINT `Pedido_Descuento_FK` FOREIGN KEY (`descuento_id`) REFERENCES `descuento` (`id_descuento`);

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
    ADD CONSTRAINT `Producto_Proveedor_FK` FOREIGN KEY (`proveedor_id`) REFERENCES `proveedor` (`id_proveedor`);
COMMIT;