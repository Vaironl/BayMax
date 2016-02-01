<html>

<head>
<link rel="shortcut icon" href="https://s3.amazonaws.com/hoyahacks/favicon.ico" type="image/x-icon" />
<link rel="icon" type="img/png" href="/img/baymax.png">
<link rel="stylesheet" href="/main.css">
<link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/bootstrap/css/bootstrap-theme.min.css">
<style>
.content {
	background-image: url('https://s3.amazonaws.com/hoyahacks/bighero_bg.jpg');
	background-repeat: no-repeat;
	background-size: cover;
	height: 85%;
}
.actualdescript {
	background-color: rgba(255, 255, 255, 0.7);
	color: black;
	padding-top: 10px;
	padding-bottom: 10px;
	padding-left: 10px;
	padding-right: 10px;
}
</style>
<script src="/jquery.js"></script>
<script src="/bootstrap/js/bootstrap.min.js"></script>
	<title>Baymax Comm Server</title>
</head>
<body>
<div id="tophead" class="tophead"></div>

<div id="navcontent" class="navcontent">
<?php require("nav.php"); ?>
</div>
<div id="tophead" class="tophead"></div>
<div id="content" class="content">
<br><br>
<div class="actualdescript">
Inspired by the hit character in the movie <b>Big Hero 6</b>, Baymax is an assistive care robot and superhero designed to help his master get through day to day life and medical understanding. The same is the intention for the Baymax designed at <b>Hoya Hacks</b> in Georgetown University in 2016.
<br><br>Designed in a team of four, Baymax is designed to both answer medical and legal questions. Networked with a Spark Core, motors powered and managed with an Arduino, a medical assistance application in Android, and using this AWS instance to aggregate and sync up data to the individual parts, Hoya Hacks Baymax is a robot designed to provide an intuitive experience to the end user.
<br>Currently we're facing a few challenges, swapping out our Spark Core with another Spark Core after the first one bricked with an Intel Edison.
<br><br>What does work:<br>
<ul>
<li>The actual motors can move their arms</li>
<li>The app can talk to both the AWS LEMP server and the Intel Edison.</li>
<li><strike>The AWS C&C server is 100% running and optimized</strike></li><ul><li>While the AWS server still exists and serves certain roles, it is not managing the Command and Control. This is done on the actual controller (an Intel Edison)</li><li>In lieu of a LEMP stack, the controller uses node express</li></ul>
<li>The calls necessary to trigger arm activity.</li>
</ul>
<br><br>What doesn't work yet:<br>
<ul><li>Optimizations regarding powering the arm motion on one of the arms. Needs a certain (PNP) transistor that isn't available here.</li></ul>
<br>The pivotal point in the actual project was for sure the transition from a Spark Core to an Edison. The was probably the smoothest transition you can imagine.<br>

</div>
</div>

<div id="footer" class="footer">
<?php require("footer.php"); ?>
<script src="/presentationtime.js"></script>

</div>
<script>$("#about").addClass("active")</script>
</body>
</html>
