package com.DyVert.DyVert.User;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAllUsers();
    }

    public void deleteUser(String accountID) {
         userRepository.deleteUser(accountID);
    }
}
