<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Post - Social Media</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
    <nav class="navbar">
        <div class="nav-container">
            <h1>Social Media</h1>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/user/dashboard">Dashboard</a>
                <a href="${pageContext.request.contextPath}/user/create-post" class="active">Create Post</a>
                <a href="${pageContext.request.contextPath}/user/users">Find Users</a>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </div>
        </div>
    </nav>
    
    <div class="container">
        <div class="form-container">
            <h2>Create New Post</h2>
            
            <c:if test="${not empty error}">
                <div class="alert alert-error">${error}</div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/user/create-post" method="post">
                <div class="form-group">
                    <label for="title">Title:</label>
                    <input type="text" id="title" name="title" required maxlength="255">
                </div>
                
                <div class="form-group">
                    <label for="body">Content:</label>
                    <textarea id="body" name="body" rows="10" required></textarea>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Publish Post</button>
                    <a href="${pageContext.request.contextPath}/user/dashboard" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>