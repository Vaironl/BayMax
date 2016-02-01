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
$sql = "SELECT appendage FROM armsandlegs WHERE state='1';";
$result = $conn->query($sql);
$returnstr = "";
if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
       $returnstr .= $row["appendage"];
	if ($result->num_rows > 1) {
		$returnstr .= ",";
	}
    }
} else {
    echo "";
}
$returnstr = rtrim($returnstr, ",");
$sql2 = "UPDATE armsandlegs SET state='0'";
$result2 = $conn->query($sql2);
echo $returnstr;
$conn->close();
?>
