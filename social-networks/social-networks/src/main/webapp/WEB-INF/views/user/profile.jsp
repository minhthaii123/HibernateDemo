<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>${profileUser.username}'sProfile- Social Media</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
	<nav class="navbar">
		<div class="nav-container">
			<h1>Social Media</h1>
			<div class="nav-links">
				<a href="${pageContext.request.contextPath}/user/dashboard">Dashboard</a>
				<a href="${pageContext.request.contextPath}/user/create-post">Create
					Post</a> <a href="${pageContext.request.contextPath}/user/users">Find
					Users</a> <a href="${pageContext.request.contextPath}/logout">Logout</a>
			</div>
		</div>
	</nav>

	<div class="container">
		<div class="profile-header">
			<div class="profile-info">
				<h1>${profileUser.username}</h1>
				<p class="profile-role">${profileUser.role}</p>
				<p class="profile-joined">Joined: ${profileUser.createdAt != null ? profileUser.createdAt : 'No date'}
				</p>
			</div>

			<div class="profile-stats">
				<div class="stat">
					<span class="stat-number">${userPosts.size()}</span> <span
						class="stat-label">Posts</span>
				</div>
				<div class="stat">
					<span class="stat-number">${followersCount}</span> <span
						class="stat-label">Followers</span>
				</div>
				<div class="stat">
					<span class="stat-number">${followingCount}</span> <span
						class="stat-label">Following</span>
				</div>
			</div>

			<c:if test="${profileUser.id != currentUser.id}">
				<div class="profile-actions">
					<c:choose>
						<c:when test="${isFollowing}">
							<button class="btn btn-secondary follow-btn"
								data-user-id="${profileUser.id}" data-action="unfollow">
								Unfollow</button>
						</c:when>
						<c:otherwise>
							<button class="btn btn-primary follow-btn"
								data-user-id="${profileUser.id}" data-action="follow">
								Follow</button>
						</c:otherwise>
					</c:choose>
				</div>
			</c:if>
		</div>

		<div class="profile-posts">
			<h2>${profileUser.username}'sPosts</h2>
			<c:choose>
				<c:when test="${not empty userPosts}">
					<c:forEach var="post" items="${userPosts}">
						<div class="post-card">
							<div class="post-header">
								<h3>${post.title}</h3>
								<span class="post-date"> ${post.createdAt != null ? post.createdAt : 'No date'}
								</span>
							</div>
							<div class="post-content">
								<p>${post.body}</p>
							</div>
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<p class="no-content">${profileUser.username}hasn'tposted
						anything yet.</p>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</body>
</html>