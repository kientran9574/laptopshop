<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=9, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <form:form action="/admin/user/delete" method="post" modelAttribute="newsUser">
        <div class="mb-3">
            <form:input type="hidden" class="form-control" path="id"/>
        </div>
        <!-- <div class="mb-3">
            <form:input type="hidden" class="form-control" path="id"/>
        </div>
        <div class="mb-3">
            <label for="" class="form-label ">Email:</label>
            <form:input type="email" name="" id="" class="form-control" path="email" disabled="true"/>
        </div>
        <div class="mb-3">
            <label for="" class="form-label ">Password:</label>
            <form:input type="password" name="" id="" class="form-control" path="pasword"/>
        </div>
        <div class="mb-3">
            <label for="" class="form-label ">Address:</label>
            <form:input type="text" name="" id="" class="form-control" path="address"/>
        </div>
        <div class="mb-3">
            <label for="" class="form-label ">Phone Number:</label>
            <form:input type="number" name="" id="" class="form-control" path="number"/>
        </div>
        <div class="mb-3">
            <label for="" class="form-label ">FullName:</label>
            <form:input type="text" name="" id="" class="form-control" path="fullname"/>
        </div> -->
        <p>bạn thật sư muốn xóa</p>
        <button type="submit" class="btn btn-danger">Delete</button>
    </form:form>
</body>
</html>