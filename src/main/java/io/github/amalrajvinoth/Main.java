package io.github.amalrajvinoth;

import java.math.BigDecimal;
import java.util.UUID;

public class Main {

  public static void main(String[] args) {
    String dbUser = System.getProperty("DB_USER");
    String dbPassword = System.getProperty("DB_PASSWORD");
    String dbUrl = System.getProperty("DB_URL");

    Datasource datasource = new Datasource(dbUser, dbPassword, dbUrl);
    JooqUtil util = new JooqUtil(datasource);
    AccountService accountService = new AccountService(util.getDslContext());

    String accountId = UUID.randomUUID().toString();
    BigDecimal initialBalance = new BigDecimal("100.00");

    System.out.println("Creating account with ID: " + accountId);
    System.out.println(accountService.createAccount(accountId, initialBalance));

    System.out.println("Retrieving account details for account ID: " + accountId);
    System.out.println(accountService.getAccount(accountId));

    System.out.println("Retrieving all accounts");
    System.out.println(accountService.getAllAccounts());
  }
}
