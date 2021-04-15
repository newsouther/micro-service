
## 构建项目
docker build -t registry.cn-hongkong.aliyuncs.com/souther/micro-service/gateway:latest E:\company\yfu\codespace\micro-service\micro-gateway
docker build -t registry.cn-hongkong.aliyuncs.com/souther/micro-service/user-service:latest E:\company\yfu\codespace\micro-service\micro-user\user-service
docker build -t registry.cn-hongkong.aliyuncs.com/souther/micro-service/security:latest E:\company\yfu\codespace\micro-service\micro-security

## 上传项目
docker push registry.cn-hongkong.aliyuncs.com/souther/micro-service/gateway:latest
docker push registry.cn-hongkong.aliyuncs.com/souther/micro-service/user-service:latest
docker push registry.cn-hongkong.aliyuncs.com/souther/micro-service/security:latest

mvn clean package -Dmaven.test.skip=true -pl micro-gateway -am
###