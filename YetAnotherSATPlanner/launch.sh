#!/bin/env bash


if [[ $# -lt 2 ]]; then
    echo "usage : $0 <domainFile> <problemFile>"
    exit 1
fi

domainFile=$1
problemFile=$2

javac -d classes -cp lib/pddl4j-4.0.0.jar:lib/org.sat4j.core.jar src/fr/uga/pddl4j/yasp/*.java

java -cp classes:lib/pddl4j-4.0.0.jar:lib/org.sat4j.core.jar -server -Xms2048m -Xmx2048m fr.uga.pddl4j.yasp.YetAnotherSATPlanner "$domainFile" "$problemFile"