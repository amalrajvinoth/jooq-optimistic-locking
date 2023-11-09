# jOOQ Optimistic Locking with Spring

This repository demonstrates the integration of jOOQ's powerful querying capabilities with Spring's robust framework, specifically focusing on implementing optimistic locking strategies. The goal is to showcase how to leverage jOOQ's expressive DSL for building type-safe SQL queries and Spring's extensive features for building enterprise Java applications to implement efficient optimistic locking mechanisms.

## Key Features

- Seamless integration of jOOQ and Spring for efficient database operations
- Implementation of optimistic locking strategies to manage concurrent data access
- Detailed examples and best practices for incorporating jOOQ and Spring functionalities
- Comprehensive documentation and code samples for easy understanding and implementation

## Installation

1. Clone the repository: `git clone https://github.com/amalrajvinoth/jooq-optimistic-locking.git`
2. DB Migrations 
   1. To first clean the database and then run all the migrations, run the following command:
    ```shell
    ./mvnw -f pom.xml flyway:clean flyway:migrate  \
      -Djooq.skip.generation=true \
      -DDATASOURCE_HOST=127.0.0.1 \
      -DDATASOURCE_PORT=5432 \
      -DDATASOURCE_USER=postgres \
      -DDATASOURCE_DATABASE_NAME=postgres \
      -DDATASOURCE_PASSWORD=password
    ```
3. Generate jOOq classes
    ```shell
    $ ./mvnw -f pom.xml jooq-codegen:generate  \
    -Djooq.skip.generation=false \
    -DDATASOURCE_HOST=127.0.0.1 \
    -DDATASOURCE_PORT=5432 \
    -DDATASOURCE_USER=postgres \
    -DDATASOURCE_DATABASE_NAME=postgres \
    -DDATASOURCE_PASSWORD=password
    ```
4. Run Main
   ```shell
    mvn compile exec:java -Dexec.mainClass="io.github.amalrajvinoth.Main" -DDB_PASSWORD=pzS5dRWDUc7bu8Kr -DDB_URL="jdbc:postgresql://127.0.0.1:54320/test" -DDB_USER=postgres
   ```


## Usage

Follow the guidelines in the [usage documentation](https://www.jooq.org/doc/latest/manual/getting-started/tutorials/jooq-in-7-steps/jooq-in-7-steps-step1/) to understand how to use the jOOQ and Spring integration with optimistic locking.
