#!/bin/sh

APP_ID="park.web"
APP_HOME=`pwd`
CATALINA_HOME="${APP_HOME}"

echo "appid ${APP_ID}"
echo "apphome ${APP_HOME}"
echo "catalina home: ${CATALINA_HOME}"

#MEM_OPTS="-Xms512m -Xmx1024m"
export JAVA_OPTS="$JAVA_OPTS -Xms512m -Xmx1024m"

${CATALINA_HOME}/bin/catalina.sh run

