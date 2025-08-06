<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Social Media</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <script>
        window.contextPath = '${pageContext.request.contextPath}';
    </script>
</head>
<body>
    <nav class="navbar">
        <div class="nav-container">
            <h1>Social Media</h1>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/user/dashboard">Dashboard</a>
                <a href="${pageContext.request.contextPath}/user/create-post">Create Post</a>
                <a href="${pageContext.request.contextPath}/user/users">Find Users</a>
                <span>Welcome, ${user.username}!</span>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </div>
        </div>
    </nav>
    
    <div class="container">
        <div class="dashboard-stats">
            <div class="stat-card">
                <h3>My Posts</h3>
                <p class="stat-number">${myPosts.size()}</p>
            </div>
            <div class="stat-card">
                <h3>Followers</h3>
                <p class="stat-number">${followersCount}</p>
            </div>
            <div class="stat-card">
                <h3>Following</h3>
                <p class="stat-number">${followingCount}</p>
            </div>
        </div>
        
        <div class="content-grid">
            <div class="feed-section">
                <h2>Your Feed</h2>
                <c:choose>
                    <c:when test="${not empty feedPosts}">
                        <c:forEach var="post" items="${feedPosts}">
                            <div class="post-card">
                                <div class="post-header">
                                    <div class="post-header-left">
                                        <h3>${post.title}</h3>
                                        <span class="post-author">by ${post.user.username}</span>
                                        <span class="post-date">
                                            ${post.createdAt != null ? post.createdAt : 'No date'}
                                        </span>
                                    </div>
                                </div>
                                <div class="post-content">
                                    <p>${post.body}</p>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p class="no-content">No posts in your feed. Follow some users to see their posts!</p>
                    </c:otherwise>
                </c:choose>
            </div>
            
            <div class="my-posts-section">
                <h2>My Posts</h2>
                <c:choose>
                    <c:when test="${not empty myPosts}">
                        <c:forEach var="post" items="${myPosts}">
                            <div class="post-card">
                                <div class="post-header">
                                    <div class="post-header-left">
                                        <h3>${post.title}</h3>
                                        <span class="post-status status-${post.status.toLowerCase()}">${post.status}</span>
                                        <span class="post-date">
                                            ${post.createdAt != null ? post.createdAt : 'No date'}
                                        </span>
                                    </div>
                                    <div class="post-actions" style="position: relative;">
                                        <button class="post-menu-btn" onclick="toggleDropdown('${post.id}')" style="background: none; border: none; font-size: 18px; cursor: pointer; padding: 4px 8px;">⋮</button>
                                        <div id="dropdown-${post.id}" class="post-dropdown" style="display: none; position: absolute; background: white; border: 1px solid #ccc; border-radius: 4px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); z-index: 1000; min-width: 80px;">
                                            <a href="${pageContext.request.contextPath}/user/edit-post/${post.id}" style="display: block; padding: 8px 12px; text-decoration: none; color: #333; border-bottom: 1px solid #eee;">Edit</a>
                                            <a href="#" class="delete" onclick="confirmDelete('${post.id}')" style="display: block; padding: 8px 12px; text-decoration: none; color: #d32f2f;">Delete</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="post-content">
                                    <p>${post.body}</p>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p class="no-content">You haven't created any posts yet. <a href="${pageContext.request.contextPath}/user/create-post">Create your first post!</a></p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    
    <!-- Delete Confirmation Modal -->
    <div id="deleteModal" class="modal" style="display: none;">
        <div class="modal-content">
            <h3>Confirm Delete</h3>
            <p>Are you sure you want to delete this post? This action cannot be undone.</p>
            <div class="modal-buttons">
                <button class="btn-confirm" onclick="deletePost()">Delete</button>
                <button class="btn-cancel" onclick="closeModal()">Cancel</button>
            </div>
        </div>
    </div>
    
    <script>
    let currentPostId = null;

	function toggleDropdown(postId) {
		console.log('Toggle dropdown for post:', postId, 'Type:', typeof postId); // Debug log

		// Convert to string and create dropdown ID
		const postIdStr = String(postId);
		const dropdownId = 'dropdown-' + postIdStr;
		console.log('Looking for element:', dropdownId); // Debug log

		// Close all other dropdowns first
		const allDropdowns = document.querySelectorAll('.post-dropdown');
		allDropdowns.forEach(dropdown => {
			if (dropdown.id !== dropdownId) {
				dropdown.style.display = 'none';
			}
		});

		// Toggle current dropdown
		const dropdown = document.getElementById(dropdownId);
		if (dropdown) {
			console.log('Found dropdown element'); // Debug log
			if (dropdown.style.display === 'none' || dropdown.style.display === '') {
				dropdown.style.display = 'block';
			} else {
				dropdown.style.display = 'none';
			}
		} else {
			console.error('Dropdown element not found:', dropdownId); // Debug log
			// Additional debug: list all available dropdown IDs
			const allDropdowns2 = document.querySelectorAll('.post-dropdown');
			console.log('Available dropdown IDs:');
			allDropdowns2.forEach(d => console.log(d.id));
		}
	}

	// Close dropdown when clicking outside
	document.addEventListener('click', function(event) {
		if (!event.target.matches('.post-menu-btn')) {
			const dropdowns = document.querySelectorAll('.post-dropdown');
			dropdowns.forEach(dropdown => {
				dropdown.style.display = 'none';
			});
		}
	});

	function confirmDelete(postId) {
		// Convert to string and create dropdown ID
		const postIdStr = String(postId);
		const dropdownId = 'dropdown-' + postIdStr;
		currentPostId = postIdStr;
		document.getElementById('deleteModal').style.display = 'block';
		// Close dropdown
		const dropdown = document.getElementById(dropdownId);
		if (dropdown) {
			dropdown.style.display = 'none';
		}
	}

	function closeModal() {
		document.getElementById('deleteModal').style.display = 'none';
		currentPostId = null;
	}

	function deletePost() {
	    if (currentPostId) {
	        const url = window.contextPath + '/user/delete-post/' + currentPostId;

	        fetch(url, {
	            method: 'POST',
	            headers: {
	                'Content-Type': 'application/json',
	            }
	        })
	        .then(response => response.text())
	        .then(data => {
	            if (data === 'success') {
	                location.reload();
	            } else {
	                alert('Failed to delete post');
	            }
	        })
	        .catch(error => {
	            console.error('Error:', error);
	            alert('Error deleting post');
	        });
	    }
	    closeModal();
	}

	// Debug: Log all dropdown elements after page load
	document.addEventListener('DOMContentLoaded', function() {
		const dropdowns = document.querySelectorAll('.post-dropdown');
		console.log('Found dropdowns:', dropdowns.length);
		dropdowns.forEach(dropdown => {
			console.log('Dropdown ID:', dropdown.id);
		});
	});
    </script>
    
    <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</body>
</html>