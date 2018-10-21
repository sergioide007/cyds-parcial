package dycs.bankaccounts.domain.repository;

import java.util.List;

import dycs.bankaccounts.domain.entity.BankAccount;

public interface BankAccountBatchRepository {
	public void createBatch(List<BankAccount> bankAccountList);
}
