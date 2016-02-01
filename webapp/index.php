<html>

<head>
<link rel="shortcut icon" href="/https://s3.amazonaws.com/hoyahacks/favicon.ico" type="image/x-icon" />
<link rel="icon" type="img/png" href="">
<link rel="stylesheet" href="/main.css">
<link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/bootstrap/css/bootstrap-theme.min.css">
<style>
.btn-xl {
    padding: 18px 28px;
    font-size: 22px;
    border-radius: 8px;
}
</style>
<script src="/jquery.js"></script>
<script src="/arms.js"></script>
<script src="/bootstrap/js/bootstrap.min.js"></script>
	<title>Baymax Comm Server</title>
</head>
<body>
<div id="tophead" class="tophead"></div>
<div id="navcontent" class="navcontent">
<?php require("nav.php"); ?>
</div>
<div id="tophead" class="tophead"></div>
<div id="content" class="content" style="text-align: center;">
<br><br><b>Hoya Hacks 2016 Baymax Command and Contol Server</b><br>Please don't break anything here. It's open by design to maximize collaboration.<br><br>
<div id="buttons">
<button id="left" type="button" class="btn btn-xl btn-primary" onclick="buttons('left');"><img src="https://s3.amazonaws.com/hoyahacks/lefthand.png" /></button><br><br>
<button id="right" type="button" class="btn btn-xl btn-primary" onclick="buttons('right');"><img src="https://s3.amazonaws.com/hoyahacks/righthand.png" /></button><br><br>
<button id="both" type="button" class="btn btn-xl btn-primary" onclick="buttons('both');"><img src="https://s3.amazonaws.com/hoyahacks/bothhand.png" /></button><br><br>

</div>
</div>
<div id="footer" class="footer">
<?php require("footer.php"); ?>

</div>
<script>$("#home").addClass("active")</script>
<script src="/presentationtime.js"></script>
<div id="pingzone" style="visibility: hidden;"></div>
</body>
</html>
