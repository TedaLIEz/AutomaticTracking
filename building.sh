#!/bin/bash
./gradlew clean
./gradlew :tracking:check
./gradlew build
retval=$?
exit $retval