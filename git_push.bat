@echo off

:: ��ȡ��ǰ�ű���·��
cd /d %~dp0
:: �Զ��ύ
git init 
git add . 
 git commit -m "bat����:%date:~0,10%,%time:~0,8%" 
::  git commit -m "%commitMessage%" 
git push origin master
@echo �Ѿ����
pause


