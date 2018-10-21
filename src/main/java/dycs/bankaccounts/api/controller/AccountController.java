package dycs.bankaccounts.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Isolation;
import dycs.bankaccounts.application.assembler.BankAccountCreateAssembler;
import dycs.bankaccounts.application.dto.BankAccountCreateDto;
import dycs.bankaccounts.application.validation.AccountCreateValidation;
import dycs.bankaccounts.domain.entity.BankAccount;
import dycs.bankaccounts.domain.repository.BankAccountBatchRepository;
import dycs.bankaccounts.domain.repository.BankAccountRepository;
import dycs.common.application.ApiResponseHandler;
import dycs.common.application.UnitOfWork;
import dycs.common.domain.valueobject.Money;
import dycs.common.domain.valueobject.MoneyAbstraction;

@RestController
@RequestMapping("v1/customers/{customerId}/accounts")
public class AccountController {
	@Autowired
	UnitOfWork unitOfWork;
	
	@Autowired
	BankAccountRepository bankAccountRepository;
	
	@Autowired
	BankAccountBatchRepository bankAccountBatchRepository;
	
	@Autowired
	AccountCreateValidation accountCreateValidation;
	
	@Autowired
	BankAccountCreateAssembler bankAccountCreateAssembler;
	
	@Autowired
	ApiResponseHandler apiResponseHandler;
	
	@Transactional(rollbackFor=Exception.class, isolation = Isolation.READ_COMMITTED)
	@RequestMapping(
	    method = RequestMethod.POST,
	    path = "",
	    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
	    produces = MediaType.APPLICATION_JSON_UTF8_VALUE
	)
	public ResponseEntity<Object> create(@PathVariable("customerId") long customerId, @RequestBody BankAccountCreateDto bankAccountCreateDto) throws Exception {
        try {
        	bankAccountCreateDto.setCustomerId(customerId);
        	accountCreateValidation.validate(bankAccountCreateDto);
            BankAccount bankAccount = bankAccountCreateAssembler.toEntity(bankAccountCreateDto);
            bankAccountRepository.create(bankAccount);
            BankAccountCreateDto bankAccountCreateDto2 = bankAccountCreateAssembler.toDto(bankAccount);
            return new ResponseEntity<Object>(bankAccountCreateDto2, HttpStatus.CREATED);
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
	public ResponseEntity<Object> unitOfWork(@PathVariable("customerId") long customerId, @RequestBody List<BankAccountCreateDto> bankAccountCreateListDto) throws Exception {
		boolean status = false;
		try {
			for (BankAccountCreateDto bankAccountCreateDto : bankAccountCreateListDto) {
				bankAccountCreateDto.setCustomerId(customerId);
			}
			List<BankAccount> bankAccountList = bankAccountCreateAssembler.toEntityList(bankAccountCreateListDto);
            status = unitOfWork.beginTransaction();
            long lastId = 0;
            for (BankAccount bankAccount : bankAccountList) {
            	bankAccountRepository.create(bankAccount);
            	lastId = bankAccount.getId();
            }
            BankAccount bankAccount = bankAccountRepository.read(lastId);
        	bankAccountRepository.delete(bankAccount);
            unitOfWork.commit(status);
            return new ResponseEntity<Object>(apiResponseHandler.getApplicationMessage("Bank Accounts were created!"), HttpStatus.CREATED);
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
	public ResponseEntity<Object> batch(@PathVariable("customerId") long customerId, @RequestBody List<BankAccountCreateDto> bankAccountCreateListDto) throws Exception {
		try {
			for (BankAccountCreateDto bankAccountCreateDto : bankAccountCreateListDto) {
				bankAccountCreateDto.setCustomerId(customerId);
			}
			List<BankAccount> bankAccountList = bankAccountCreateAssembler.toEntityList(bankAccountCreateListDto);
			bankAccountBatchRepository.createBatch(bankAccountList);
            return new ResponseEntity<Object>(apiResponseHandler.getApplicationMessage("Bank Accounts were created!"), HttpStatus.CREATED);
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
