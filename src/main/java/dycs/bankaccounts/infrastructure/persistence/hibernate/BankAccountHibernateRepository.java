package dycs.bankaccounts.infrastructure.persistence.hibernate;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dycs.bankaccounts.domain.entity.BankAccount;
import dycs.bankaccounts.domain.repository.BankAccountRepository;
import dycs.bankaccounts.infrastructure.persistence.jooq.BankAccountBatchJooqRepository;
import dycs.common.infrastructure.persistence.hibernate.BaseHibernateRepository;

@Transactional(rollbackOn=Exception.class)
@Repository
public class BankAccountHibernateRepository extends BaseHibernateRepository<BankAccount> implements BankAccountRepository {
	@Autowired
	BankAccountBatchJooqRepository bankAccountBatchJooqRepository;
	
	public BankAccountHibernateRepository() {
		super(BankAccount.class);
	}
}
