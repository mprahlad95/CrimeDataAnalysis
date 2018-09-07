<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>LA Crime Data Analysis</title>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
        <link rel="stylesheet" href="CSS/Style.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,300,300italic,400italic,500,500italic,700,700italic">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto+Mono:400,700">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>

        <script type="text/javascript"
        src="https://maps.googleapis.com/maps/api/js?sensor=false&key=AIzaSyCTD0fHUoquh0JZ9vR22taHW8Fx7ZVuXcU">
        </script>

        <style>
            body{
                background-image:url("Assets/Background.png");
                background-position:center;
                background-attachment: fixed;
                background-size:cover;
               
            }

            #RegistrationSurface{
                position:absolute;
                top:50%;
                margin-top:-300px;
                left:50%;
                margin-left:-250px;
                height:600px;
                width:500px;
                background-color:#FFF;
                border-radius:3px;
                box-shadow: 8px 8px 44px -10px rgba(107,107,107,1);
            }

           
        </style>

    </head>

    <body>
        <div class="card" id = "RegistrationSurface">

            <div class="card-body" style="margin-left:60px;margin-right: 60px;">
                <br>
                <br>
              <form action="registration.htm" id="RegistrationForm" method="get" >
                <input type="hidden" class="form-control" id="Lat" name="Lat" >
                <input type="hidden" class="form-control" id="Long" name="Long" >
                <div id="regPage1">
                    <div class="form-group">
                        <label for="Username">Username</label>
                        <input type="text" class="form-control" id="Username" name="user" placeholder="Enter Username" required>
                    </div>
                    <div class="form-group">
                        <label for="Password">Password</label>
                        <input type="password" class="form-control" id="Password" name="pass" placeholder="Enter Password" required>
                    </div>
                    <div class="form-group">
                        <label for="RePass">Retype-Password</label>
                        <input type="password" class="form-control" id="RePass" name="repass" placeholder="Re-enter Password" required>
                    </div>
                    <div class="form-group">
                        <label for="Address">Address</label>
                        <input type="text" class="form-control" id="Address" name="address" placeholder="Enter Address" required>
                    </div>
                    <div class="form-group">
                        <label for="Date">Date of Birth</label>
                        <input type="date" class="form-control" id="Date" name="DOB" placeholder="Enter Date of Birth" required>
                    </div>
                </div>
                  <div id="regPage2" style="display:none">
                    <h4 style="margin-left:100px">Gender</h4> 
                    <label class="radio-inline"><input type="radio" name="gender" value="Male">Male</label>
                    <label class="radio-inline"><input type="radio" name="gender" value="Female">Female</label>
                    <h4 style="margin-left:100px">Ethnicity</h4> 
                    <p><label class="radio-inline"><input type="radio" name="ethnicity" value="Asian">Asian</label>
                    <label class="radio-inline"><input type="radio" name="ethnicity" value="Black">Black</label>
                    <label class="radio-inline"><input type="radio" name="ethnicity" value="Chinese">Chinese</label></p><p>
                    <label class="radio-inline"><input type="radio" name="ethnicity" value="Cambodian">Cambodian</label>
                    <label class="radio-inline"><input type="radio" name="ethnicity" value="Filipino">Filipino</label>
                    <label class="radio-inline"><input type="radio" name="ethnicity" value="Guamanian">Guamanian</label></p><p>
                    <label class="radio-inline"><input type="radio" name="ethnicity" value="Hispanic">Hispanic</label>
                    <label class="radio-inline"><input type="radio" name="ethnicity" value="American Indian">American Indian</label>
                    <label class="radio-inline"><input type="radio" name="ethnicity" value="Japanese">Japanese</label></p><p>
                    <label class="radio-inline"><input type="radio" name="ethnicity" value="Korean">Korean</label>
                    <label class="radio-inline"><input type="radio" name="ethnicity" value="Laotian">Laotian</label>
                    <label class="radio-inline"><input type="radio" name="ethnicity" value="Pacific Islander">Pacific Islander</label></p><p>
                    <label class="radio-inline"><input type="radio" name="ethnicity" value="Samoan">Samoan</label>
                    <label class="radio-inline"><input type="radio" name="ethnicity" value="Hawaiian">Hawaiian</label>
                    <label class="radio-inline"><input type="radio" name="ethnicity" value="Vietnamese">Vietnamese</label></p><p>
                    <label class="radio-inline"><input type="radio" name="ethnicity" value="White">White</label>
                    <label class="radio-inline"><input type="radio" name="ethnicity" value="Asian Indian">Asian Indian</label>
                    <label class="radio-inline"><input type="radio" name="ethnicity" value="Other">Other</label></p>
                    
                    
                </div>
                <br/>
                </form>
                <center>
                    <button type="button" class="btn btn-light" style="width:90px" id="back" onclick="showP1()" disabled>Previous</button>
                    <button type="button" class="btn btn-light" style="width:90px" id="check" onclick="submitForm()" disabled>Register</button>
                    <button type="button" class="btn btn-light" style="width:90px" id="fwd" onclick="showP2()" >Next</button>
                </center>
            </div>
        </div>
        

        <script>
            
            $("#Address").focusout(function(){
                var geocoder = new google.maps.Geocoder();
                    geocoder.geocode({'address': $("#Address").val()}, function(results, status){
                        if (status == google.maps.GeocoderStatus.OK) {
                            var latitude = results[0].geometry.location.lat();
                            var longitude = results[0].geometry.location.lng();
                            $("#Lat").val(latitude);
                            $("#Long").val(longitude);
                            
                          } 
                });
            });
            function submitForm(){
                var blank = false;
                        
                if($('#Username').val()==""){
                    alert("Please enter username");
                    $('#User').focus();
                    blank = true;
                }
                if ($('#Password').val()=="") {
                    alert("Please enter password!");
                    $('#Pass').focus();
                    blank=true;
                }
                if ($('#RePass').val()=="") {
                    alert("Please retype password!");
                    $('#RePass').focus();
                    blank=true;
                }
                
                if ($('#RePass').val()!=$("#Password").val()) {
                    alert("Passwords do not match!");
                    $('#RePass').focus();
                    blank=true;
                }
                
                if ($('#Address').val()=="") {
                    alert("Please enter address!");
                    $('#Address').focus();
                    blank=true;
                }
                
                if(!blank){
                    $('#RegistrationForm').submit();
                    
                }
                
            
            }

            function showP1(){
                $("#regPage2").hide();
                $("#regPage1").show();
                $("#fwd").attr("disabled", false);
                 
                $("#back").attr("disabled", true);
                
                $("#check").attr("disabled", true);
            }

            function showP2(){
                $("#regPage1").hide();
                $("#regPage2").show();
                 $("#fwd").attr("disabled", true);
                $("#back").attr("disabled", false);
                $("#check").attr("disabled", false);
            }
            
        </script>
    </body>
    <script src="JS/Script.js"/>
</html>
