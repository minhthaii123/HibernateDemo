package com.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.UserDAO;
import com.entities.User;


@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserDAO userDAO;
    
    public void saveUser(User user) {
        userDAO.save(user);
    }
    
    public User findById(Integer id) {
        return userDAO.findById(id);
    }
    
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }
    
    public List<User> findAllUsers() {
        return userDAO.findAllUsers();
    }
    
    public List<User> findAll() {
        return userDAO.findAll();
    }
    
    public void deleteUser(Integer id) {
        userDAO.delete(id);
    }
    
    public boolean authenticate(String username, String password) {
        User user = userDAO.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
    
    public User login(String username, String password) {
        if (authenticate(username, password)) {
            return userDAO.findByUsername(username);
        }
        return null;
    }
}