package io.github.amalrajvinoth;

import java.math.BigDecimal;
import java.util.UUID;

public class Main {

  public static void main(String[] args) {
    var dbUser = System.getProperty("DB_USER");
    var dbPassword = System.getProperty("DB_PASSWORD");
    var dbUrl = System.getProperty("DB_URL");

    var datasource = new Datasource(dbUser, dbPassword, dbUrl);
    var util = new JooqUtil(datasource);
    var accountService = new AccountService(util.getDslContext());

    var accountId = UUID.randomUUID().toString();
    var initialBalance = new BigDecimal("100.00");

    System.out.println("Creating account with ID: " + accountId);
    System.out.println(accountService.createAccount(accountId, initialBalance));

    System.out.println("Retrieving account details for account ID: " + accountId);
    System.out.println(accountService.getAccount(accountId));

    System.out.println("Retrieving all accounts");
    System.out.println(accountService.getAllAccounts());

    System.out.println("Incrementing 10 to account with ID: " + accountId);
    System.out.println(accountService.incrementBalance(accountId, BigDecimal.TEN));

    System.out.println("Decrementing 10 from account with ID: " + accountId);
    System.out.println(accountService.decrementBalance(accountId, BigDecimal.TEN));
  }
}
