package io.github.amalrajvinoth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.UUID;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountServiceTest {

  private static AccountService accountService;

  private static final String accountId = UUID.randomUUID().toString();
  private static final BigDecimal initialBalance = new BigDecimal("100.00");

  @BeforeAll
  public static void setUp() {
    String dbUser = System.getProperty("DB_USER");
    String dbPassword = System.getProperty("DB_PASSWORD");
    String dbUrl = System.getProperty("DB_URL");

    Datasource datasource = new Datasource(dbUser, dbPassword, dbUrl);
    JooqUtil util = new JooqUtil(datasource);
    accountService = new AccountService(util.getDslContext());
  }

  @AfterAll
  public static void tearDown() {
    // Clean up resources if needed
  }

  @BeforeEach
  public void setUpAccount() {
    accountService.createAccount(accountId, initialBalance);
  }

  @AfterEach
  public void cleanAccount() {
    accountService.deleteAllAccounts();
  }
  
  @Test
  public void getAllAccounts() {
    var accounts = accountService.getAllAccounts();
    assertEquals(1, accounts.size());
  }

  @Test
  public void createAndGetAccount_validAccount() {
    var retrievedAccount = accountService.getAccount(accountId);
    assertNotNull(retrievedAccount);
    assertEquals(accountId, retrievedAccount.getId());
    assertEquals(0, initialBalance.compareTo(retrievedAccount.getBalance()));
  }

  @Test
  public void createAccount_existingAccount() {
    assertThrows(IntegrityConstraintViolationException.class, () ->accountService.createAccount(accountId,
        initialBalance));
  }

  @Test
  public void incrementBalance_positiveValue() {
    BigDecimal incrementAmount = new BigDecimal("50.00");
    accountService.incrementBalance(accountId, incrementAmount);

    var retrievedAccount = accountService.getAccount(accountId);
    assertNotNull(retrievedAccount);
    assertEquals(0, initialBalance.add(incrementAmount).compareTo(retrievedAccount.getBalance()));
  }

  @Test
  public void decrementBalance_insufficientBalance() {
    BigDecimal decrementAmount = new BigDecimal("150.00");
    assertThrows(InsufficientBalanceException.class, () -> accountService.decrementBalance(accountId, decrementAmount));
  }

  @Test
  public void deleteByAccount_validAccount() {
    int deleted = accountService.deleteByAccount(accountId);
    assertEquals(1, deleted);

    var deletedAccount = accountService.getAccount(accountId);
    assertNull(deletedAccount);

    accountService.createAccount(accountId, initialBalance);
  }

  @Test
  public void deleteByAccount_invalidAccount() {
    String invalidAccountId = UUID.randomUUID().toString();

    assertEquals(0, accountService.deleteByAccount(invalidAccountId));
  }

}
