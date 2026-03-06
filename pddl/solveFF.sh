#!/bin/env bash

if [[ $# -lt 2 ]]; then
    echo "usage : $0 <domainFile> <problemFile> [timeOut]"
    exit 1
fi

domainFile=$1
problemFile=$2
timeOut=${3:-60}

echo "time out : $timeOut"

java -cp ./pddl4j-4.0.0.jar -server -Xms2048m -Xmx2048m fr.uga.pddl4j.planners.statespace.FF "$domainFile" "$problemFile" -t "$timeOut"