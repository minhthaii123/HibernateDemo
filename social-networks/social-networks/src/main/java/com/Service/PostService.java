package com.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.PostDAO;
import com.entities.Post;

@Service
@Transactional
public class PostService {
    
    @Autowired
    private PostDAO postDAO;
    
    public void savePost(Post post) {
        postDAO.save(post);
    }
    
    public Post findById(Integer id) {
        return postDAO.findById(id);
    }
    
    public List<Post> findByUserId(Integer userId) {
        return postDAO.findByUserId(userId);
    }
    
    public List<Post> findAll() {
        return postDAO.findAll();
    }
    
    public List<Post> findFeedForUser(Integer userId) {
        return postDAO.findActivePostsByFollowedUsers(userId);
    }
    
    public void deletePost(Integer id) {
        postDAO.delete(id);
    }
    
    public void updatePostStatus(Integer id, String status) {
        Post post = postDAO.findById(id);
        if (post != null) {
            post.setStatus(status);
            postDAO.save(post);
        }
    }
    
    public void updatePost(Post post) {
        postDAO.update(post);
    }
}