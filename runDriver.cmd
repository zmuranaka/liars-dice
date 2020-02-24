:: File: runDriver.cmd
:: Zachary Muranaka
:: Runs the Driver class without compiling

:: prevents the commands from being echoed to the screen
@echo off
cd bin
:: runs the Driver class file
java Driver
cd ../
:: prevents the window from automatically closing
pause
