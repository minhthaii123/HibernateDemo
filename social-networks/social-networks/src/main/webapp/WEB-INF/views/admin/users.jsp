<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Users - Admin</title>
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
                <a href="${pageContext.request.contextPath}/admin/users" class="active">Manage Users</a>
                <a href="${pageContext.request.contextPath}/admin/posts">Manage Posts</a>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </div>
        </div>
    </nav>
    
    <div class="container">
        <h1>Manage Users</h1>
        
        <div class="table-container">
            <table class="data-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Role</th>
                        <th>Joined</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${users}">
                        <tr id="user-${user.id}">
                            <td>${user.id}</td>
                            <td>${user.username}</td>
                            <td><span class="role-badge role-${user.role.toLowerCase()}">${user.role}</span></td>
                           <td>${user.createdAt != null ? user.createdAt : 'No date'}</td>

                            <td>
                                <button class="btn btn-danger btn-sm delete-user-btn" 
                                        data-user-id="${user.id}"
                                        data-username="${user.username}">
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