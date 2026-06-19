CREATE TABLE `producto` (
                            `id_producto` Integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
                            `proveedor_id` Integer NOT NULL,
                            `nombre` varchar(200) NOT NULL,
                            `formato` varchar(100) NOT NULL,
                            `precio` INTEGER NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
