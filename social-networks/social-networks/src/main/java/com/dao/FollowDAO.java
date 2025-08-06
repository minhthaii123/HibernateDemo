package com.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entities.Follow;
import com.entities.FollowId;
import com.entities.User;

@Repository
public class FollowDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void save(Follow follow) {
        try {
            getCurrentSession().merge(follow);
            System.out.println("Follow saved successfully: " + follow.getFollowingUserId() + " -> " + follow.getFollowedUserId());
        } catch (Exception e) {
            System.err.println("Error saving follow: " + e.getMessage());
            throw e;
        }
    }

    public Follow findByFollowingAndFollowed(Integer followingUserId, Integer followedUserId) {
        try {
            FollowId followId = new FollowId(followingUserId, followedUserId);
            Follow result = getCurrentSession().get(Follow.class, followId);
            System.out.println("Find follow result: " + (result != null ? "Found" : "Not found"));
            return result;
        } catch (Exception e) {
            System.err.println("Error finding follow: " + e.getMessage());
            throw e;
        }
    }

    public List<User> findFollowing(Integer userId) {
        String hql = "SELECT u FROM User u JOIN Follow f ON u.id = f.id.followedUserId WHERE f.id.followingUserId = :userId";
        Query<User> query = getCurrentSession().createQuery(hql, User.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public List<User> findFollowers(Integer userId) {
        String hql = "SELECT u FROM User u JOIN Follow f ON u.id = f.id.followingUserId WHERE f.id.followedUserId = :userId";
        Query<User> query = getCurrentSession().createQuery(hql, User.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public void delete(Integer followingUserId, Integer followedUserId) {
        try {
            FollowId followId = new FollowId(followingUserId, followedUserId);
            Follow follow = getCurrentSession().get(Follow.class, followId);
            if (follow != null) {
                getCurrentSession().remove(follow); // Đổi từ delete() thành remove()
                System.out.println("Follow deleted successfully: " + followingUserId + " -> " + followedUserId);
            } else {
                System.out.println("Follow not found for delete: " + followingUserId + " -> " + followedUserId);
            }
        } catch (Exception e) {
            System.err.println("Error deleting follow: " + e.getMessage());
            throw e;
        }
    }

    public long countFollowers(Integer userId) {
        String hql = "SELECT COUNT(*) FROM Follow WHERE id.followedUserId = :userId";
        Query<Long> query = getCurrentSession().createQuery(hql, Long.class);
        query.setParameter("userId", userId);
        return query.uniqueResult();
    }

    public long countFollowing(Integer userId) {
        String hql = "SELECT COUNT(*) FROM Follow WHERE id.followingUserId = :userId";
        Query<Long> query = getCurrentSession().createQuery(hql, Long.class);
        query.setParameter("userId", userId);
        return query.uniqueResult();
    }
}