package dycs.ventapeliculas.domain.repository;

import java.sql.SQLException;

import dycs.ventapeliculas.domain.entity.VentaPelicula;

//Separated Interface Pattern
//https://www.martinfowler.com/eaaCatalog/separatedInterface.html
public interface VentaPeliculaRepository {
	public void create(VentaPelicula ventaPelicula) throws SQLException;
	public void delete(VentaPelicula ventaPelicula) throws SQLException;
	public VentaPelicula read(long id) throws SQLException;
}
