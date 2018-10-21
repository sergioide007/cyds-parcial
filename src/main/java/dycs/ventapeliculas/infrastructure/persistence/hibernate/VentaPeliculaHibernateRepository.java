package dycs.ventapeliculas.infrastructure.persistence.hibernate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dycs.common.infrastructure.persistence.hibernate.BaseHibernateRepository;
import dycs.ventapeliculas.domain.entity.VentaPelicula;
import dycs.ventapeliculas.domain.repository.VentaPeliculaRepository;
import dycs.ventapeliculas.infrastructure.persistence.jooq.VentaPeliculaBatchJooqRepository;

@Transactional(rollbackOn=Exception.class)
@Repository
public class VentaPeliculaHibernateRepository extends BaseHibernateRepository<VentaPelicula> implements VentaPeliculaRepository {
	@Autowired
	VentaPeliculaBatchJooqRepository ventaPeliculaBatchJooqRepository;
	
	public VentaPeliculaHibernateRepository() {
		super(VentaPelicula.class);
	}
}
