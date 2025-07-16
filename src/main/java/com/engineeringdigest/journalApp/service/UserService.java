package com.engineeringdigest.journalApp.service;

import com.engineeringdigest.journalApp.entity.User;
import com.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Save an Entry in the database
    public void saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);
    }
    public void saveUser(User user) {
        userRepository.save(user);
    }

    // Get all the Entries from the database
    public List<User> getAll() {
        List<User> allUsers = userRepository.findAll();
        return allUsers;
    }

    // Find Entry by Id
    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    // Find by username
    public User findByUsername(String username) {
        return userRepository.findByUserName(username);
    }


    // Delete Entry by Id
    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }

    // Delete User by UserName
    public void deleteByUserName(String userName) {
        userRepository.deleteByUserName(userName);
    }
}


//controller -> service -> repository --extends--> MongoRepository<>
