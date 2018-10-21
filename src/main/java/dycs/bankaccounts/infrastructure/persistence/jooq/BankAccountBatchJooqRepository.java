package dycs.bankaccounts.infrastructure.persistence.jooq;

import java.util.List;
import javax.transaction.Transactional;
import org.jooq.BatchBindStep;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import dycs.bankaccounts.domain.entity.BankAccount;
import dycs.bankaccounts.domain.repository.BankAccountBatchRepository;

@Transactional(rollbackOn=Exception.class)
@Repository
public class BankAccountBatchJooqRepository implements BankAccountBatchRepository {
	@Autowired
	DSLContext dsl;
	
	@Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
	protected String HIBERNATE_JDBC_BATCH_SIZE;
	
	public BankAccountBatchJooqRepository() {
	}
	
	public void createBatch(List<BankAccount> bankAccountList) {
		String sql = "INSERT INTO bank_account(number, balance, currency, locked, customer_id) VALUES(?, ?, ?, ?, ?)";
		BatchBindStep batch = dsl.batch(sql);
		long i = 0;
		for (BankAccount bankAccount : bankAccountList) {
			if (i % Integer.valueOf(HIBERNATE_JDBC_BATCH_SIZE) == 0 && i > 0) {
				batch.execute();
				batch = dsl.batch(sql);
			}
		    batch.bind(bankAccount.getNumber(), bankAccount.getBalance().getAmount(), bankAccount.getBalance().getCurrency().getCurrencyCode(), bankAccount.getIsLocked(), bankAccount.getCustomer().getId());
		    i++;
		}
		batch.execute();
	}
}
