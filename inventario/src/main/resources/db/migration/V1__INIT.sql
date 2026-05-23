
CREATE TABLE `inventario` (
                              `id_inventario` Integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
                              `producto_id` Integer NOT NULL,
                              `bodega_id` Integer NOT NULL,
                              `cantidad` Integer NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

