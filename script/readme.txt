1.编译打包
#一定要先clean
gradle clean
#然后再打包
gradle build -x test

2.生成镜像
#在本地生成带私服前缀的镜像  (每次打镜像前版本号要更新)
docker build -t 10.19.13.18:5000/ch-user-web:v1.0 .
#将镜像推送到私服
docker push 10.19.13.18:5000/ch-user-web:v1.0

3. 运行镜像
#--net=host  表示为主机(host)模式  去掉该配置，默认为桥接(bridge)模式
#-e 代表需要设置的环境变量
docker run -d --name ch-user-web-v1.0  -p 14151:8080 -e "REST_REGISTRY_ADDR=10.19.13.13:29181" -e "findByCompanyId_http_url=http://124.207.3.100:8083/opaas/http/srv_up_user_findbycompanyid_qry" -e "searchCompanyList_http_url=http://124.207.3.100:8083/opaas/http/srv_up_user_searchcompanylist_qry" -e "updateAuditState_http_url=http://124.207.3.100:8083/opaas/http/srv_up_user_updateauditstate_update" -e "updateCompanyState_http_url=http://124.207.3.100:8083/opaas/http/srv_up_user_updatecompanystate_update" -e "balance_http_url=http://111.9.116.138:7001/upp-route/entry.html" -e "paymentApplication_http_url=http://lytest.ngrok.tech:7777/upp-route/entry.html" -e "appkey=3a83ed361ebce978731b736328a97ea8" -e "sigh_classpath=classpath:CO20160700000018.pfx" -e "check_sign_classpath=classpath:mobile.cer" -e "casServerLoginUrl=http://10.19.13.14:14125/uac/login"  -e "http://10.19.13.14:14125/uac"   -e "serverName=http://10.19.13.14:14151"   -e "logOutServerUrl=http://10.19.13.14:14125/uac/logout"   -e "logOutBackUrl=http://10.19.13.14:14151/ch-user-web"   -e "casServerLoginUrl_Inner=http://10.19.13.14:14125/uac/login"  -e "casServerUrlPrefix_Inner=http://10.19.13.14:14125/uac"   -e "serverName_Inner=http://10.19.13.14:14151"   -e "logOutServerUrl_Inner=http://10.19.13.14:14125/uac/logout"   -e "logOutBackUrl_Inner=http://10.19.13.14:14151/ch-user-web"    -e "innerDomains=changhong.com" -e "SDK_MODE=0" -e "CCS_NAME=aiopt-ch-user" -e "ZK_ADDR=10.19.13.13:29181"   -e "whitelist=changhong.com" 10.19.13.18:5000/ch-user-web:v1.0  
#查看镜像启动日志
docker logs ch-user-web-v1.0
#进入容器，查看镜像内部的情况
docker exec -it ch-user-web-v1.0 /bin/bash
#删除运行的容器
docker rm -fv ch-user-web-v1.0

#=============更新日志========================#
*2016-09-23
1）初始打包
