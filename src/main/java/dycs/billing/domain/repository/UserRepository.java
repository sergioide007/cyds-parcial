package dycs.billing.domain.repository;

import java.util.List;

import dycs.billing.domain.entity.User;

public interface UserRepository {
    List<User> getAll();
    User find(String name);
}