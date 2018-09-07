<%-- 
    Document   : insights
    Created on : Apr 25, 2018, 10:52:17 AM
    Author     : Vigneet Sompura
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
            <div id="Calculator" class="card-group">
                
                <div class="card" >
                    <div class="card-body">
                        <h5 class="card-title">Safety Calculator</h5><br>
                        <div class="col">
                          <input class="form-control" type="text" placeholder="Address" id="address">
                          <input id="lat" type="hidden">
                          <input id="long" type="hidden">
                        </div><br>
                        <div class="col">
                            <input class="form-control" type="time" placeholder="Time" id="time">
                        </div><br>
                        <div style="width:100%">
                            <div style="display:inline-block">
                            <div class="col 3">
                                <input class="form-control" type="number" placeholder="Age" min="0" max="100" id="age">
                            </div>
                            </div>
                            <div style="display:inline-block">
                            <div class="form-group col 3">
                                <select class="form-control" id="Gender" placeholder="Select Gender">
                                  <option >Any</option>
                                  <option >Male</option>
                                  <option >Female</option>
                                </select>
                            </div>
                            </div>
                            <div style="display:inline-block">
                                <div class="form-group" style="width:155px">
                                    <select class="form-control" id="Ethnicity" placeholder="Select Ethnicity" onchange="">
                                        <option >Any</option>
                                        <option >Asian</option>
                                        <option >Black</option>
                                        <option >Chinese</option>
                                        <option >Cambodian</option>
                                        <option >Filipino</option>
                                        <option >Guamanian</option>
                                        <option >Hispanic</option>
                                        <option >American Indian</option>
                                        <option >Japanese</option>
                                        <option >Korean</option>
                                        <option >Laotian</option>
                                        <option >Pacific Islander</option>
                                        <option >Samoan</option>
                                        <option >Hawaiian</option>
                                        <option >Vietnamese</option>
                                        <option >White</option>
                                        <option >Asian Indian</option>
                                        <option >Other</option>
                                    </select>
                                </div>
                            </div>
                        </div><br>
                        <center>
                        <button id="calculate" type="button" class="btn btn-outline-primary" onclick="updateResults()">Calculate</button>
                        </center>
                    </div>
                </div>
                <div class="card" >
                    <div class="card-body">
                        <h5 class="card-title">Results</h5>
                        <div id="resultcontainer"></div>
                    </div>
                </div>
            </div>
            <div class="card-group">       
                <div class="card" style="width:100%">
                    <div class="card-body">
                        <h5 class="card-title">Most targeted victim profile</h5>
                        <ol>
                        <c:forEach var="vp" items="${vp}">
                            <li><p class="card-text">${vp.descent} ${vp.gender}s between age ${vp.lage} and ${vp.rage}: ${vp.perc}% of total crimes</p></li>
                        </c:forEach>
                        </ol>
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
                        </ul>
                    </div>
                </div>
            </div>
                    
            
            
                    
            <div id="AreaStatistics" class="card-deck" style="width:100%; margin: auto;">
                <div class="card" >
                    <div class="card-body">
                        <h5 class="card-title">Areas with lowest violent crimes</h5>
                        <ol>
                            <c:forEach var="lva" items="${lva}">
                            <li><p class="card-text">${lva}</p></li>
                            </c:forEach>
                        </ol>
                        
                    </div>
                </div>
                <div class="card" >
                    <div class="card-body">
                        <h5 class="card-title">Areas with highest violent crimes</h5>
                        <ol>
                            <c:forEach var="hva" items="${hva}">
                            <li><p class="card-text">${hva}</p></li>
                            </c:forEach>
                        </ol>
                    </div>
                </div>
            </div>
            <div id="AreaStatistics" class="card-deck" style="width:100%;margin: auto;">
                <div class="card" >
                    <div class="card-body">
                        <h5 class="card-title">Areas with lowest property crimes </h5>
                        <ol>
                            <c:forEach var="lpa" items="${lpa}">
                            <li><p class="card-text">${lpa}</p></li>
                            </c:forEach>
                        </ol>
                    </div>
                </div>
                <div class="card" >
                    <div class="card-body">
                        <h5 class="card-title">Areas with highest property crimes</h5>
                        <ol>
                            <c:forEach var="hpa" items="${hpa}">
                            <li><p class="card-text">${hpa}</p></li>
                            </c:forEach>
                        </ol>
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
                        
                        
                        
                    <script>
                        
                        $("#address").focusout(function(){
                            var geocoder = new google.maps.Geocoder();
                                geocoder.geocode({'address': $("#address").val()}, function(results, status){
                                    if (status == google.maps.GeocoderStatus.OK) {
                                        var latitude = results[0].geometry.location.lat();
                                        var longitude = results[0].geometry.location.lng();
                                        $("#lat").val(latitude);
                                        $("#long").val(longitude);

                                      } 
                            });
                        });
                        function TestFunc(){
                            alert($("#Ethnicity").val());
                        }
                        
                        function updateResults(){
                            var lat = $("#lat").val();
                            var long = $("#long").val();
                            var time = $("#time").val();
                            var age = $("#age").val();
                            var gender = $("#Gender").val();
                            var ethnicity = $("#Ethnicity").val();
                            $("#resultcontainer").html("");
                            var query = "lat="+lat+"&long="+long+"&time="+time+"&age="+age+"&gender="+gender+"&ethnicity="+ethnicity;
                            
                            $.ajax({
                            url: 'calcresult.htm?'+query,
                            type: 'GET',
                            dataType: 'html',
                            success: function(Data){
                                    $("#resultcontainer").html(Data);
                            }
                            });
                        }
                    </script>