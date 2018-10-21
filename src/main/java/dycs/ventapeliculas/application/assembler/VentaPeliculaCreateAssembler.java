package dycs.ventapeliculas.application.assembler;

import java.util.List;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dycs.common.domain.valueobject.Money;
import dycs.common.domain.valueobject.MoneyAbstraction;
import dycs.common.infrastructure.persistence.hibernate.UnitOfWorkHibernate;
import dycs.customers.domain.entity.Customer;
import dycs.pelicula.domain.entity.Pelicula;
import dycs.ventapeliculas.application.dto.VentaPeliculaCreateDto;
import dycs.ventapeliculas.domain.entity.VentaPelicula;

@Component
public class VentaPeliculaCreateAssembler {
	@Autowired
	protected UnitOfWorkHibernate unitOfWork;
	
	public VentaPelicula toEntity(VentaPeliculaCreateDto ventaPeliculaCreateDto) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addConverter(getConverter());
		VentaPelicula ventaPelicula = modelMapper.map(ventaPeliculaCreateDto, VentaPelicula.class);
		return ventaPelicula;
	}
	
	public List<VentaPelicula> toEntityList(List<VentaPeliculaCreateDto> ventaPeliculaCreateListDto) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addConverter(getConverter());
		List<VentaPelicula> ventaPeliculaListDto = modelMapper.map(ventaPeliculaCreateListDto, new TypeToken<List<VentaPelicula>>() {}.getType());
		return ventaPeliculaListDto;
	}
	
	private Converter<VentaPeliculaCreateDto, VentaPelicula> getConverter() {
		Converter<VentaPeliculaCreateDto, VentaPelicula> converter = new Converter<VentaPeliculaCreateDto, VentaPelicula>() {
		    @Override
		    public VentaPelicula convert(MappingContext<VentaPeliculaCreateDto, VentaPelicula> context) {
		    	VentaPeliculaCreateDto ventaPeliculaCreateDto =  VentaPeliculaCreateDto.class.cast(context.getSource());
		    	MoneyAbstraction importe = new Money(ventaPeliculaCreateDto.getBalance(), ventaPeliculaCreateDto.getCurrency());
		    	VentaPelicula ventaPelicula = new VentaPelicula();
		    	ventaPelicula.setNumber(ventaPeliculaCreateDto.getNumber());
		    	ventaPelicula.setImporte(importe);
		        Customer customer = new Customer();
		        customer.setId(ventaPeliculaCreateDto.getCustomerId());
		        ventaPelicula.setCustomer(customer);
		        Pelicula pelicula = new Pelicula();
		        pelicula.setId(ventaPeliculaCreateDto.getPeliculaId());
		        ventaPelicula.setPelicula(pelicula);
		    	return ventaPelicula;
		    }
		};
		return converter;
	}
	
	public VentaPeliculaCreateDto toDto(VentaPelicula ventaPelicula) {
		ModelMapper modelMapper = new ModelMapper();
		PropertyMap<VentaPelicula, VentaPeliculaCreateDto> map = new PropertyMap<VentaPelicula, VentaPeliculaCreateDto>() {
		  protected void configure() {
			map().setNumber(source.getNumber());
		    map().setBalance(source.getImporte().getAmount());
		    map().setCurrency(source.getImporte().getCurrencyAsString());
		    map().setLocked(source.getIsLocked());
		    map().setCustomerId(source.getCustomer().getId());
		    map().setPeliculaId(source.getPelicula().getId());
		  }
		};
		modelMapper.addMappings(map);
		VentaPeliculaCreateDto ventaPeliculaCreateDto = modelMapper.map(ventaPelicula, VentaPeliculaCreateDto.class);
		return ventaPeliculaCreateDto;
	}
}
