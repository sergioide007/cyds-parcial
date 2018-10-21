package dycs.ventapeliculas.infrastructure.persistence.jooq;

import java.util.List;

import javax.transaction.Transactional;

import org.jooq.BatchBindStep;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import dycs.ventapeliculas.domain.entity.VentaPelicula;
import dycs.ventapeliculas.domain.repository.VentaPeliculaBatchRepository;

@Transactional(rollbackOn=Exception.class)
@Repository
public class VentaPeliculaBatchJooqRepository implements VentaPeliculaBatchRepository {
	@Autowired
	DSLContext dsl;
	
	@Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
	protected String HIBERNATE_JDBC_BATCH_SIZE;
	
	public VentaPeliculaBatchJooqRepository() {
	}
	
	public void createBatch(List<VentaPelicula> ventaPeliculaList) {
		String sql = "INSERT INTO venta_pelicula(number, importe, currency, locked, customer_id, pelicula_id) VALUES(?, ?, ?, ?, ?, ?)";
		BatchBindStep batch = dsl.batch(sql);
		long i = 0;
		for (VentaPelicula ventaPelicula : ventaPeliculaList) {
			if (i % Integer.valueOf(HIBERNATE_JDBC_BATCH_SIZE) == 0 && i > 0) {
				batch.execute();
				batch = dsl.batch(sql);
			}
		    batch.bind(ventaPelicula.getNumber(), ventaPelicula.getImporte().getAmount(), ventaPelicula.getImporte().getCurrency().getCurrencyCode(), ventaPelicula.getIsLocked(), ventaPelicula.getCustomer().getId(), ventaPelicula.getPelicula().getId());
		    i++;
		}
		batch.execute();
	}
}
