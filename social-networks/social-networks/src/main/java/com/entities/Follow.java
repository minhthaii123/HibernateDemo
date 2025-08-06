package com.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "follows")
public class Follow {
    
    @EmbeddedId
    private FollowId id;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_user_id", insertable = false, updatable = false)
    private User followingUser;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followed_user_id", insertable = false, updatable = false)
    private User followedUser;
    
    // Constructors
    public Follow() {
        this.createdAt = LocalDateTime.now();
    }
    
    public Follow(Integer followingUserId, Integer followedUserId) {
        this();
        this.id = new FollowId(followingUserId, followedUserId);
    }
    
    // Getters and Setters
    public FollowId getId() { return id; }
    public void setId(FollowId id) { this.id = id; }
    
    public Integer getFollowingUserId() { 
        return id != null ? id.getFollowingUserId() : null; 
    }
    
    public Integer getFollowedUserId() { 
        return id != null ? id.getFollowedUserId() : null; 
    }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public User getFollowingUser() { return followingUser; }
    public void setFollowingUser(User followingUser) { this.followingUser = followingUser; }
    
    public User getFollowedUser() { return followedUser; }
    public void setFollowedUser(User followedUser) { this.followedUser = followedUser; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Follow follow = (Follow) o;
        return Objects.equals(id, follow.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}