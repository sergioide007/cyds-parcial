package dycs.bankaccounts.application.assembler;

import java.util.List;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dycs.bankaccounts.application.dto.BankAccountCreateDto;
import dycs.bankaccounts.domain.entity.BankAccount;
import dycs.common.domain.valueobject.Money;
import dycs.common.domain.valueobject.MoneyAbstraction;
import dycs.common.infrastructure.persistence.hibernate.UnitOfWorkHibernate;
import dycs.customers.domain.entity.Customer;

@Component
public class BankAccountCreateAssembler {
	@Autowired
	protected UnitOfWorkHibernate unitOfWork;
	
	public BankAccount toEntity(BankAccountCreateDto bankAccountCreateDto) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addConverter(getConverter());
		BankAccount bankAccount = modelMapper.map(bankAccountCreateDto, BankAccount.class);
		return bankAccount;
	}
	
	public List<BankAccount> toEntityList(List<BankAccountCreateDto> bankAccountCreateListDto) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addConverter(getConverter());
		List<BankAccount> bankAccountListDto = modelMapper.map(bankAccountCreateListDto, new TypeToken<List<BankAccount>>() {}.getType());
		return bankAccountListDto;
	}
	
	private Converter<BankAccountCreateDto, BankAccount> getConverter() {
		Converter<BankAccountCreateDto, BankAccount> converter = new Converter<BankAccountCreateDto, BankAccount>() {
		    @Override
		    public BankAccount convert(MappingContext<BankAccountCreateDto, BankAccount> context) {
		    	BankAccountCreateDto bankAccountCreateDto =  BankAccountCreateDto.class.cast(context.getSource());
		    	MoneyAbstraction balance = new Money(bankAccountCreateDto.getBalance(), bankAccountCreateDto.getCurrency());
		        BankAccount bankAccount = new BankAccount();
		        bankAccount.setNumber(bankAccountCreateDto.getNumber());
		        bankAccount.setBalance(balance);
		        Customer customer = new Customer();
		        customer.setId(bankAccountCreateDto.getCustomerId());
		        bankAccount.setCustomer(customer);
		    	return bankAccount;
		    }
		};
		return converter;
	}
	
	public BankAccountCreateDto toDto(BankAccount bankAccount) {
		ModelMapper modelMapper = new ModelMapper();
		PropertyMap<BankAccount, BankAccountCreateDto> map = new PropertyMap<BankAccount, BankAccountCreateDto>() {
		  protected void configure() {
			map().setNumber(source.getNumber());
		    map().setBalance(source.getBalance().getAmount());
		    map().setCurrency(source.getBalance().getCurrencyAsString());
		    map().setLocked(source.getIsLocked());
		    map().setCustomerId(source.getCustomer().getId());
		  }
		};
		modelMapper.addMappings(map);
		BankAccountCreateDto bankAccountCreateDto = modelMapper.map(bankAccount, BankAccountCreateDto.class);
		return bankAccountCreateDto;
	}
}
