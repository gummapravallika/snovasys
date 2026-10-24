# TrustVault — Maven project

This project uses the standard Maven layout:

- `pom.xml` is at the project root.
- Java source: `src/main/java/com/trustvault/`
- Configuration: `src/main/resources/application.properties`
- Thymeleaf templates: `src/main/resources/templates/`
- Static assets: `src/main/resources/static/`
- Tests: `src/test/java/com/trustvault/`

## Run

1. Set environment variables for DB_URL, DB_USER, DB_PASSWORD, TRUSTVAULT_STORAGE, DEMO_ADMIN_PASSWORD, DEMO_OWNER_PASSWORD, and DEMO_VIEWER_PASSWORD.
2. Start MySQL using the provided `docker-compose.yml`, or use your existing MySQL instance.
3. Run from this directory (the directory containing `pom.xml`):

```bash
mvn clean test
mvn spring-boot:run
```

On Windows PowerShell, use `$env:VARIABLE = "value"` to set environment variables.

Never commit real secrets or private documents. This is a hackathon MVP; review and test all security controls before deployment.


## Database tables and JPA relationships

You do **not** need to write `CREATE TABLE` statements manually. With `spring.jpa.hibernate.ddl-auto=update`, Hibernate creates/updates tables from the `@Entity` classes when the application starts. The MySQL database itself must be available; the provided JDBC URL includes `createDatabaseIfNotExist=true`, and Docker Compose also initializes the `trustvault` database.

Relationships are mapped in entities:

- `AppUser` to `VaultDocument`: one-to-many (one employee can own many documents); `VaultDocument.owner` is many-to-one.
- `VaultDocument` to `DocumentPermission`: one-to-many (a document can be shared with many named users).
- `DocumentPermission.document`, `.user`, and `.grantedBy`: many-to-one relationships to the document, recipient employee, and granting employee.

The `document_permissions` table is the join/association entity because each share has its own `accessLevel`, `grantedAt`, and `grantedBy`. A one-to-one relationship is not artificially added where the employee/document domain does not need one.

For production, use Flyway or Liquibase migrations rather than relying on `ddl-auto=update`.
