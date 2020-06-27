package pl.mmieczak.springbootexercise_22_1;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public void removeAll(Stream<User> streamToRemove) {
        userCollection.removeAll(streamToRemove.collect(Collectors.toList()));
    }

    public Stream<User> filterDataUsingFormValues(String name, String surname, String age){
        Stream<User> userStream = userCollection.stream();
        if (!name.equals("") && surname.equals("") && age.equals("-1")) {           //100
            userStream = userStream
                    .filter(User -> User.getName().equals(name));
        } else if (name.equals("") && !surname.equals("") && age.equals("-1")) {    //010
            userStream = userStream
                    .filter(User -> User.getSurname().equals(surname));
        } else if (!name.equals("") && !surname.equals("") && age.equals("-1")) {   //110
            userStream = userStream
                    .filter(User -> User.getSurname().equals(surname))
                    .filter(User -> User.getName().equals(name));
        } else if (!name.equals("") && surname.equals("") && !age.equals("-1")) {   //101
            userStream = userStream
                    .filter(User -> User.getName().equals(name))
                    .filter(User -> User.getAge() == Integer.valueOf(age));
        } else if (name.equals("") && !surname.equals("") && !age.equals("-1")) {   //011
            userStream = userStream
                    .filter(User -> User.getSurname().equals(surname))
                    .filter(User -> User.getAge() == Integer.valueOf(age));
        }else if (!name.equals("") && !surname.equals("") && !age.equals("-1")) {   //111
            userStream = userStream
                    .filter(User -> User.getName().equals(name))
                    .filter(User -> User.getSurname().equals(surname))
                    .filter(User -> User.getAge() == Integer.valueOf(age));
        }else if (name.equals("") && surname.equals("") && !age.equals("-1")) {     //001
            userStream = userStream
                    .filter(User -> User.getAge() == Integer.valueOf(age));
        }
        return  userStream;
    }
}
