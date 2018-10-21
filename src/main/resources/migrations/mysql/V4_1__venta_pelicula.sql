CREATE TABLE venta_pelicula (
  venta_pelicula_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  number VARCHAR(50) NOT NULL,  
  importe DECIMAL(14,2) NOT NULL,
  currency VARCHAR(3) NOT NULL,
  locked BIT NOT NULL,
  customer_id BIGINT UNSIGNED NOT NULL,
  pelicula_id BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(venta_pelicula_id),
  INDEX IX_venta_pelicula_customer_id(customer_id),
  UNIQUE INDEX UQ_venta_pelicula_number(number),
  CONSTRAINT FK_venta_pelicula_customer_id FOREIGN KEY(customer_id) REFERENCES customer(customer_id),
  CONSTRAINT FK_venta_pelicula_pelicula_id FOREIGN KEY(pelicula_id) REFERENCES pelicula(pelicula_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;