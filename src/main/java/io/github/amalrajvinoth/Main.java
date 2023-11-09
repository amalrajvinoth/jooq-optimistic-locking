package io.github.amalrajvinoth;

import java.math.BigDecimal;
import java.util.UUID;

public class Main {
  public static void main(String[] args) {
    Datasource datasource = new Datasource(
        System.getProperty("DB_USER"),
        System.getProperty("DB_PASSWORD"),
        System.getProperty("DB_URL"));
    
    JooqUtil util = new JooqUtil(datasource);
    AccountService accountService = new AccountService(util.getDslContext());
    
    String accountId = UUID.randomUUID().toString();
    System.out.println(accountService.createAccount(accountId, new BigDecimal("100.00")));
    
    System.out.println(accountService.getAccount(accountId));
    
    System.out.println(accountService.getAllAccount());
  }
}
