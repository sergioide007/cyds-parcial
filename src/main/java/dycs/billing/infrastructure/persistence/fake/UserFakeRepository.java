package dycs.billing.infrastructure.persistence.fake;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import dycs.ApplicationContextHolder;
import dycs.billing.domain.entity.User;
import dycs.billing.domain.entity.UserNull;
import dycs.billing.domain.repository.UserRepository;

@Repository
public class UserFakeRepository implements UserRepository {
    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<User>();
        users.add(new User("User 1"));
        users.add(new User("User 2"));
        users.add(new User("User 3"));
        return users;
    }
    
    public User find(String name) {
        List<User> users = this.getAll();
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return ApplicationContextHolder.getContext().getBean(UserNull.class);
    }
}