#!/bin/bash

RESULTS_PATH=./results
HTML_RESULTS_PATH=$RESULTS_PATH/html-reports

rm -rf $RESULTS_PATH
bash $SOAPUI_HOME/bin/testrunner.sh -Djava.awt.headless=true -s SmokeTestSuite -a -r -j -f $RESULTS_PATH AppointmentRequestService_SmokeTest.xml

# move html test report to a separate directory, so that it is easier for Bamboo to save as an artifact
mkdir $HTML_RESULTS_PATH
mv $RESULTS_PATH/*.html $HTML_RESULTS_PATH
mv $RESULTS_PATH/*.css $HTML_RESULTS_PATH
mv $RESULTS_PATH/AppointmentRequestService_SmokeTest $HTML_RESULTS_PATH
