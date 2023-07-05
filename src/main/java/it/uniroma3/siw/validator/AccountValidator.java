package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Account;
import it.uniroma3.siw.repository.AccountRepository;

@Component
public class AccountValidator  implements Validator{
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public boolean supports(Class<?> aClass) {
		return Account.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Account account = (Account) o;
		if(!this.accountRepository.existsByUsername(account.getUsername())) {
			errors.reject("account.duplicate");
		}
		if(!this.accountRepository.existsByUsernameAndPassword(account.getUsername(), account.getPassword())) {
			errors.reject("account.wrongPass");
		}
	}
}