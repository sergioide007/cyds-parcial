package dycs.ventapeliculas.domain.repository;

import java.util.List;

import dycs.ventapeliculas.domain.entity.VentaPelicula;

public interface VentaPeliculaBatchRepository {
	public void createBatch(List<VentaPelicula> ventaPeliculaList);
}
