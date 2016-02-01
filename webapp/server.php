<html>

<head>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="icon" type="img/png" href="/img/baymax.png">
<link rel="stylesheet" href="/main.css">
<link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/bootstrap/css/bootstrap-theme.min.css">
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
<br>
<?php
$contentchk=shell_exec("serverqueries/contentchk.sh");
echo "Routing and content check: " . $contentchk;
$phpchk=shell_exec("serverqueries/phpchk.sh");
echo "<br>PHP execution check: " . $phpchk;
?>
</div>
<div id="footer" class="footer">
<?php require("footer.php"); ?>
</div>
<script>$("#server").addClass("active")</script>
<script src="/presentationtime.js"></script>
</body>
</html>
