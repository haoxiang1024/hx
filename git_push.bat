@echo off

:: 获取当前脚本的路径
cd /d %~dp0
:: 自动提交
git init 
git add . 
 git commit -m "bat推送:%date:~0,10%,%time:~0,8%" 
::  git commit -m "%commitMessage%" 
git push origin master
@echo 已经完成
pause


