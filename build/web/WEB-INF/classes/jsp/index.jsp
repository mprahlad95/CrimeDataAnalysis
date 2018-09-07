<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>LA Crime Data Analysis</title>

                <!-- Link Polymer elements -->
                <link rel="stylesheet" href="CSS/Style.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
        
        <!-- Link Polymer elements -->

        <!-- Link Roboto Fonts -->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,300,300italic,400italic,500,500italic,700,700italic">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto+Mono:400,700">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>

        <!-- Link Roboto Fonts -->
        

        <style>
            body{
                background-image:url("Assets/Background.png");
                background-position:center;
                background-attachment: fixed;
                background-size:cover;
            }

            #Error{
                color:#03c;
                height:15px;
                width:100%;
                text-align:center;
            }

            #LoginSurface{
                position:absolute;
                top:50%;
                margin-top:-175px;
                left:50%;
                margin-left:-250px;
                height:350px;
                width:500px;
                background-color:#FFF;
                border-radius:3px;
                box-shadow: 8px 8px 44px -10px rgba(107,107,107,1);
            }

        </style>

    </head>

    <body>
        
        <div class="card" id = "LoginSurface">

            <div class="card-body" style="margin-left:60px;margin-right: 60px;">
                <br>
                <div id="Error">
                    ${error}
                </div>
              <form action="Dashboard.htm" id="LoginForm" method="post" >
                <div class="form-group">
                    <label for="Username">Username</label>
                    <input type="text" class="form-control" id="Username" name="user" placeholder="Enter Username" required>
                </div>
                <div class="form-group">
                    <label for="Password">Password</label>
                    <input type="password" class="form-control" id="Password" name="pass" placeholder="Enter Password" required>
                </div>
                
                <a href="register.htm" style="color:#03c;">New User?</a>
                <br/>
                <br/>
                <center>
                    <button type="submit" class="btn btn-light" >Login</button>
                </center>
                </form>
            </div>
        </div>
        <script>
            function submitForm(){
                var blank = false;
                if($('#User').val()==""){
                    alert("Please enter username");
                    $('#User').focus();
                    blank = true;
                }else if ($('#Pass').val()=="") {
                    alert("Please enter password!");
                    $('#Pass').focus();
                    blank=true;
                }

                if(!blank){
                    $('#LoginForm').submit();
                }
            }

            $(document).keypress(function(event) {
                if(event.which == 13){
                    submitForm();
                }
            });
        </script>
    </body>
    <script src="JS/Script.js"/>
</html>
