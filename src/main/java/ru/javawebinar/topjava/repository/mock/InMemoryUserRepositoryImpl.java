package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private Map<Integer, User> repository=new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        return repository.remove(id)!=null;
    }
    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if(user.isNew()){
            user.setId(counter.getAndIncrement());
        }
        repository.put(user.getId(),user);
        return user;
    }
    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        List<User> users=new ArrayList<>();
        for(Map.Entry<Integer,User>pair:repository.entrySet()){
            users.add(pair.getValue());
        }
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                int result=o1.getName().compareTo(o2.getName());
                return result!=0?result:o1.getEmail().compareTo(o2.getEmail());
            }
        });
        return users;
    }
    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        List<User>users=getAll();
        for(User u:users){
            if(email.equals(u.getEmail()))return u;
        }
        return null;
    }
}
