ALTER TABLE `inventario`
    CHANGE COLUMN `cantidad` `cantidad_disponible` INTEGER NOT NULL,
    ADD COLUMN `cantidad_reservada` INTEGER NOT NULL DEFAULT 0;