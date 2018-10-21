CREATE TABLE pelicula (
  pelicula_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  name_original VARCHAR(50) NOT NULL,  
  tipo_guion VARCHAR(50) NOT NULL,
  anio DECIMAL(4,0) NOT NULL,
  sinopsis VARCHAR(500) NOT NULL,
  fecha_estreno TIMESTAMP NOT NULL,
  duracion_minutos DECIMAL(4,0) NOT NULL,
  financiamiento DECIMAL(14,2) NOT NULL,
  currency VARCHAR(3) NOT NULL,
  active BIT NOT NULL,
  pais_id BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(pelicula_id),
  INDEX IX_pelicula_name_original(name_original),
  CONSTRAINT FK_pelicula_pais_id FOREIGN KEY(pais_id) REFERENCES pais(pais_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;