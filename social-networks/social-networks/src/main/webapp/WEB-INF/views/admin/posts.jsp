<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Posts - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <script>
        window.contextPath = '${pageContext.request.contextPath}';
    </script>
</head>
<body>
    <nav class="navbar">
        <div class="nav-container">
            <h1>Social Media - Admin</h1>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
                <a href="${pageContext.request.contextPath}/admin/users">Manage Users</a>
                <a href="${pageContext.request.contextPath}/admin/posts" class="active">Manage Posts</a>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </div>
        </div>
    </nav>
    
    <div class="container">
        <h1>Manage Posts</h1>
        
        <div class="table-container">
            <table class="data-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Author</th>
                        <th>Status</th>
                        <th>Created</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="post" items="${posts}">
                        <tr id="post-${post.id}">
                            <td>${post.id}</td>
                            <td class="post-title">${post.title}</td>
                            <td>${post.user.username}</td>
                            <td>
                                <select class="status-select" data-post-id="${post.id}">
                                    <option value="ACTIVE" ${post.status == 'ACTIVE' ? 'selected' : ''}>Active</option>
                                    <option value="INACTIVE" ${post.status == 'INACTIVE' ? 'selected' : ''}>Inactive</option>
                                    <option value="BANNED" ${post.status == 'BANNED' ? 'selected' : ''}>Banned</option>
                                </select>
                            </td>
                            <td>${post.createdAt != null ? post.createdAt : 'No date'}</td>
                            <td>
                                <button class="btn btn-danger btn-sm delete-post-btn" 
                                        data-post-id="${post.id}"
                                        data-post-title="${post.title}">
                                    Delete
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    
    <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</body>
</html>