
## 构建项目
docker build -t registry.cn-hongkong.aliyuncs.com/souther/gateway:latest D:\sync\myspace\myproject\micro-service\micro-gateway
docker build -t registry.cn-hongkong.aliyuncs.com/souther/user_service:latest D:\sync\myspace\myproject\micro-service\micro-user\user-service
docker build -t registry.cn-hongkong.aliyuncs.com/souther/security:latest D:\sync\myspace\myproject\micro-service\micro-security
docker build -t registry.cn-hongkong.aliyuncs.com/souther/oms_service:latest D:\sync\myspace\myproject\micro-service\micro-oms\oms-service

## 上传项目
docker push registry.cn-hongkong.aliyuncs.com/souther/gateway:latest
docker push registry.cn-hongkong.aliyuncs.com/souther/user_service:latest
docker push registry.cn-hongkong.aliyuncs.com/souther/security:latest
docker push registry.cn-hongkong.aliyuncs.com/souther/oms_service:latest


mvn clean package -Dmaven.test.skip=true -pl micro-gateway -am
