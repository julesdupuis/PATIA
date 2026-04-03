#!/bin/env bash

mvn install:install-file -Dfile=../pddl/pddl4j-4.0.0.jar \
    -DgroupId=fr.uga -DartifactId=pddl4j -Dversion=4.0.0 \
    -Dpackaging=jar -DgeneratePom=true -Djava.net.useSystemProxies=true
