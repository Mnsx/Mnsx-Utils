mysql:5.7

```shell
sudo docker run -d --name=mysql_sks -p 3306:3306 
-e MYSQL_ROOT_PASSWORD=123123 
-v /usr/local/docker/mysql:/var/lib/mysql 
mysql:5.7
```

redis:7.0.4

```shell
sudo docker run -d --name=redis_sks -p 6379:6379 
-v /usr/local/docker/redis/redis.con:/etc/redis/redis.conf 
-v /usr/local/docker/redis/data:/data 
redis:7.0.4
```

rabbitmq:3.8.5

```shell
sudo docker run -d --name rabbit -p 15672:15672 -p 5672:5672 
-e RABBITMQ_DEFAULT_USER=root
-e RABBITMQ_DEFAULT_PASS=123123
-e RABBITMQ_DEFAULT_VHOST=center  
rabbitmq:3.8.5-management
```

