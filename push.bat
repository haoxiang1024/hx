@echo off
 
title GITһ���ύ
color 3
echo ��ǰĿ¼�ǣ�%cd%
echo;
 
echo ��ʼ��ӱ����git add .
git add .
echo;
 
set /p declation=�����ύ��commit��Ϣ:
git commit -m "%declation%"
echo;
 
echo ���������ύ��Զ���Լ���֧��git push origin master
git push origin master
echo;
 
echo �л�����֧��git checkout master
git checkout master
echo;
 
echo ��������֧��ȡԶ������֧��git pull origin master
git pull origin master
echo;
 
echo ����֧�ϲ���֧��git merge master
git merge master
echo;
 
echo ���������ύ��Զ������֧��git push origin master
git push origin master
echo;
 
echo �л���֧��git checkout master
git checkout master
echo;
 
echo ���ط�֧��ȡԶ������֧��git pull origin master
git pull origin master
echo;
 
echo ִ����ϣ�
echo;
 
pause