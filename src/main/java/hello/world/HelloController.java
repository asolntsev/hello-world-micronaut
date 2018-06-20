package hello.world;

import hello.domain.User;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.spring.tx.annotation.Transactional;
import io.reactivex.Single;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller("/hello")
public class HelloController {
    @Inject
    private DataSource dataSource;

    @Inject private SessionFactory sessionFactory;

    @Get("/users")
    @Produces
    public MutableHttpResponse<Single<List<User>>> index() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            ResultSet result = connection.prepareStatement("select first_name, last_name from users").executeQuery();
            while (result.next()) {
                users.add(new User(result.getString("first_name"), result.getString("last_name")));
            }
            return HttpResponse.created(Single.just(users));
        }
    }

    @Post("/users")
    public Single<MutableHttpResponse<User>> save(@Body Single<User> person) {
        return person.map(p -> HttpResponse.created(insert(p)));
    }

    @Post("/users-jpa")
    @Transactional
    public Single<MutableHttpResponse<User>> saveJpa(@Body Single<User> person) {
        return person.map(p -> HttpResponse.created(insertJpa(p)));
    }

    User insert(User p) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("insert into users(first_name, last_name) values (?,?)");
            statement.setString(1, p.firstname);
            statement.setString(2, p.lastname);
            statement.execute();
            return p;
        }

    }

    User insertJpa(User p) {
        sessionFactory.getCurrentSession().persist(p);
        return p;
    }
}