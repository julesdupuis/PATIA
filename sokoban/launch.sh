#!/bin/env bash

# mvn -q package

# java --add-opens java.base/java.lang=ALL-UNNAMED \
#       -server -Xms2048m -Xmx2048m \
#       -cp target/sokoban-1.0-SNAPSHOT-jar-with-dependencies.jar \
#       sokoban.SokobanMain

mvn -Djava.net.useSystemProxies=true clean compile

java --add-opens java.base/java.lang=ALL-UNNAMED \
      -server -Xms4096m -Xmx4096m \
      -cp "$(mvn dependency:build-classpath -Dmdep.outputFile=/dev/stdout -q -Djava.net.useSystemProxies=true):target/test-classes/:target/classes" \
      sokoban.SokobanMain
