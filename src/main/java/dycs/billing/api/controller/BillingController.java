package dycs.billing.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import dycs.billing.domain.abstractions.PurchaseReport;
import dycs.billing.domain.service.PurchaseService;
import dycs.common.application.ApiResponseHandler;

@Transactional(rollbackFor=Exception.class)
@RestController
@RequestMapping("v1/billing")
public class BillingController {
	@Autowired
	ApiResponseHandler apiResponseHandler;
	
	@Autowired
	PurchaseService purchaseService;
	
	@RequestMapping(method = RequestMethod.POST, path = "/special-case", produces = "application/json; charset=UTF-8")
	public ResponseEntity<Object> specialCase() throws Exception {
        try {
        	StringBuilder message = new StringBuilder();
        	PurchaseReport purchase1 = purchaseService.purchase("User 1", "Product 1");
        	PurchaseReport purchase2 = purchaseService.purchase("UserNotFound", "Product 1");
        	PurchaseReport purchase3 = purchaseService.purchase("User 1", "Product Not Found");
        	PurchaseReport purchase4 = purchaseService.purchase("User 1", "Product 3");
        	message.append(purchase1.toUiText());
        	message.append(" | " + purchase2.toUiText());
        	message.append(" | " + purchase3.toUiText());
        	message.append(" | " + purchase4.toUiText());
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
