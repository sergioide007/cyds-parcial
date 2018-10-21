CREATE TABLE pais (
  pais_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  PRIMARY KEY(pais_id),
  INDEX IX_pais_last_first_name(name),
  UNIQUE INDEX UQ_pais_identity_document(name)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;