<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Post - Social Media</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
    <nav class="navbar">
        <div class="nav-container">
            <h1>Social Media</h1>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/user/dashboard">Dashboard</a>
                <a href="${pageContext.request.contextPath}/user/create-post">Create Post</a>
                <a href="${pageContext.request.contextPath}/user/users">Find Users</a>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="form-container">
            <h2>Edit Post</h2>

            <c:if test="${not empty error}">
                <div class="alert alert-error">${error}</div>
            </c:if>

            <c:if test="${not empty success}">
                <div class="alert alert-success">${success}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/user/edit-post/${post.id}" method="post">
                <div class="form-group">
                    <label for="title">Title:</label>
                    <input type="text" id="title" name="title" value="${post.title}" required maxlength="255">
                </div>

                <div class="form-group">
                    <label for="body">Content:</label>
                    <textarea id="body" name="body" rows="10" required>${post.body}</textarea>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Update Post</button>
                    <a href="${pageContext.request.contextPath}/user/dashboard" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>