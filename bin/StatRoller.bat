@echo off

rem Set the environment variables local to this batch file so that we don't
rem screw up the environment on the user's system.
setlocal

rem %~dp0 is name of current script directory under NT
set MERP_HOME=%~dp0
rem Remove the bin directory if they're running out of it
set MERP_HOME=%MERP_HOME:bin\=%

rem Slurp up any libraries that have been added
for %%i in ("%MERP_HOME%lib\*.jar") do call "%MERP_HOME%bin\lcp.bat" "%%i"

rem Add the jar to the classpath
call "%MERP_HOME%bin\lcp.bat" "%MERP_HOME%merpfw.jar"

rem Add the classes directory
call "%MERP_HOME%bin\lcp.bat" "%MERP_HOME%classes"

java -DMERP_HOME=%MERP_HOME% -classpath %LOCALCLASSPATH%;%CLASSPATH% com.chaosserver.merp.gui.swing.StatRoller

rem Restore the original environment now that we're done
endlocal