#!/bin/bash
curl -s http://baymax.michaelbailey.co | grep "Server time" &> /dev/null
if [ $? -eq 0 ]; then
	echo "<font color='green'><b>[ PASSED ]</b></font>";
else
	echo "<font color='red'><b>[ FAILED ]</b></font>";
fi
