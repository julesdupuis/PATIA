#!/bin/env bash

timeOut=60

if [[ $# -lt 3 ]]; then
    echo "usage : $0 <domainFile> <problemFile> <heuristic>"
    echo "heuristics :
    'AJUSTED_SUM'
    'AJUSTED_SUM2'
    'AJUSTED_SUM2M'
    'COMBO'
    'MAX'
    'FAST_FORWARD'
    'SET_LEVEL'
    'SUM'
    'SUM_MUTEX'"
    exit 1
fi

domainFile=$1
problemFile=$2
heuristic=$3

java -cp ./pddl4j-4.0.0.jar -server -Xms2048m -Xmx2048m fr.uga.pddl4j.planners.statespace.HSP "$domainFile" "$problemFile" -t "$timeOut" -e "$heuristic"