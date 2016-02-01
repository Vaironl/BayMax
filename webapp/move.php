<?php
$servername = "localhost";
$username = "root";
$password = "WHERESTHEBEANS";
$dbname = "devicestates";
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
if (strtolower($_REQUEST["appendage"]) == "l") {
	$sql = "UPDATE armsandlegs SET state='1' WHERE appendage='leftarm'";
} elseif (strtolower($_REQUEST["appendage"]) == "r") {
	$sql = "UPDATE armsandlegs SET state='1' WHERE appendage='rightarm'";
} elseif (strtolower($_REQUEST["appendage"]) == "lr") {
	$sql = "UPDATE armsandlegs SET state='1'";
} else {
	$sql = "UPDATE armsandlegs SET state='" . $_REQUEST["state"] . "' WHERE appendage='" . $_REQUEST["appendage"] . "';";
}
if ($conn->query($sql) === TRUE) {
    echo "Success";
} else {
    echo "Error updating record: " . $conn->error;
}
$conn->close();
?>
