import java.util.ArrayList;

public class Mediator {
    ArrayList<User> users;

    public Mediator(){
        users = new ArrayList<>(0);
    }

    public void addUser(User user){
        users.add(user);
    }
}
