<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
                <meta name="author" content="Hỏi Dân IT" />
                <title>Dashboard - KO</title>
                <link href="../../css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            </head>

            <body class="sb-nav-fixed">
                <!-- import -->
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <!-- import -->
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Manager User</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                                    <li class="breadcrumb-item active">User</li>
                                </ol>
                                <div class="container mt-5">
                                    <h2>User Details</h2>
                                    <hr>
                                    <div class="card mx-auto" style="width: 18rem;">
                                        <div class="card-header">
                                            User details = ${id}
                                        </div>
                                        <ul class="list-group list-group-flush">
                                            <li class="list-group-item">${user.id}</li>
                                            <li class="list-group-item">${user.email}</li>
                                            <li class="list-group-item">${user.fullname}</li>
                                            <li class="list-group-item">${user.number}</li>
                                            <li class="list-group-item">${user.address}</li>
                                        </ul>
                                    </div>
                                    <a href="/admin/user" class="btn btn-primary mt-4">Back</a>
                                </div>
                            </div>
                        </main>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="../../js/scripts.js"></script>
            </body>

            </html>






            <!-- 
<div class="container mt-5">
    <h2>User Details</h2>
    <hr>
    <div class="card mx-auto" style="width: 18rem;">
        <div class="card-header">
            User details = ${id}
        </div>
        <ul class="list-group list-group-flush">           
                <li class="list-group-item">${user.id}</li>
                <li class="list-group-item">${user.email}</li>
                <li class="list-group-item">${user.fullname}</li>
                <li class="list-group-item">${user.number}</li>
                <li class="list-group-item">${user.address}</li>
        </ul>
    </div>
    <a href="/admin/user" class="btn btn-primary mt-4">Back</a>
</div> -->