<%@page import="ru.naumen.perfhouse.statdata.Constants"%>
<%@ page import="ru.naumen.perfhouse.plugins.nio.NioConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="org.influxdb.dto.QueryResult.Series" %>

<html>

<head>
    <title>SD40 Performance indicator</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <script src="/js/jquery-3.1.1.min.js"></script>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.5/css/bootstrap.min.css"
                integrity="sha384-AysaV+vQoT3kOAXZkl02PThvDr8HYKPZhNT5h/CXfBThSRXQ6jW5DO2ekP5ViFdi"
                crossorigin="anonymous"/>
    	<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.2.0/js/tether.min.js"
                integrity="sha384-Plbmg8JY28KFelvJVai01l8WyZzrYWG825m+cZ0eDDS1f7d/js6ikvy1+X+guPIB"
                crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.5/js/bootstrap.min.js"
            integrity="sha384-BLiI7JTZm+JWlgKa0M0kGRpJbF2J8q+qreVrKBC47e3K6BW78kGLrCkeRX6I9RoK"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="/css/style.css"/>
    <style>
        .col-xs-40 {
        	width: 34%;
        }
        .col-xs-10 {
        	width: 11%;
        }
    }
    </style>
    <script>
            $(document).ready(function() {
                var path = location.pathname + location.search;
                $('.nav-item a').each(function(index, elem) {
                     var jElem = $(elem);
                     if (jElem.attr('href') == path) {
                        jElem.removeClass("btn btn-outline-primary").addClass("nav-link active");
                     }
                });
            })
        </script>
</head>

<body>

<script src="http://code.highcharts.com/highcharts.js"></script>
<%
    NioConstants pluginConstants = (NioConstants)request.getAttribute("constants");

    Number times[] = (Number[])request.getAttribute(Constants.TIME);
    Number avgTime[]=  (Number[])request.getAttribute(pluginConstants.AVG_TIME);
    Number maxTime[]=  (Number[])request.getAttribute(pluginConstants.MAX_TIME);
    
  //Prepare links
  	String path="";
  	String custom = "";
  	if(request.getAttribute("custom") == null){
  	Object year = request.getAttribute("year");
  	Object month = request.getAttribute("month");
  	Object day = request.getAttribute("day");
      
      String countParam = (String)request.getParameter("count");
      
  	String params = "";
  	String datePath = "";

  	StringBuilder sb = new StringBuilder();


  	if(countParam != null){
      	params = sb.append("?count=").append(countParam).toString();
  	}else{
      	sb.append('/').append(year).append('/').append(month);
      	if(!day.toString().equals("0")){
          	sb.append('/').append(day);
      	}
      	datePath = sb.toString();
  	}
  	path = datePath + params;
  	}
  	else{
  	    custom = "/custom";
  	    Object from = request.getAttribute("from");
  	  	Object to = request.getAttribute("to");
  	  	Object maxResults = request.getAttribute("maxResults");
  	  	
  	  	StringBuilder sb = new StringBuilder();
  	  	path = sb.append("?from=").append(from).append("&to=").append(to).append("&maxResults=").append(maxResults).toString();
  	}
      
%>

<div class="container">
	<br>
    <h1>Performance data for "${client}"</h1>
    <h3><a class="btn btn-success btn-lg" href="/">Client list</a></h3>
    <h4 id="date_range"></h4>
    <p>
        Feel free to hide/show specific data by clicking on chart's legend
    </p>
    <ul class="nav nav-pills">
		<%  Map<String, String> tabs = (Map<String, String>)request.getAttribute("tabs");
                for (String name : tabs.keySet()) {
            %>
                <li class="nav-item"><a class="btn btn-outline-primary" href="/history/${client}<%=custom %><%=tabs.get(name) %><%=path%>"><%=name %></a></li>
            <%
                }
            %>
	</ul>
</div>

<div class="container">
<div id="cpu-chart-container" style="height: 600px"></div>
<div >
	<table class="table table-fixed header-fixed">
        <thead class="thead-inverse">
            <th class="col-xs-40">Time</th>
            <th class="col-xs-10">Avg parser time</th>
            <th class="col-xs-10">Max parser time</th>
        </thead>
        <tbody>
            <% for(int i=0;i<times.length;i++) {%>
                <tr class="row">
                    <td class="col-xs-40" style="text-align:center;">
                       <%= new java.util.Date(times[i].longValue()).toString() %>
                    </td>
                    <td class="col-xs-10" >
                        <%= avgTime[i] %>
                    </td>
                    <td class="col-xs-10">
                        <%= maxTime[i] %>
                    </td>
                </tr>
            <% } %>
        </tbody>
    </table>
</div>

</div>
<script type="text/javascript">
var times = [];
var avgTime = [];
var maxTime = [];

<% for(int i=0;i<times.length;i++) {%>
    times.push((<%=times[i]%>));
    avgTime.push([new Date(<%= times[i] %>), <%= avgTime[i] %>]);
    maxTime.push([new Date(<%= times[i] %>), <%= maxTime[i] %>]);
<% } %>

document.getElementById('date_range').innerHTML += 'From: '+new Date(times[<%=times.length%>-1])+'<br/>To:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' +new Date(times[0])

if(localStorage.getItem('avgTime')==null){
    localStorage.setItem('avgTime', 'true');
}
if(localStorage.getItem('maxTime')==null){
    localStorage.setItem('maxTime', 'true');
}

var avgTimeVisible = localStorage.getItem('avgTime')==='true';
var maxTimeVisible = localStorage.getItem('maxTime')==='true';

Highcharts.setOptions({
	global: {
		useUTC: false
	}
});

var myChart = Highcharts.chart('cpu-chart-container', {
        chart: {
                zoomType: 'x,y'
            },

        title: {
            text: 'Parser data'
        },

        tooltip: {
            formatter: function() {
                            var index = this.point.index;
                            var date =  new Date(times[index]);
                            return Highcharts.dateFormat('%a %d %b %H:%M:%S', date)
                            + '<br/> <b>'+this.series.name+'</b> '+ this.y + '<br/>'
                        }
        },

        xAxis: {
            labels:{
                formatter:function(obj){
//                        var index = this.point.index;
//                        var date =  new Date(times[index]);
                        return Highcharts.dateFormat('%a %d %b %H:%M:%S', new Date(times[this.value]));
                    }
                },
                reversed: true
        },

        yAxis: {
            title: {
                text: 'Parser time'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        plotOptions: {
            line: {
                marker: {
                    enabled: false
                },
                events: {
                    legendItemClick: function(event) {
                        var series = this.yAxis.series;
                        seriesLen = series.length;

                        if(event.target.index==0){
                            localStorage.setItem('avgTime', !series[0].visible);
                        }
                        if(event.target.index==1){
                            localStorage.setItem('maxTine', !series[1].visible);
                        }
                    }
                }
            }
        },
        series: [{
            name: 'Average Time',
            data: avgTime,
            visible: avgTimeVisible,
            turboThreshold: 10000
        }, {
            name: 'Max Time',
            data: maxTime,
            visible: maxTimeVisible,
            turboThreshold: 10000
        }]
});

</script>

</body>

</html>