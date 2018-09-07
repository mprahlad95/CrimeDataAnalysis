<%-- 
    Document   : home
    Created on : Apr 19, 2018, 4:00:36 PM
    Author     : Vigneet Sompura
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
        <script src="https://canvasjs.com/assets/script/canvasjs.min.js" ></script>
        <title>LA Crime Data Analysis</title>
        
        <style>
            body{
                background-color: #dddddd;
            }
            .card-title{
                color: #03c;
            }
                #nav{
                        position: fixed;
                        left: 0px;
                        height: 100%;
                        background-color:#444444;
                        width: 300px;
                        box-shadow: 8px 8px 44px -10px rgba(107,107,107,1);
                        z-index: 10;
                }
                #userblock{
                        width: 100%;
                        height: 220px;
                        border-bottom: 1px solid #888888;
                        position: relative;
                        background-image:url(Assets/Background_Logo.png);
                        background-size:cover;
                }
                #dropdownMenu2{
                        background-color: #44444400;
                        border: 0px;	
                        width: 260px !important;
                }

                #v-pills-tab a{
                        height: 50px;
                        background-color: #444444;
                        border: 0px;
                        border-bottom: 1px solid #555555;
                        line-height: 50px;
                        vertical-align: middle;
                        width: 100% !important;
                        border-radius: 0px;
                }

                .btn:active:focus {
                  outline: 0;
                }  
                #heatmap{
                    position:fixed;
                    top: 0px;
                    left:0px;
                    width: 100%;
                    height:100%;
                    display: none;
                    z-index: 1;
                }
                
                #dashboard{
                    width: calc(100% - 600px);
                    margin-left: 450px;
                    padding-top: 50px;
                    padding-bottom: 50px;
                }
                
                #insights{
                    width: calc(100% - 600px);
                    margin-left: 450px;
                    padding-top: 50px;
                    padding-bottom: 50px;
                }
                
                #trends{
                    width: calc(100% - 600px);
                    margin-left: 450px;
                    padding-top: 50px;
                    padding-bottom: 50px;
                }
                
                .card{
                    box-shadow: 3px 3px 16px -5px rgba(0,0,0,0.75);
                    margin-top:20px;
                    margin-bottom: 20px;
                    padding: 20px;
                    text-align: justify;
                }
                .card-group{
                    margin:0px 16px 0px 16px;
                }
                
                .card-deck{
                    margin:0px 0px 0px 0px;
                }
        </style>
    </head>
    <body>
        <!-- Left Navigation Bar -->
        <div id="nav">
            <div id="userblock">
                    <div class="dropdown" style="position: absolute; bottom: 5px;margin-left: 20px;">
                      <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Hello! ${user.username}
                      </button>
                      <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenu2">
                        <button class="dropdown-item" type="button">Logout</button>
                      </div>
                    </div>
            </div>
            <br><br>
            <div class="col-11">
                <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
                  <a class="nav-link active" id="v-pills-home-tab" data-toggle="pill" href="#v-pills-home" role="tab" aria-controls="v-pills-home" aria-selected="true" onclick="showHome()">Home</a>
                  <a class="nav-link" id="v-pills-profile-tab" data-toggle="pill" href="#v-pills-profile" role="tab" aria-controls="v-pills-profile" aria-selected="false" onclick="showInsights()">Insights</a>
                  <a class="nav-link" id="v-pills-messages-tab" data-toggle="pill" href="#v-pills-messages" role="tab" aria-controls="v-pills-messages" aria-selected="false" onclick="showTrends()">Trends</a>
                  <a class="nav-link" id="v-pills-settings-tab" data-toggle="pill" href="#v-pills-settings" role="tab" aria-controls="v-pills-settings" aria-selected="false" onclick="showHeatmap()">Heatmap</a>
                </div>
            </div>

            <div style="position: absolute;width: 100%; height: 50px; bottom: 0px; vertical-align: middle;line-height: 50px; text-align: center; color: #ffffff">No of Tuples : ${tuples}</div>
        </div>
        <!-- Left Navigation Bar -->
        
        <!-- Dashboard -->
        <div id="dashboard">
            <div class="card-group">
            <div class="card" style="width: 100%;">
                <div class="card-body">
                    <h5 class="card-title">Crime Rate around ${user.address}</h5><br>
                    <div id="normalDistribution" style="height: 300px; width: 100%;"></div><br>
                    <p class="card-text">Your area is safer than ${dcp.percentage}% areas in LA!</p>
                </div>
            </div>
            </div>
            <div class="card-group">
                <div class="card" style="width:100%">
                    <div class="card-body">
                        <h5 class="card-title">Insights</h5>
                        <ul>
                            <li><p class="card-text">${cfp}</p></li>
                        <li><p class="card-text">${mcm}</p></li>
                        <li><p class="card-text">${mcw}</p></li>
                        <li><p class="card-text">${mcd}</p></li>
                        <li><p class="card-text">${mch}</p></li>
                        <li><p class="card-text">${mcp}</p></li>
                        <li><p class="card-text">${mc6m}</p></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div id="AreaStatistics" class="card-deck" style="width:100%;margin: auto;">
                <div class="card" >
                    <div class="card-body">
                        <h5 class="card-title">Most committed crime </h5>
                        <ol>
                            <c:forEach var="hc" items="${hc}">
                            <li><p class="card-text">${hc.id} (${hc.percentage}%)</p></li>
                            </c:forEach>
                        </ol>
                    </div>
                </div>
                <div class="card" >
                    <div class="card-body">
                        <h5 class="card-title">Most used weapon</h5>
                        <ol>
                            <c:forEach var="hw" items="${hw}">
                            <li><p class="card-text">${hw.id} (${hw.percentage}%)</p></li>
                            </c:forEach>
                        </ol>
                    </div>
                </div>
            </div>
        </div>
        <!-- Dashboard -->
        
        <!-- Insights -->
        <div id="insights" style="display:none">
            
            
        </div>
        <!-- Insights -->
        
        <!-- Trends -->
        <div id="trends"style="display:none"></div>
        <!-- Trends -->
        
        <!-- Heatmap -->
        <div id="heatmap"></div>
        <!-- Heatmap -->
        
        
        
        <script>
                
            $(document).ready(function(){
               
                var chart = new CanvasJS.Chart("normalDistribution", {
                            axisX:{
                                   title: "Number of crimes"
                            },
                            axisY:{
                                title: "Number of Districts"
                            },
                            data: [              
                            {
                                    // Change type to "doughnut", "line", "splineArea", etc.
                                    type: "spline",
                                    showInLegend: false,
                                    dataPoints: [
                                        
                                        <c:forEach var="normal" items="${normal}">
                                                { label: ${normal.id},  y: ${normal.percentage}  <c:if test = "${normal.id == dcp.id}">, indexLabel: "Your District", markerType: "triangle",markerColor: "red", markerSize: 12</c:if>},
                                        </c:forEach>
                                    ]
                            }
                            ]
                    });
                    chart.render();
                    
                $.ajax({
		url: 'insights.htm',
		type: 'GET',
		dataType: 'html',
		success: function(Data){
			$("#insights").html(Data);
		}
                });
               
               $.ajax({
		url: 'trends.htm',
		type: 'GET',
		dataType: 'html',
		success: function(Data){
			$("#trends").html(Data);
		}
                });
                
                $.ajax({
		url: 'heatmap.htm',
		type: 'GET',
		dataType: 'html',
		success: function(Data){
			$("#heatmap").html(Data);
		}
                });
            });
            function showHome(){
                $("#dashboard").show();
                $("#insights").hide();
                $("#trends").hide();
                $("#heatmap").hide();
            }
            function showInsights(){
                $("#dashboard").hide();
                $("#insights").show();
                $("#trends").hide();
                $("#heatmap").hide();
            }
            function showTrends(){
                $("#dashboard").hide();
                $("#insights").hide();
                $("#trends").show();
                $("#heatmap").hide();
            }
            function showHeatmap(){
                $("#dashboard").hide();
                $("#insights").hide();
                $("#trends").hide();
                $("#heatmap").show();
            }
            
            
        </script>
    </body>
</html>
