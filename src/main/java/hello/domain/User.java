package hello.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id Long id;
    public String firstname;
    public String lastname;

    public User() {
    }

    public User(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }


}
