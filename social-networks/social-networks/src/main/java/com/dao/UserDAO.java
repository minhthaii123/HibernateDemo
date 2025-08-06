package com.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entities.User;

@Repository
public class UserDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(user);
    }
    
    public User findById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, id);
    }
    
    public User findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("FROM User WHERE username = :username", User.class);
        query.setParameter("username", username);
        return query.uniqueResult();
    }
    
    public List<User> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("FROM User", User.class);
        return query.list();
    }
    
    public List<User> findAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("FROM User WHERE role = 'USER'", User.class);
        return query.list();
    }
    
    public void delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        if (user != null) {
            session.remove(user);
        }
    }
}
