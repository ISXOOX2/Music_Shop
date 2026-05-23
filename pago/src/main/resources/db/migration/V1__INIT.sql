
CREATE TABLE `pago` (
                        `id_pago` Integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
                        `pedido_id` Integer NOT NULL,
                        `monto_pagado` INTEGER NOT NULL,
                        `metodo_pago` varchar(50) NOT NULL,
                        `fecha_pago` DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

