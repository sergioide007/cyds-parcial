package dycs.pais.domain.entity;

import java.util.Set;

import dycs.pelicula.domain.entity.Pelicula;

public class Pais {
	private long id;
    private String name;
    private Set<Pelicula> peliculas;
    
    public Pais() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Pelicula> getPeliculas() {
		return peliculas;
	}

	public void setPeliculas(Set<Pelicula> peliculas) {
		this.peliculas = peliculas;
	}

	
}
