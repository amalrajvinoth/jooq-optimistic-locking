package io.github.amalrajvinoth;

import org.jooq.exception.IntegrityConstraintViolationException;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

  private static AccountService accountService;

  private static final String accountId = UUID.randomUUID().toString();
  private static final BigDecimal initialBalance = new BigDecimal("100.00");

  @BeforeAll
  public static void setUp() {
      var dbUser = System.getProperty("DB_USER");
      var dbPassword = System.getProperty("DB_PASSWORD");
      var dbUrl = System.getProperty("DB_URL");

      var datasource = new Datasource(dbUser, dbPassword, dbUrl);
      var util = new JooqUtil(datasource);
    accountService = new AccountService(util.getDslContext());
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
      var incrementAmount = new BigDecimal("50.00");
    accountService.incrementBalance(accountId, incrementAmount);

    var retrievedAccount = accountService.getAccount(accountId);
    assertNotNull(retrievedAccount);
    assertEquals(0, initialBalance.add(incrementAmount).compareTo(retrievedAccount.getBalance()));
  }

  @Test
  public void decrementBalance_insufficientBalance() {
      var decrementAmount = new BigDecimal("150.00");
    assertThrows(InsufficientBalanceException.class, () -> accountService.decrementBalance(accountId, decrementAmount));
  }

  @Test
  public void deleteByAccount_validAccount() {
      var deleted = accountService.deleteByAccount(accountId);
    assertEquals(1, deleted);

    var deletedAccount = accountService.getAccount(accountId);
    assertNull(deletedAccount);

    accountService.createAccount(accountId, initialBalance);
  }

  @Test
  public void deleteByAccount_invalidAccount() {
      var invalidAccountId = UUID.randomUUID().toString();

    assertEquals(0, accountService.deleteByAccount(invalidAccountId));
  }

}
