<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard - Social Media</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
    <nav class="navbar">
        <div class="nav-container">
            <h1>Social Media - Admin</h1>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/admin/dashboard" class="active">Dashboard</a>
                <a href="${pageContext.request.contextPath}/admin/users">Manage Users</a>
                <a href="${pageContext.request.contextPath}/admin/posts">Manage Posts</a>
                <span>Welcome, Admin ${user.username}!</span>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </div>
        </div>
    </nav>
    
    <div class="container">
        <h1>Admin Dashboard</h1>
        
        <div class="dashboard-stats">
            <div class="stat-card">
                <h3>Total Users</h3>
                <p class="stat-number">${totalUsers}</p>
            </div>
            <div class="stat-card">
                <h3>Total Posts</h3>
                <p class="stat-number">${totalPosts}</p>
            </div>
        </div>
        
        <div class="admin-sections">
            <div class="admin-section">
                <h2>Recent Users</h2>
                <div class="table-container">
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Username</th>
                                <th>Role</th>
                                <th>Joined</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="user" items="${recentUsers}">
                                <tr>
                                    <td>${user.id}</td>
                                    <td>${user.username}</td>
                                    <td><span class="role-badge role-${user.role.toLowerCase()}">${user.role}</span></td>
                                    <td>${user.createdAt != null ? user.createdAt : 'No date'}</td>

                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-primary">View All Users</a>
            </div>
            
            <div class="admin-section">
                <h2>Recent Posts</h2>
                <div class="table-container">
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Title</th>
                                <th>Author</th>
                                <th>Status</th>
                                <th>Created</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="post" items="${recentPosts}">
                                <tr>
                                    <td>${post.id}</td>
                                    <td>${post.title}</td>
                                    <td>${post.user.username}</td>
                                    <td><span class="status-badge status-${post.status.toLowerCase()}">${post.status}</span></td>
                                    <td>${post.createdAt != null ? post.createdAt : 'No date'}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <a href="${pageContext.request.contextPath}/admin/posts" class="btn btn-primary">View All Posts</a>
            </div>
        </div>
    </div>
</body>
</html>