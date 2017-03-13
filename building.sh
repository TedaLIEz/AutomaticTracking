#!/bin/bash
./gradlew clean
./gradlew :tracking:check
retval=$?
exit $retval
./gradlew build
retval=$?
exit $retval