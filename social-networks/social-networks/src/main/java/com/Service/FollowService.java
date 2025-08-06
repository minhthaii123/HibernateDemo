package com.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.FollowDAO;
import com.entities.Follow;
import com.entities.FollowId;
import com.entities.User;

@Service
@Transactional
public class FollowService {

    @Autowired
    private FollowDAO followDAO;

    public void followUser(Integer followingUserId, Integer followedUserId) {
        try {
            Follow existingFollow = followDAO.findByFollowingAndFollowed(followingUserId, followedUserId);
            if (existingFollow == null) {
                // Tạo Follow entity đúng cách
                Follow follow = new Follow(followingUserId, followedUserId);
                // Không cần set followingUserId và followedUserId vì chúng đã được set thông qua constructor
                followDAO.save(follow);
                System.out.println("Successfully followed user: " + followingUserId + " -> " + followedUserId);
            } else {
                System.out.println("User already followed: " + followingUserId + " -> " + followedUserId);
            }
        } catch (Exception e) {
            System.err.println("Error in followUser: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to follow user", e);
        }
    }

    public void unfollowUser(Integer followingUserId, Integer followedUserId) {
        try {
            followDAO.delete(followingUserId, followedUserId);
            System.out.println("Successfully unfollowed user: " + followingUserId + " -> " + followedUserId);
        } catch (Exception e) {
            System.err.println("Error in unfollowUser: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to unfollow user", e);
        }
    }

    public boolean isFollowing(Integer followingUserId, Integer followedUserId) {
        try {
            Follow follow = followDAO.findByFollowingAndFollowed(followingUserId, followedUserId);
            boolean result = follow != null;
            System.out.println("IsFollowing check: " + followingUserId + " -> " + followedUserId + " = " + result);
            return result;
        } catch (Exception e) {
            System.err.println("Error in isFollowing: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<User> getFollowing(Integer userId) {
        try {
            return followDAO.findFollowing(userId);
        } catch (Exception e) {
            System.err.println("Error getting following list: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to get following list", e);
        }
    }

    public List<User> getFollowers(Integer userId) {
        try {
            return followDAO.findFollowers(userId);
        } catch (Exception e) {
            System.err.println("Error getting followers list: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to get followers list", e);
        }
    }

    public long getFollowersCount(Integer userId) {
        try {
            return followDAO.countFollowers(userId);
        } catch (Exception e) {
            System.err.println("Error getting followers count: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    public long getFollowingCount(Integer userId) {
        try {
            return followDAO.countFollowing(userId);
        } catch (Exception e) {
            System.err.println("Error getting following count: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
}