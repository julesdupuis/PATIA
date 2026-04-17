#!/bin/env bash

# mvn -q package

# java --add-opens java.base/java.lang=ALL-UNNAMED \
#       -server -Xms2048m -Xmx2048m \
#       -cp target/sokoban-1.0-SNAPSHOT-jar-with-dependencies.jar \
#       sokoban.Agent

mvn -Djava.net.useSystemProxies=true clean compile

# note : there is only 4GB on the VM
java --add-opens java.base/java.lang=ALL-UNNAMED \
      -server -Xms3g -Xmx3g \
      -cp "$(mvn dependency:build-classpath -Dmdep.outputFile=/dev/stdout -q -Djava.net.useSystemProxies=true):target/test-classes/:target/classes" \
      sokoban.Agent
