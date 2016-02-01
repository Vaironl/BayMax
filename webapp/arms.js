ipaddr="10.128.128.178";
function writeresult() {
	// Deprecated function
	var request = "";
        $.ajax({
                type: 'POST',
                url: "http://baymax.michaelbailey.co/serverqueries/readserver.php",
                data: request,
                dataType: "text",
                success: function(resultData) { document.getElementById("results").innerHTML = resultData; }
        });
}

function restorein5() {
	setTimeout(
	function() {
	document.getElementsByTagName("button")[0].removeAttribute("disabled");
	document.getElementsByTagName("button")[1].removeAttribute("disabled");
	document.getElementsByTagName("button")[2].removeAttribute("disabled");
        document.getElementById("pingzone").innerHTML = "";
        location.reload();

	}, 3000);
//	document.getElementById("pingzone").innerHTML = "";
//	location.reload();
}
function buttons(button) {
        url="http://"+ipaddr+":3000/"+button;
	document.getElementById("pingzone").innerHTML = "<img src="+url+"></img>";
	document.getElementById(button.toLowerCase()).setAttribute("disabled","disabled");
	restorein5();
}
function restorewipein5() {
        setTimeout(
        function() {
        document.getElementsByTagName("button")[3].removeAttribute("disabled");
        }, 3000);
}
