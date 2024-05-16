<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
                <html lang="en">

                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Document</title>

                    <!-- Customized Bootstrap Stylesheet -->
                    <link href="/client/css/bootstrap.min.css" rel="stylesheet">

                    <!-- Template Stylesheet -->
                    <link href="/client/css/style.css" rel="stylesheet">
                </head>

                <body>
                    <jsp:include page="../layout/header.jsp" />
                    <div class="container" style="margin-top: 100px;">
                        <div class="row">
                            <div class="col-12 mt-5">
                                <div class="alert alert-success" role="alert">
                                    Cảm ơn bạn đã đặt hàng , đơn hàng đã xác nhận thành công
                                </div>
                            </div>
                        </div>
                        <a href="/" class="p-2 btn border-3 border-primary active">Back</a>
                    </div>
                  
                    <jsp:include page="../layout/featurs.jsp" />
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
                    <script src="/client/lib/easing/easing.min.js"></script>
                </body>

                </html>