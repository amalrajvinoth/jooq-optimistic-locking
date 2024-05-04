package io.github.amalrajvinoth;

import static schema.jooq.tables.Accounts.ACCOUNTS;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import org.jooq.DSLContext;
import schema.jooq.tables.records.AccountsRecord;

public class AccountService {

  private final DSLContext dslContext;

  public AccountService(DSLContext dslContext) {
    this.dslContext = dslContext;
  }

  public List<AccountsRecord> getAllAccounts() {
    return dslContext.selectFrom(ACCOUNTS).fetch();
  }

  public AccountsRecord getAccount(String accountId) {
    return dslContext.selectFrom(ACCOUNTS)
        .where(ACCOUNTS.ID.eq(accountId))
        .fetchOne();
  }

  public AccountsRecord createAccount(String accountId, BigDecimal balance) {
      var insert = dslContext.newRecord(ACCOUNTS);
    insert.setId(accountId);
    insert.setBalance(balance);
    insert.setVersion(1);
      var now = OffsetDateTime.now();
    insert.setCreated(now);
    insert.setUpdated(now);
    insert.insert();
    return insert;
  }

  public AccountsRecord incrementBalance(String accountId, BigDecimal amount) {
      var accountsRecord = getAccount(accountId);
    accountsRecord.setBalance(accountsRecord.getBalance().add(amount));
    accountsRecord.store();
    return accountsRecord;
  }

  public AccountsRecord decrementBalance(String accountId, BigDecimal amount) {
      var accountsRecord = getAccount(accountId);
    if(accountsRecord.getBalance().compareTo(amount) < 0) {
      throw new InsufficientBalanceException("Account:"+accountId+" has insufficient balance");
    }
    accountsRecord.setBalance(accountsRecord.getBalance().subtract(amount));
    accountsRecord.store();
    return accountsRecord;
  }

  public int deleteByAccount(String accountId) {
    return dslContext.deleteFrom(ACCOUNTS)
        .where(ACCOUNTS.ID.eq(accountId))
        .execute();
  }

  public void deleteAllAccounts() {
    dslContext.deleteFrom(ACCOUNTS)
            .execute();
  }
}

