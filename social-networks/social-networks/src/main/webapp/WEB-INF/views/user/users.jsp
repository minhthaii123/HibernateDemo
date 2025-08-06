<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Find Users - Social Media</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <!-- Thêm context path vào JavaScript -->
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
                <a href="${pageContext.request.contextPath}/user/users" class="active">Find Users</a>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <h2>Find Users</h2>

        <div class="users-grid">
            <c:forEach var="user" items="${users}">
                <div class="user-card">
                    <div class="user-info">
                        <h3>${user.username}</h3>
                        <p class="user-role">${user.role}</p>
                    </div>
                    <div class="user-actions">
                        <c:choose>
                            <c:when test="${followService.isFollowing(currentUser.id, user.id)}">
                                <button class="btn btn-secondary follow-btn"
                                        data-user-id="${user.id}"
                                        data-action="unfollow">
                                    Unfollow
                                </button>
                            </c:when>
                            <c:otherwise>
                                <button class="btn btn-primary follow-btn"
                                        data-user-id="${user.id}"
                                        data-action="follow">
                                    Follow
                                </button>
                            </c:otherwise>
                        </c:choose>
                        <a href="${pageContext.request.contextPath}/user/profile/${user.id}" class="btn btn-outline">View Profile</a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</body>
</html>