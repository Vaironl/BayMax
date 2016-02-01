var date = new Date();
var day = date.getDay();
var min = date.getMinutes();
var hr = date.getHours();
if ( day == 7 && hr > 9) {
	var msg = "You're out of time.";
}
if ( day == 6 ) {
	var hrleft = 23 - hr + 9; 
	var msg = "You have about " + hrleft + " hours left";
}
if ( day == 0 ) {
	var hrleft = 9 - hr;
	if ( hrleft < 0 ) {
		hrleft = 0;
	}
	var msg = "You have about " + hrleft + " hours left";

}
document.getElementById("timeleft").innerHTML = msg;
