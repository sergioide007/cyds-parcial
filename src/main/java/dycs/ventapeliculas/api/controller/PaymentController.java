package dycs.ventapeliculas.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dycs.common.application.ApiResponseHandler;
import dycs.common.application.UnitOfWork;
import dycs.common.domain.valueobject.Money;
import dycs.common.domain.valueobject.MoneyAbstraction;
import dycs.ventapeliculas.application.assembler.VentaPeliculaCreateAssembler;
import dycs.ventapeliculas.application.dto.VentaPeliculaCreateDto;
import dycs.ventapeliculas.application.validation.AccountPaymentCreateValidation;
import dycs.ventapeliculas.domain.entity.VentaPelicula;
import dycs.ventapeliculas.domain.repository.VentaPeliculaBatchRepository;
import dycs.ventapeliculas.domain.repository.VentaPeliculaRepository;

@RestController
@RequestMapping("v1/customers/{customerId}/films/{filmId}/payments")
public class PaymentController {
	@Autowired
	UnitOfWork unitOfWork;
	
	@Autowired
	VentaPeliculaRepository ventaPeliculaRepository;
	
	@Autowired
	VentaPeliculaBatchRepository ventaPeliculaBatchRepository;
	
	@Autowired
	AccountPaymentCreateValidation accountCreateValidation;
	
	@Autowired
	VentaPeliculaCreateAssembler ventaPeliculaCreateAssembler;
	
	@Autowired
	ApiResponseHandler apiResponseHandler;
	
	@Transactional(rollbackFor=Exception.class, isolation = Isolation.READ_COMMITTED)
	@RequestMapping(
	    method = RequestMethod.POST,
	    path = "",
	    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
	    produces = MediaType.APPLICATION_JSON_UTF8_VALUE
	)
	public ResponseEntity<Object> create(@PathVariable("customerId") long customerId, @PathVariable("filmId") long filmId, @RequestBody VentaPeliculaCreateDto ventaPeliculaCreateDto) throws Exception {
        try {
        	ventaPeliculaCreateDto.setCustomerId(customerId);
        	ventaPeliculaCreateDto.setPeliculaId(filmId);
        	accountCreateValidation.validate(ventaPeliculaCreateDto);
        	VentaPelicula ventaPelicula = ventaPeliculaCreateAssembler.toEntity(ventaPeliculaCreateDto);
            ventaPeliculaRepository.create(ventaPelicula);
            VentaPeliculaCreateDto ventaPeliculaCreateDto2 = ventaPeliculaCreateAssembler.toDto(ventaPelicula);
            return new ResponseEntity<Object>(ventaPeliculaCreateDto2, HttpStatus.CREATED);
        } catch(IllegalArgumentException ex) {
        	ex.printStackTrace();
        	return new ResponseEntity<Object>(apiResponseHandler.getApplicationError(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<Object>(apiResponseHandler.getApplicationException(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@RequestMapping(
	    method = RequestMethod.POST,
	    path = "/unit-of-work",
	    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
	    produces = MediaType.APPLICATION_JSON_UTF8_VALUE
	)
	public ResponseEntity<Object> unitOfWork(@PathVariable("customerId") long customerId, @PathVariable("filmId") long filmId, @RequestBody List<VentaPeliculaCreateDto> ventaPeliculaCreateListDto) throws Exception {
		boolean status = false;
		try {
			for (VentaPeliculaCreateDto ventaPeliculaCreateDto : ventaPeliculaCreateListDto) {
				ventaPeliculaCreateDto.setCustomerId(customerId);
				ventaPeliculaCreateDto.setPeliculaId(filmId);
			}
			List<VentaPelicula> ventaPeliculaList = ventaPeliculaCreateAssembler.toEntityList(ventaPeliculaCreateListDto);
            status = unitOfWork.beginTransaction();
            long lastId = 0;
            for (VentaPelicula ventaPelicula : ventaPeliculaList) {
            	ventaPeliculaRepository.create(ventaPelicula);
            	lastId = ventaPelicula.getId();
            }
            VentaPelicula ventaPelicula = ventaPeliculaRepository.read(lastId);
        	ventaPeliculaRepository.delete(ventaPelicula);
            unitOfWork.commit(status);
            return new ResponseEntity<Object>(apiResponseHandler.getApplicationMessage("Movie Sales were created!"), HttpStatus.CREATED);
        } catch(IllegalArgumentException ex) {
        	unitOfWork.rollback(status);
        	return new ResponseEntity<Object>(apiResponseHandler.getApplicationError(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
        	unitOfWork.rollback(status);
			return new ResponseEntity<Object>(apiResponseHandler.getApplicationException(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@RequestMapping(
	    method = RequestMethod.POST,
	    path = "/batch",
	    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
	    produces = MediaType.APPLICATION_JSON_UTF8_VALUE
	)
	public ResponseEntity<Object> batch(@PathVariable("customerId") long customerId, @RequestBody List<VentaPeliculaCreateDto> ventaPeliculaCreateListDto) throws Exception {
		try {
			for (VentaPeliculaCreateDto bankAccountCreateDto : ventaPeliculaCreateListDto) {
				bankAccountCreateDto.setCustomerId(customerId);
			}
			List<VentaPelicula> ventaPeliculaList = ventaPeliculaCreateAssembler.toEntityList(ventaPeliculaCreateListDto);
			ventaPeliculaBatchRepository.createBatch(ventaPeliculaList);
            return new ResponseEntity<Object>(apiResponseHandler.getApplicationMessage("Movie Sales were created!"), HttpStatus.CREATED);
        } catch(IllegalArgumentException ex) {
        	ex.printStackTrace();
        	return new ResponseEntity<Object>(apiResponseHandler.getApplicationError(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
        	ex.printStackTrace();
			return new ResponseEntity<Object>(apiResponseHandler.getApplicationException(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@RequestMapping(
		method = RequestMethod.POST,
		path = "/null-object",
		produces = MediaType.APPLICATION_JSON_UTF8_VALUE
	)
	public ResponseEntity<Object> nullObject() throws Exception {
        try {
            Money money1 = Money.dollars(1850.74);
            Money money2 = Money.soles(1850.75);
            StringBuilder message = new StringBuilder();
            message.append("equals: " + money1.equals(money2));
            MoneyAbstraction money = money2.subtract(money1);
            message.append(" ** " + money.getCurrencyAsString() + ": " + money.getAmount());
            message.append(" ** " + (money.isNull() ? "Null Object - different currencies" : "Real Object"));
            return new ResponseEntity<Object>(apiResponseHandler.getApplicationMessage(message.toString()), HttpStatus.OK);
        } catch(IllegalArgumentException ex) {
        	ex.printStackTrace();
        	return new ResponseEntity<Object>(apiResponseHandler.getApplicationError(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<Object>(apiResponseHandler.getApplicationException(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
}
