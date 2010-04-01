echo off
REM ***************************************
REM  File:$file$
REM  Revision: $rev$
REM
REM  Author: Nguyen Quang Huy
REM
REM  Version: 1.0
REM
REM  Description: Simple script for compiling cpptest plugin and start hudson with it
REM ***************************************
rmdir /S /Q "%HOMEDRIVE%%HOMEPATH%\.hudson\plugins\cpptest" 
d:
cd D:\checkout\hudson\trunk\plugins\cpptest
call mvn -o package
REM call mvn install
copy ".\target\cpptest.hpi" "%HOMEDRIVE%%HOMEPATH%\.hudson\plugins\"
java -jar "%HOMEDRIVE%%HOMEPATH%\.hudson\hudson.war"