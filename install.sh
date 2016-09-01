#!/bin/bash
APP_NAME='planetsudo.server'
clear &&
echo "=== clean ${APP_NAME} ===" &&
mvn clean $@ &&
clear &&
echo "=== install and deploy ${APP_NAME} ===" &&
mvn install -DskipTests -DassembleDirectory=${prefix} $@ &&
clear &&
echo "=== ${APP_NAME} is successfully installed to ${prefix} ==="
