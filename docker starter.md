mysql:5.7

```shell
sudo docker run -d --name=mysql_sks -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123123 -v /usr/local/sks/docker/mysql:/var/lib/mysql mysql:5.7
```

redis:7.0.4

```shell
sudo docker run -d --name=redis_blog -p 6379:6379 -v /usr/local/blog/docker/redis/redis.conf:/etc/redis/redis.conf -v /usr/local/blog/docker/redis/data:/data redis:7.0.4 redis-server /etc/redis/redis.conf
```

rabbitmq:3.8.5

```shell
sudo docker run -d --name=rabbit_sks -p 15672:15672 -p 5672:5672 -e RABBITMQ_DEFAULT_USER=root -e RABBITMQ_DEFAULT_PASS=123123 -e RABBITMQ_DEFAULT_VHOST=center  rabbitmq:3.8.5-management
```

nginx:1.22

```shell
sudo docker run -d --name=nginx_store -p 80:80 -p 443:443 -v /usr/local/docker/nginx_store/nginx.conf:/etc/nginx/nginx.conf -v /usr/local/docker/nginx_store/log:/var/log/nginx -v /usr/local/docker/nginx_store/conf.d/default.conf:/etc/nginx/conf.d/default.conf -v /usr/local/docker/nginx_store/html:/usr/share/nginx/html nginx:1.22
```

openzipkin/zipkin

```shell
sudo docker run -d --name=zipkin_test -p 9411:9411 openzipkin/zipkin
```

nacos/nacos-server:v1.4.4

```shell
sudo docker run --name nacos_test -e MODE=standalone -p 8848:8848 -d nacos/nacos-server:v1.4.4
```

