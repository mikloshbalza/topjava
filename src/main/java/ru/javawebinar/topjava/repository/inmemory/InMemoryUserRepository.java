package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Map<Integer, User> userMap = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    @Override
    public boolean delete(int id) {
        if (userMap.remove(id) != null) {
            log.info("delete {}", id);
            return true;
        }
        return false;
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            log.info("save {}", user);
            user.setId(counter.incrementAndGet());
            userMap.put(user.getId(), user);
            return user;
        }
        log.info("update {}", user);
        return userMap.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return userMap.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return userMap.values().stream()
                .sorted(Comparator.comparing(User::getEmail)
                        .thenComparing(User::getName))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return userMap.values().stream()
                .filter(user -> email.equals(user.getEmail()))
                .findFirst()
                .orElse(null);
    }
}
