#!/bin/bash
result=0
result=$(php /usr/share/nginx/html/serverqueries/test.php)
if [ "$result" = "<font color='green'><b>[ PASSED ]</b></font>" ]; then
	echo $result;
else
	echo "[ FAILED ]";
	echo $result;
fi
