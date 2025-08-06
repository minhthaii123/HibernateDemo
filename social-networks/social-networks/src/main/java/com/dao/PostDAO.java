package com.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entities.Post;

@Repository
public class PostDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void save(Post post) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(post);
    }
    
    public Post findById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Post.class, id);
    }
    
    public List<Post> findByUserId(Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Post> query = session.createQuery("FROM Post WHERE userId = :userId ORDER BY createdAt DESC", Post.class);
        query.setParameter("userId", userId);
        return query.list();
    }
    
    public List<Post> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Post> query = session.createQuery("FROM Post ORDER BY createdAt DESC", Post.class);
        return query.list();
    }
    
    public List<Post> findActivePostsByFollowedUsers(Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT p FROM Post p JOIN Follow f ON p.userId = f.id.followedUserId " +
                "WHERE f.id.followingUserId = :userId AND p.status = 'ACTIVE' ORDER BY p.createdAt DESC";
        Query<Post> query = session.createQuery(hql, Post.class);
        query.setParameter("userId", userId);
        return query.list();
    }
    
    public void delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Post post = session.get(Post.class, id);
        if (post != null) {
            session.remove(post);
        }
    }
    
    public void update(Post post) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(post);
    }
}