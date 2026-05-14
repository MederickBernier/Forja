#!/bin/bash
cd "$(dirname "$0")"
mvn -q -pl . install -am 2>/dev/null
java \
  --module-path /usr/share/openjfx/lib \
  --add-modules javafx.controls,javafx.fxml \
  -cp target/forja-demo-0.1.0-SNAPSHOT.jar:/home/enigma/.m2/repository/io/forja/forja-components/0.1.0-SNAPSHOT/forja-components-0.1.0-SNAPSHOT.jar \
  io.forja.demo.DemoApp
