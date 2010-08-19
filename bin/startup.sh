#!/bin/sh

JAVA=/home/macio/jdk1.7.0/bin/java
SERVER=/home/macio/opt/valkyrie/dev-server

case $1 in
start)
        nohup $JAVA -jar $SERVER/bin/sgs-boot.jar > /home/macio/valkyrie/log/dev.log&
        ;;
stop)
        nohup $JAVA -jar $SERVER/bin/sgs-stop.jar >> /home/macio/valkyrie/log/dev.log&
        ;;
hotbackup)
        db4.5_hotbackup  -cuh $SERVER/data/dsdb/  -b $SERVER/data/backup
        ;;
cleanrestart)
	skill -9 java
	rm -rf $SERVER/data/
	nohup $JAVA -jar $SERVER/bin/sgs-boot.jar > /home/macio/valkyrie/log/dev.log&
	;;
	
esac
