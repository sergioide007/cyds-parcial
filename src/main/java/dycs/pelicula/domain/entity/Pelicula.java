package dycs.pelicula.domain.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import dycs.pais.domain.entity.Pais;
import dycs.ventapeliculas.domain.entity.VentaPelicula;

public class Pelicula {
	private long id;
    private String nameOriginal;
    private String tipoGuion;
    private Integer anio;
    private String sinopsis;
    private Date fechaEstreno;
    private Integer duracionMinutos;
    private BigDecimal financiamiento;
    private Boolean isActive;
    private Pais paisId;
    private Set<VentaPelicula> ventaPeliculas;
    
    public Pelicula() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

	public String getNameOriginal() {
		return nameOriginal;
	}

	public void setNameOriginal(String nameOriginal) {
		this.nameOriginal = nameOriginal;
	}

	public String getTipoGuion() {
		return tipoGuion;
	}

	public void setTipoGuion(String tipoGuion) {
		this.tipoGuion = tipoGuion;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public String getSinopsis() {
		return sinopsis;
	}

	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}

	public Date getFechaEstreno() {
		return fechaEstreno;
	}

	public void setFechaEstreno(Date fechaEstreno) {
		this.fechaEstreno = fechaEstreno;
	}

	public Integer getDuracionMinutos() {
		return duracionMinutos;
	}

	public void setDuracionMinutos(Integer duracionMinutos) {
		this.duracionMinutos = duracionMinutos;
	}

	public BigDecimal getFinanciamiento() {
		return financiamiento;
	}

	public void setFinanciamiento(BigDecimal financiamiento) {
		this.financiamiento = financiamiento;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Pais getPaisId() {
		return paisId;
	}

	public void setPaisId(Pais paisId) {
		this.paisId = paisId;
	}

	public Set<VentaPelicula> getVentaPeliculas() {
		return ventaPeliculas;
	}

	public void setVentaPeliculas(Set<VentaPelicula> ventaPeliculas) {
		this.ventaPeliculas = ventaPeliculas;
	}

    
}
