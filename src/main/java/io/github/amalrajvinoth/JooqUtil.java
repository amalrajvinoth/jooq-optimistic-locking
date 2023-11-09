package io.github.amalrajvinoth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class JooqUtil {

  private DSLContext dslContext;

  public JooqUtil(Datasource datasource) {
    Objects.requireNonNull(datasource.dbUrl(), "DB_URL is empty");
    Objects.requireNonNull(datasource.dbUser(), "DB_USER is empty");
    Objects.requireNonNull(datasource.dbPassword(), "DB_PASSWORD is empty");

    try {
      initConnection(datasource);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private void initConnection(Datasource datasource) throws SQLException {
    Connection connection = DriverManager.getConnection(datasource.dbUrl(), datasource.dbUser(),
        datasource.dbPassword());
    this.dslContext = DSL.using(connection, SQLDialect.POSTGRES);
  }

  public DSLContext getDslContext() {
    return dslContext;
  }
}
