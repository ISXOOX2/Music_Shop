
CREATE TABLE `pedido` (
                          `id_pedido` Integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
                          `id_cliente` Integer NOT NULL,
                          `descuento_id` Integer DEFAULT NULL,
                          `fecha_emision` DATETIME NOT NULL,
                          `total_final` INTEGER NOT NULL,
                          `estado_pedido` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
