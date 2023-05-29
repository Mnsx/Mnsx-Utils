mysql:5.7（单机）

```shell
sudo docker run -d \
--name=mysql \
-p 3306:3306 \
-e MYSQL_ROOT_PASSWORD=xx1527030652 \
-v /root/mysql:/var/lib/mysql \
mysql:5.7
```

redis:7.0.4（单机）

```shell
sudo docker run -d \
--name=redis \
-p 6379:6379 \
-v /root/redis/redis.conf:/etc/redis/redis.conf \
-v /root/redis/data:/data \
redis:7.0.4 \
redis-server /etc/redis/redis.conf
```

rabbitmq:3.8.5（单机）

```shell
sudo docker run -d \
--name=rabbit \
-p 15672:15672 \
-p 5672:5672 \
-e RABBITMQ_DEFAULT_USER=root \
-e RABBITMQ_DEFAULT_PASS=123123 \
-e RABBITMQ_DEFAULT_VHOST=center \ 
rabbitmq:3.8.5-management
```

nginx:1.22（单机）

```shell
sudo docker run -d \
--name=nginx \
-p 80:80 \
-p 443:443 \
-v /home/mnsx/docker/nginx/nginx.conf:/etc/nginx/nginx.conf \
-v /home/mnsx/docker/nginx/log:/var/log/nginx \
-v /home/mnsx/docker/nginx/conf.d/default.conf:/etc/nginx/conf.d/default.conf \
-v /home/mnsx/docker/nginx/html:/usr/share/nginx/html \
nginx:1.22
```

openzipkin/zipkin（单机）

```shell
sudo docker run -d \
--name=zipkin \
-p 9411:9411 \
openzipkin/zipkin
```

nacos/nacos-server:v1.4.4（单机）

```shell
sudo docker run -d \
--name nacos \
-e MODE=standalone \
-p 8848:8848 \
nacos/nacos-server:v1.4.4
```

nacos/nacos-server:v2.1.0（单机数据库）

```shell
sudo docker run -d \
--name nacos \
-e MODE=standalone \
-e SPRING_DATASOURCE_PLATFORM=mysql \
-e  MYSQL_SERVICE_HOST=192.168.92.132 \
-e MYSQL_SERVICE_PORT=3306 \
-e MYSQL_SERVICE_DB_NAME=nacos \
-e MYSQL_SERVICE_USER=root \
-e MYSQL_SERVICE_PASSWORD=xx1527030652 \
-p 8848:8848 \
nacos/nacos-server:v2.1.0
```

nacos/nacos-server:v1.4.4（集群）

```shell
docker run -d \
--name nacos \
--hostname nacos1 \
--net=host \
--add-host nacos1:192.168.32.71 \
--add-host nacos2:192.168.32.72 \
--add-host nacos3:192.168.32.73 \
-e PREFER_HOST_MODE=hostname \
-e MYSQL_SERVICE_HOST=192.168.32.70 \
-e MYSQL_SERVICE_DB_NAME=nacos \
-e MYSQL_SERVICE_USER=root \
-e MYSQL_SERVICE_PASSWORD=123123 \
-e MYSQL_SERVICE_PORT=3306 \
-e NACOS_SERVERS="nacos1:8848 nacos2:8848 nacos3:8848" \
-v /usr/local/docker/nacos/custom.properties:/home/nacos/init.d/custom.properties \
-v /usr/local/docker/nacos/logs:/home/nacos/logs \
nacos/nacos-server:1.4.4
```

```shell
docker run -d \
--name nacos \
--hostname nacos2 \
--net=host \
--add-host nacos1:192.168.32.71 \
--add-host nacos2:192.168.32.72 \
--add-host nacos3:192.168.32.73 \
-e MYSQL_SERVICE_HOST=192.168.32.70 \
-e MYSQL_SERVICE_DB_NAME=nacos \
-e MYSQL_SERVICE_USER=root \
-e MYSQL_SERVICE_PASSWORD=123123 \
-e MYSQL_SERVICE_PORT=3306 \
-e NACOS_SERVERS="nacos1:8848 nacos2:8848 nacos3:8848" \
-v /usr/local/docker/nacos/custom.properties:/home/nacos/init.d/custom.properties \
-v /usr/local/docker/nacos/logs:/home/nacos/logs \
nacos/nacos-server:1.4.4
```

```shell
docker run -d \
--name nacos \
--hostname nacos2 \
--net=host \
--add-host nacos1:192.168.32.71 \
--add-host nacos2:192.168.32.72 \
--add-host nacos3:192.168.32.73 \
-e MYSQL_SERVICE_HOST=192.168.32.70 \
-e MYSQL_SERVICE_DB_NAME=nacos \
-e MYSQL_SERVICE_USER=root \
-e MYSQL_SERVICE_PASSWORD=123123 \
-e MYSQL_SERVICE_PORT=3306 \
-e NACOS_SERVERS="nacos1:8848 nacos2:8848 nacos3:8848" \
-v /usr/local/docker/nacos/custom.properties:/home/nacos/init.d/custom.properties \
-v /usr/local/docker/nacos/logs:/home/nacos/logs \
nacos/nacos-server:1.4.4
```

seata:1.5.1（单机）

```shell
docker run -d --name seata \
-p 8091:8091 \
-p 7091:7091 \
-v /usr/local/docker/seata/resources:/seata-server/resources  \
seataio/seata-server:1.5.1
```

elasticsearch:7.12.1（单机）

```shell
sudo docker run -d \
--name es \
-e "ES_JAVA_OPTS=-Xms512m -Xmx512m" \
-e "discovery.type=single-node" \
-v /home/docker/es/data:/usr/share/elasticsearch/data \
-v /home/docker/es/plugins:/usr/share/elasticsearch/plugins \
--privileged \
--network es-net \
-p 9200:9200 \
-p 9300:9300 \
elasticsearch:7.12.1
```

kibana:7.12.1（单机）

```shell
sudo docker run -d \
--name kibana \
-e ELASTICSEARCH_HOSTS=http://es:9200 \
--network=es-net \
-p 5601:5601 \
kibana:7.12.1
```

minio

```shell
sudo docker run -d\
-p 9000:9000 \
-p 9090:9090 \
--net=host \
--name minio \
-d --restart=always \
-e "MINIO_ACCESS_KEY=minioadmin" \
-e "MINIO_SECRET_KEY=minioadmin" \
-v /root/docker/minio/data:/data \
-v /root/docker/minio/config:/root/.minio \
minio/minio \ 
server /data --console-address ":9090" -address ":9000"
```

