CREATE TABLE `sucursal` (
                            `id_sucursal` Integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
                            `nombre` varchar(100) NOT NULL,
                            `direccion` varchar(200) NOT NULL,
                            `telefono` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;