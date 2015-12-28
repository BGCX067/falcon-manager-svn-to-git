@echo off
call clean.cmd

echo Building the server ...
call %VBROKERDIR%\bin\idl2cpp -src_suffix cpp -I%VBROKERDIR%\idl -root_dir ..\server\server Falcon.idl

echo Building the client ...
call idl2java -root_dir ..\client\common\src_idl Falcon.idl
