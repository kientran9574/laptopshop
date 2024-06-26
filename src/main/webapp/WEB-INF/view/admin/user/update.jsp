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
                <link href="../../../css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <script>
                    $(document).ready(() => {
                        const avatarFile = $("#avatarFile");
                        const orgImage = "${newsProducts.image}";
                        if (orgImage) {
                            const urlImage = "/images/product/" + orgImage;
                            $("#avatarPreview").attr("src", urlImage);
                            $("#avatarPreview").css({ "display": "block" });
                        }

                        avatarFile.change(function (e) {
                            const imgURL = URL.createObjectURL(e.target.files[0]);
                            $("#avatarPreview").attr("src", imgURL);
                            $("#avatarPreview").css({ "display": "block" });
                        });
                    });

                </script>
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
                                    <div class="row">
                                        <div class="col-md-6 col-12 mx-auto">
                                            <h3>Update User</h3>
                                            <hr>
                                            <form:form action="/admin/user/update" method="post"
                                                modelAttribute="newsUser">
                                                <div class="mb-3">
                                                    <form:input type="hidden" class="form-control" path="id" />
                                                </div>
                                                <div class="mb-3">
                                                    <label for="" class="form-label ">Email:</label>
                                                    <form:input type="email" name="" id="" class="form-control"
                                                        path="email" disabled="true" />
                                                </div>
                                                <div class="mb-3">
                                                    <label for="" class="form-label ">Password:</label>
                                                    <form:input type="password" name="" id="" class="form-control"
                                                        path="pasword" />
                                                </div>
                                                <div class="mb-3">
                                                    <label for="" class="form-label ">Address:</label>
                                                    <form:input type="text" name="" id="" class="form-control"
                                                        path="address" />
                                                </div>
                                                <div class="mb-3">
                                                    <label for="" class="form-label ">Phone Number:</label>
                                                    <form:input type="number" name="" id="" class="form-control"
                                                        path="number" />
                                                </div>
                                                <div class="mb-3">
                                                    <label for="" class="form-label ">FullName:</label>
                                                    <form:input type="text" name="" id="" class="form-control"
                                                        path="fullname" />
                                                </div>
                                                <!-- <div class="mb-3 col-12 col-md-6">
                                                    <label for="avatarFile" class="form-label">Product:</label>
                                                    <input class="form-control" type="file" id="avatarFile"
                                                        accept=".png, .jpg, .jpeg" name="hoidanitFile">
                                                </div>
                                                <div class="mb-3 col-12">
                                                    <img alt="avatarPreview" style="max-height: 250px; display: none;"
                                                        id="avatarPreview">
                                                </div> -->
                                                <button type="submit" class="btn btn-warning">Update</button>
                                            </form:form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </main>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="../../../js/scripts.js"></script>
            </body>

            </html>


            <!-- <div class="container mt-5">
                <div class="row">
                    <div class="col-md-6 col-12 mx-auto">
                        <h3>Update User</h3>
                        <hr>
                        <form:form action="/admin/user/update" method="post" modelAttribute="newsUser">
                            <div class="mb-3">
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
                            </div>

                            <button type="submit" class="btn btn-warning">Update</button>
                        </form:form>
                    </div>
                </div>
            </div> -->