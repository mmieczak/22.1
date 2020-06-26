package pl.mmieczak.springbootexercise_22_1;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private List<User> userCollection = new ArrayList<>();

    public UserRepository() {
        userCollection.add(new User("Joe", "Boo", 35));
        userCollection.add(new User("Mac", "Spencer", 25));
        userCollection.add(new User("Tim", "Beam", 45));
    }

    public List<User> getAll() {
        return userCollection;
    }

    public void add(String name, String surname, int age) {
        userCollection.add(new User(name, surname, age));
    }
}
