sudo ~/tomcat/bin/shutdown.sh
mysql-ctl start
sudo ~/tomcat/bin/startup.sh
tail -f ~/tomcat/logs/catalina.out
