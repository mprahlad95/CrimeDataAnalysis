<%-- 
    Document   : trends
    Created on : Apr 25, 2018, 11:02:05 PM
    Author     : Vigneet Sompura
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="card-group">
    <div class="card" style="width: 100%;">
        <div class="card-body">
            <h5 class="card-title">Crimes in last 8 years by crime type</h5><br>
            <div id="crimebycrimetype" style="height: 400px; width: 100% !important;"></div><br>
        </div>
    </div>
</div>

<div class="card-group">
    <div class="card" style="width: 100%;">
        <div class="card-body">
            <h5 class="card-title">Crimes in last 8 years by area</h5><br>
            <div id="crimebyarea" style="height: 400px; width: 100% !important;"></div><br>
        </div>
    </div>
</div>

<div class="card-deck" style="width: 100%;">
    <div class="card" >
        <div class="card-body">
            <h5 class="card-title">Monthly distribution</h5><br>
            <div id="dm" style="height: 250px; width: 100% !important;"></div><br>
        </div>
    </div>
    <div class="card" >
        <div class="card-body">
            <h5 class="card-title">Weakly distribution</h5><br>
            <div id="ddow" style="height: 250px; width: 100% !important;"></div><br>
        </div>
    </div>
</div>

<div class="card-deck" style="width: 100%;">
    <div class="card" >
        <div class="card-body">
            <h5 class="card-title">Gender distribution</h5><br>
            <div id="dg" style="height: 250px; width: 100% !important;"></div><br>
        </div>
    </div>
    <div class="card" >
        <div class="card-body">
            <h5 class="card-title">Descent distribution</h5><br>
            <div id="dd" style="height: 250px; width: 100% !important;"></div><br>
        </div>
    </div>
</div>

<div class="card-deck" style="width: 100%;">
    <div class="card" >
        <div class="card-body">
            <h5 class="card-title">Crime type distribution</h5><br>
            <div id="dct" style="height: 250px; width: 100% !important;"></div><br>
        </div>
    </div>
    <div class="card" >
        <div class="card-body">
            <h5 class="card-title">Crime distribution</h5><br>
            <div id="dc" style="height: 250px; width: 100% !important;"></div><br>
        </div>
    </div>
</div>

<div class="card-deck" style="width: 100%;">
    <div class="card" >
        <div class="card-body">
            <h5 class="card-title">Weapon distribution</h5><br>
            <div id="dw" style="height: 250px; width: 100% !important;"></div><br>
        </div>
    </div>
    <div class="card" >
        <div class="card-body">
            <h5 class="card-title">Premise type distribution</h5><br>
            <div id="dp" style="height: 250px; width: 100% !important;"></div><br>
        </div>
    </div>
</div>
<script>
    $(document).ready(function(){
        
        var chart1 = new CanvasJS.Chart("crimebycrimetype", {
                axisX:{
                       title: "Year"
                },
                axisY:{
                    title: "Number of Crime"
                },
                legend:{
		cursor: "pointer",
		fontSize: 16,
                itemclick: toogleDataSeries1,
                },
                toolTip:{
                        shared: true
                },
                data: [     
                    <c:forEach var="cpy" items="${cpy}">
                    {
                            // Change type to "doughnut", "line", "splineArea", etc.
                            name: "${cpy.name}",
                            type: "line",
                            showInLegend: true,
                            dataPoints: [
                                <c:set var="yr" value="2010" scope="page"/>
                                <c:forEach var="normal" items="${cpy.data}">
                                        { label: ${yr},  y: ${normal}},
                                    <c:set var="yr" value="${yr + 1}" scope="page"/>
                                </c:forEach>
                            ]
                    },
                    </c:forEach>
                ]
        });
        chart1.render();
        
        function toogleDataSeries1(e){
	if (typeof(e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
		e.dataSeries.visible = false;
	} else{
		e.dataSeries.visible = true;
	}
	chart1.render();
        }
        
        var chart2 = new CanvasJS.Chart("crimebyarea", {
                axisX:{
                       title: "Year"
                },
                axisY:{
                    title: "Number of Crime"
                },
                legend:{
		cursor: "pointer",
		fontSize: 16,
                itemclick: toogleDataSeries2,
                },
                toolTip:{
                        shared: true
                },
                data: [     
                    <c:forEach var="cpya" items="${cpya}">
                    {
                            // Change type to "doughnut", "line", "splineArea", etc.
                            name: "${cpya.name}",
                            type: "line",
                            showInLegend: true,
                            dataPoints: [
                                <c:set var="yr" value="2010" scope="page"/>
                                <c:forEach var="normal" items="${cpya.data}">
                                        { label: ${yr},  y: ${normal}},
                                    <c:set var="yr" value="${yr + 1}" scope="page"/>
                                </c:forEach>
                            ]
                    },
                    </c:forEach>
                ]
        });
        chart2.render();
        function toogleDataSeries2(e){
	if (typeof(e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
		e.dataSeries.visible = false;
	} else{
		e.dataSeries.visible = true;
	}
	chart2.render();
        }
        
        var pichart1 = new CanvasJS.Chart("dm", {
                animationEnabled: true,
                data: [{
                        type: "pie",
                        startAngle: 240,
                        yValueFormatString: "##0.00\"%\"",
                        indexLabel: "{label} {y}",
                        dataPoints: [
                        <c:forEach var="temp" items="${dm}">
                           {y: ${temp.percentage}, label: "${temp.id}"},
                        </c:forEach>
                                
                        ]
                }]
        });
        pichart1.render();
        
        var pichart2 = new CanvasJS.Chart("ddow", {
                animationEnabled: true,
                data: [{
                        type: "pie",
                        startAngle: 240,
                        yValueFormatString: "##0.00\"%\"",
                        indexLabel: "{label} {y}",
                        dataPoints: [
                        <c:forEach var="temp" items="${ddow}">
                           {y: ${temp.percentage}, label: "${temp.id}"},
                        </c:forEach>
                                
                        ]
                }]
        });
        pichart2.render();
        
        var pichart3 = new CanvasJS.Chart("dg", {
                animationEnabled: true,
                data: [{
                        type: "pie",
                        startAngle: 240,
                        yValueFormatString: "##0.00\"%\"",
                        indexLabel: "{label} {y}",
                        dataPoints: [
                        <c:forEach var="temp" items="${dg}">
                           {y: ${temp.percentage}, label: "${temp.id}"},
                        </c:forEach>
                                
                        ]
                }]
        });
        pichart3.render();
       
       var pichart4 = new CanvasJS.Chart("dd", {
                animationEnabled: true,
                data: [{
                        type: "pie",
                        startAngle: 240,
                        yValueFormatString: "##0.00\"%\"",
                        indexLabel: "{label} {y}",
                        dataPoints: [
                        <c:forEach var="temp" items="${dd}">
                           {y: ${temp.percentage}, label: "${temp.id}"},
                        </c:forEach>
                                
                        ]
                }]
        });
        pichart4.render();
        
        var pichart5 = new CanvasJS.Chart("dc", {
                animationEnabled: true,
                data: [{
                        type: "pie",
                        startAngle: 240,
                        yValueFormatString: "##0.00\"%\"",
                        indexLabel: "{label} {y}",
                        dataPoints: [
                        <c:forEach var="temp" items="${dc}">
                           {y: ${temp.percentage}, label: "${temp.id}"},
                        </c:forEach>
                                
                        ]
                }]
        });
        pichart5.render();
        
        var pichart6 = new CanvasJS.Chart("dct", {
                animationEnabled: true,
                data: [{
                        type: "pie",
                        startAngle: 240,
                        yValueFormatString: "##0.00\"%\"",
                        indexLabel: "{label} {y}",
                        dataPoints: [
                        <c:forEach var="temp" items="${dct}">
                           {y: ${temp.percentage}, label: "${temp.id}"},
                        </c:forEach>
                                
                        ]
                }]
        });
        pichart6.render();
        
        var pichart7 = new CanvasJS.Chart("dw", {
                animationEnabled: true,
                data: [{
                        type: "pie",
                        startAngle: 240,
                        yValueFormatString: "##0.00\"%\"",
                        indexLabel: "{label} {y}",
                        dataPoints: [
                        <c:forEach var="temp" items="${dw}">
                           {y: ${temp.percentage}, label: "${temp.id}"},
                        </c:forEach>
                                
                        ]
                }]
        });
        pichart7.render();
        
        var pichart8 = new CanvasJS.Chart("dp", {
                animationEnabled: true,
                data: [{
                        type: "pie",
                        startAngle: 240,
                        yValueFormatString: "##0.00\"%\"",
                        indexLabel: "{label} {y}",
                        dataPoints: [
                        <c:forEach var="temp" items="${dp}">
                           {y: ${temp.percentage}, label: "${temp.id}"},
                        </c:forEach>
                                
                        ]
                }]
        });
        pichart8.render();
    });
    
    
</script>