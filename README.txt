# seckill_project

RDS服务器：

mysql:

service mysqld restart

启动ftp

 systemctl start vsftpd.service
 systemctl status vsftpd.service
ftptest
cd /home/wwwroot/ftptest/
启动nginx:

 cd /usr/local/nginx-1-13/sbin
 ./nginx
>
> ```
> ./nginx 
> ./nginx -s stop
> ./nginx -s quit
> ./nginx -s reload
> ./nginx -s quit:此方式停止步骤是待nginx进程处理任务完毕进行停止。
> ./nginx -s stop:此方式相当于先查出nginx进程id再使用kill命令强制杀掉进程。
> ```

启动redis:

 cd /usr/local/redis
./bin/redis-server ./redis.conf
>
> redis-server
>

netstat -lntp | grep 6379
>
>
>
> redis-cli shutdown
>
> 在本机redis目录下打开PowerShell或者cmd,输入命令
>
> ```
> redis-cli -h 192.168.1.1 -p 6379
> ```

清空缓存：

> redis-cli
>
> flushall

> 关：redis-cli shutdown

启动canal:

/usr/local/canal/bin/stop.sh

/usr/local/canal/bin/startup.sh

启动mq:

cd /usr/local/rabbitmq/sbin
./rabbitmq-server
>
> ./rabbitmqctl stop

启动java:

> java -jar springboot-2.0.jar



nginx服务器

启动nginx 

```
cd /usr/local/nginx/sbin
./nginx 
```

RDS:

启动canal

```
/home/canal/bin/startup.sh
/home/canal/bin/stop.sh
```

虚拟机改ip

cd /etc/sysconfig/network-scripts

vi ifcfg-ens33

停止jar
netstat -anp|grep 8088
