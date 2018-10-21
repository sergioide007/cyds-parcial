package dycs.ventapeliculas.application.validation;

import org.springframework.stereotype.Component;

import dycs.common.application.notification.Notification;
import dycs.ventapeliculas.application.dto.VentaPeliculaCreateDto;

@Component
public class AccountPaymentCreateValidation {
	public void validate(VentaPeliculaCreateDto bankAccountCreateDto) {
		Notification notification = this.validateData(bankAccountCreateDto);
		if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }
	}
	
	public Notification validateData(VentaPeliculaCreateDto bankAccountCreateDto) {
		Notification notification = new Notification();
		if (bankAccountCreateDto == null) {
			notification.addError("Missing VentaPelicula parameters");
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
