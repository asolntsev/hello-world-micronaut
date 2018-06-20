package hello;

import hello.domain.JPAListener;
import hello.world.DatabaseMigrator;
import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.Micronaut;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = Micronaut.run(Application.class);
        context.registerSingleton(new JPAListener());
        context.findBean(DatabaseMigrator.class).orElseThrow(RuntimeException::new).doRun();
    }
}
