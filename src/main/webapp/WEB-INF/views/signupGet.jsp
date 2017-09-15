<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>

<html>

<head>

    <meta charset="utf-8">

    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>SignUp</title>

    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">

</head>

<body>

<div class="row" style="padding-top: 50px;">
    <div class="col-lg-8">
        <div class="row">
            <div class="col-lg-5"></div>
            <div class="col-lg-7">

                <div class="row">
                    <div class="col-lg-2"></div>
                    <div class="col-lg-10">
                        <h1 class="my_header"> Sign up </h1>
                    </div>
                </div>

                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Login</label>
                        <div class="col-sm-10">
                            <input id="loginForm" type="text" class="form-control" placeholder="Enter your login">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="inputPassword3" class="col-sm-2 control-label">Password</label>
                        <div class="col-sm-10">
                            <input id="passwordForm" type="password" class="form-control" id="inputPassword3" placeholder="Password">
                        </div>
                    </div>


                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-danger" onclick="clickHandler()"> Log In!</button>
                        </div>

                        <script>
                        const loginObject = document.getElementById('loginForm');
                        const loginType = loginObject.type;
                        const loginValue = loginObject.value;
                        console.log(loginObject);
                        console.log(loginType);
                        console.log(loginValue);

//                        var xhr = new XMLHttpRequest();
//
//                        var body = {
//                            loginToSend:encodeURIComponent(login)
//                        };
//
//                        xhr.open("POST", '/signup', true);
//                        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
//
//                        xhr.send(body);

                        </script>

                        <%--<form name="person">--%>
                            <%--<input name="name" value="Виктор">--%>
                            <%--<input name="surname" value="Цой">--%>
                        <%--</form>--%>
                        <%--<script>--%>
                            <%--var formData = new FormData(document.forms.person);--%>

                            <%--var xhr = new XMLHttpRequest();--%>
                            <%--xhr.open("POST", "/signup");--%>
                            <%--xhr.send(formData);--%>
                        <%--</script>--%>
                    </div>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>

    <%--<script src="js/jquery-1.11.3.min.js"></script>--%>

    <%--<script src="js/bootstrap.min.js"></script>--%>
</body>
</html>

