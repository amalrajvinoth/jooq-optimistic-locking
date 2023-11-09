package io.github.amalrajvinoth;

import static schema.jooq.tables.Accounts.ACCOUNTS;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import org.jooq.DSLContext;
import schema.jooq.tables.records.AccountsRecord;

public class AccountService {

  private DSLContext dslContext;
  
  public AccountService(DSLContext dslContext) {
    this.dslContext = dslContext;
  }

  public List<AccountsRecord> getAllAccount() {
    return this.dslContext.select().from(ACCOUNTS).fetch().into(AccountsRecord.class);
  }
  
  public AccountsRecord getAccount(String accountId) {
    return this.dslContext.fetchOne(ACCOUNTS,
        ACCOUNTS.ID.eq(accountId));
  }

  public AccountsRecord createAccount(String accountId, BigDecimal balance) {
    AccountsRecord insert = new AccountsRecord();
    insert.set(ACCOUNTS.ID, accountId);
    insert.set(ACCOUNTS.BALANCE, balance);
    insert.set(ACCOUNTS.VERSION, 1);
    insert.set(ACCOUNTS.CREATED, OffsetDateTime.now());
    insert.set(ACCOUNTS.UPDATED, OffsetDateTime.now());
    AccountsRecord accountsRecord = this.dslContext.newRecord(ACCOUNTS, insert);
    this.dslContext.insertInto(ACCOUNTS)
        .set(accountsRecord)
        .execute();
    return insert;
  }
}
