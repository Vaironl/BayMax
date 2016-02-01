<html>

<head>
<link rel="shortcut icon" href="https://s3.amazonaws.com/hoyahacks/favicon.ico" type="image/x-icon" />
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
<?php
echo "<br>Active Connections to Terminal: <br>";
echo "<pre>" . shell_exec("w") . "</pre>";
echo "<br>ifconfig output: <br>";
echo "<pre>" . shell_exec("ifconfig") . "</pre>";
?>
</div>
<div id="footer" class="footer">
<?php require("footer.php"); ?>
<script src="/presentationtime.js"></script>

</div>
<script>$("#comms").addClass("active")</script>
</body>
</html>
