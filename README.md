# seckill_project

RDS服务器：

启动ftp

> systemctl start vsftpd.service
>
> systemctl status vsftpd.service

启动nginx:

> cd /usr/local/nginx-1-13/sbin
>
> ./nginx

启动redis:

> cd /usr/local/redis
>
> ./bin/redis-server ./redis.conf
>
> redis-server

清空缓存：

> redis-cli
>
> auth admin
>
> flushall

> 关：redis-cli shutdown

启动canal:

> /usr/local/canal/bin/stop.sh
>
> /usr/local/canal/bin/startup.sh

启动mq:

> cd /usr/local/rabbitmq/sbin
>
> ./rabbitmq-server

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
/usr/local/canal/bin/startup.sh
```