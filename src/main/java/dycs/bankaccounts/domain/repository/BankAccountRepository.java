package dycs.bankaccounts.domain.repository;

import java.sql.SQLException;

import dycs.bankaccounts.domain.entity.BankAccount;

//Separated Interface Pattern
//https://www.martinfowler.com/eaaCatalog/separatedInterface.html
public interface BankAccountRepository {
	public void create(BankAccount bankAccount) throws SQLException;
	public void delete(BankAccount bankAccount) throws SQLException;
	public BankAccount read(long id) throws SQLException;
}
