package com.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Service.FollowService;
import com.Service.PostService;
import com.Service.UserService;
import com.entities.Post;
import com.entities.User;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;

	@Autowired
	private FollowService followService;

	@GetMapping("/dashboard")
	public String dashboard(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}

		List<Post> feedPosts = postService.findFeedForUser(user.getId());
		List<Post> myPosts = postService.findByUserId(user.getId());

		model.addAttribute("user", user);
		model.addAttribute("feedPosts", feedPosts);
		model.addAttribute("myPosts", myPosts);
		model.addAttribute("followersCount", followService.getFollowersCount(user.getId()));
		model.addAttribute("followingCount", followService.getFollowingCount(user.getId()));

		return "user/dashboard";
	}

	@GetMapping("/create-post")
	public String createPostPage(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		return "user/create-post";
	}

	@PostMapping("/create-post")
	public String createPost(@RequestParam String title, @RequestParam String body, HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}

		try {
			Post post = new Post(title, body, user.getId());
			postService.savePost(post);
			return "redirect:/user/dashboard";
		} catch (Exception e) {
			model.addAttribute("error", "Failed to create post: " + e.getMessage());
			return "user/create-post";
		}
	}

	@GetMapping("/edit-post/{postId}")
    public String editPostPage(@PathVariable Integer postId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        Post post = postService.findById(postId);
        if (post == null) {
            model.addAttribute("error", "Post not found");
            return "redirect:/user/dashboard";
        }
        
        // Check if the post belongs to the current user
        if (!post.getUserId().equals(user.getId())) {
            model.addAttribute("error", "You can only edit your own posts");
            return "redirect:/user/dashboard";
        }
        
        model.addAttribute("post", post);
        return "user/edit-post";
    }
    
    @PostMapping("/edit-post/{postId}")
    public String updatePost(@PathVariable Integer postId,
                           @RequestParam String title,
                           @RequestParam String body,
                           HttpSession session,
                           Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        try {
            Post post = postService.findById(postId);
            if (post == null) {
                model.addAttribute("error", "Post not found");
                return "redirect:/user/dashboard";
            }
            
            // Check if the post belongs to the current user
            if (!post.getUserId().equals(user.getId())) {
                model.addAttribute("error", "You can only edit your own posts");
                return "redirect:/user/dashboard";
            }
            
            post.setTitle(title);
            post.setBody(body);
            
            postService.updatePost(post);
            
            model.addAttribute("success", "Post updated successfully!");
            model.addAttribute("post", post);
            return "user/edit-post";
            
        } catch (Exception e) {
            Post post = postService.findById(postId);
            model.addAttribute("post", post);
            model.addAttribute("error", "Failed to update post: " + e.getMessage());
            return "user/edit-post";
        }
    }
    
    @PostMapping("/delete-post/{postId}")
    @ResponseBody
    public String deletePost(@PathVariable Integer postId, HttpSession session) {
        System.out.println("Delete post request received for postId: " + postId);
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            System.out.println("No user in session for delete post request");
            return "error";
        }
        
        try {
            Post post = postService.findById(postId);
            if (post == null) {
                System.out.println("Post not found: " + postId);
                return "error";
            }
            
            // Check if the post belongs to the current user
            if (!post.getUserId().equals(user.getId())) {
                System.out.println("User " + user.getId() + " tried to delete post " + postId + " owned by " + post.getUserId());
                return "error";
            }
            
            postService.deletePost(postId);
            System.out.println("Post deleted successfully: " + postId);
            return "success";
            
        } catch (Exception e) {
            System.err.println("Error deleting post: " + e.getMessage());
            e.printStackTrace();
            return "error";
        }
    }

	@GetMapping("/users")
	public String viewUsers(HttpSession session, Model model) {
		User currentUser = (User) session.getAttribute("user");
		if (currentUser == null) {
			return "redirect:/login";
		}

		List<User> allUsers = userService.findAllUsers();
		// Remove current user from the list
		allUsers.removeIf(user -> user.getId().equals(currentUser.getId()));

		model.addAttribute("users", allUsers);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("followService", followService);

		return "user/users";
	}

	@PostMapping("/follow/{userId}")
	@ResponseBody
	public String followUser(@PathVariable Integer userId, HttpSession session) {
		System.out.println("Follow request received for userId: " + userId);

		User currentUser = (User) session.getAttribute("user");
		if (currentUser == null) {
			System.out.println("No user in session for follow request");
			return "error";
		}

		System.out.println("Current user: " + currentUser.getUsername() + " (ID: " + currentUser.getId() + ")");
		System.out.println("Attempting to follow user ID: " + userId);

		try {
			followService.followUser(currentUser.getId(), userId);
			System.out.println("Follow operation completed successfully");
			return "success";
		} catch (Exception e) {
			System.err.println("Error in follow operation: " + e.getMessage());
			e.printStackTrace();
			return "error";
		}
	}

	@PostMapping("/unfollow/{userId}")
	@ResponseBody
	public String unfollowUser(@PathVariable Integer userId, HttpSession session) {
		System.out.println("Unfollow request received for userId: " + userId);

		User currentUser = (User) session.getAttribute("user");
		if (currentUser == null) {
			System.out.println("No user in session for unfollow request");
			return "error";
		}

		System.out.println("Current user: " + currentUser.getUsername() + " (ID: " + currentUser.getId() + ")");
		System.out.println("Attempting to unfollow user ID: " + userId);

		try {
			followService.unfollowUser(currentUser.getId(), userId);
			System.out.println("Unfollow operation completed successfully");
			return "success";
		} catch (Exception e) {
			System.err.println("Error in unfollow operation: " + e.getMessage());
			e.printStackTrace();
			return "error";
		}
	}

	@GetMapping("/profile/{userId}")
	public String viewProfile(@PathVariable Integer userId, HttpSession session, Model model) {
		User currentUser = (User) session.getAttribute("user");
		if (currentUser == null) {
			return "redirect:/login";
		}

		User profileUser = userService.findById(userId);
		if (profileUser == null) {
			return "redirect:/user/users";
		}

		List<Post> userPosts = postService.findByUserId(userId);
		boolean isFollowing = followService.isFollowing(currentUser.getId(), userId);

		model.addAttribute("profileUser", profileUser);
		model.addAttribute("userPosts", userPosts);
		model.addAttribute("isFollowing", isFollowing);
		model.addAttribute("followersCount", followService.getFollowersCount(userId));
		model.addAttribute("followingCount", followService.getFollowingCount(userId));
		model.addAttribute("currentUser", currentUser);

		return "user/profile";
	}
}
