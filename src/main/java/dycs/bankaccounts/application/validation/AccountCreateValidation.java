package dycs.bankaccounts.application.validation;

import org.springframework.stereotype.Component;

import dycs.bankaccounts.application.dto.BankAccountCreateDto;
import dycs.common.application.notification.Notification;

@Component
public class AccountCreateValidation {
	public void validate(BankAccountCreateDto bankAccountCreateDto) {
		Notification notification = this.validateData(bankAccountCreateDto);
		if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }
	}
	
	public Notification validateData(BankAccountCreateDto bankAccountCreateDto) {
		Notification notification = new Notification();
		if (bankAccountCreateDto == null) {
			notification.addError("Missing BankAccount parameters");
			return notification;
		}
		if (bankAccountCreateDto.getNumber().trim().isEmpty()) {
			notification.addError("Missing Number parameter");
		}
		if (bankAccountCreateDto.getBalance() == null) {
			notification.addError("Missing Balance parameter");
			return notification;
		}
		if (bankAccountCreateDto.getBalance().doubleValue() <= 0.0) {
			notification.addError("Balance must be grater than zero");
		}
		return notification;
	}
}
