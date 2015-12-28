@echo off
echo Cleaning the server ...
del /q ..\server\server\*.obj 
del /q ..\server\server\*.exe
del /q ..\server\server\*_c.cpp
del /q ..\server\server\*_s.cpp
del /q ..\server\server\*.hh
del /q ..\server\server\*.log
del /q ..\server\server\*.out
del /q ..\server\server\*.ilk
del /q ..\server\server\*.pdb
echo Cleaning the client ...
@rd /s /q ..\client\common\src_idl\ru
