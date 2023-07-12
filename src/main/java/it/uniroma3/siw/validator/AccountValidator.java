package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Account;
import it.uniroma3.siw.service.AccountService;

@Component
public class AccountValidator  implements Validator{

	@Autowired
	private AccountService accountService;

	@Override
	public boolean supports(Class<?> aClass) {
		return Account.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Account account = (Account) o;
		if(account != null && this.accountService.isPresentByUsername(account.getUsername())) {
			errors.reject("account.duplicate");
		}
	}
}