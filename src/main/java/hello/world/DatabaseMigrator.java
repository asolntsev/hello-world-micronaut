package hello.world;

import org.flywaydb.core.Flyway;

import javax.inject.Inject;
import javax.sql.DataSource;

public class DatabaseMigrator {

    @Inject private DataSource dataSource;

    public void doRun() {
        Flyway flyway = new Flyway();
        flyway.setLocations("/db/migration/all");
        flyway.setDataSource(dataSource);
        flyway.migrate();
    }
}
