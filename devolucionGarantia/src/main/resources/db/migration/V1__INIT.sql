

CREATE TABLE `devolucion_garantia` (
                                       `id_devolucion_garantia` Integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
                                       `pedido_id` Integer NOT NULL,
                                       `tipo_solicitud` varchar(50) NOT NULL,
                                       `motivo` varchar(300) NOT NULL,
                                       `estado_resolucion` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


