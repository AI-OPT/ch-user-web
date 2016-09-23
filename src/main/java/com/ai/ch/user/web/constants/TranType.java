package com.ai.ch.user.web.constants;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

/**
 * 交易类型枚举
 * @author lvy
 *
 */
public enum TranType {
    ENTERPRISE_OPEN("510.001.01","企业开户")
   ,PAY_APPLY("100.001.01","支付请求")
   ,PAY_NOTICE("103.001.01","支付通知")
   ,PAY_QUERY("300.001.01","支付查询")
   ,PAY_QUERY_RESULT("301.001.01","支付查询应答")
 
   ,COMMON("599.001.01","通用应答")
   
   ,PAY_GUARANTEE_MONEY("104.001.01", "保证金扣款")
   ,PAY_GUARANTEE_MONEY_NOTICE("105.001.01", "保证金扣款通知")
   ,PAY_GUARANTEE_MONEY_QUERY("711.001.01", "保证金支付订单查询")
   ,PAY_GUARANTEE_MONEY_QUERY_RESULT("712.001.01", "保证金支付订单应答")
   ;
   private String value;
   private String name;

   TranType(String value,String name){
       this.value = value;
       this.name = name;
   }

   private static Map<String,TranType> enumMap = new TreeMap<String,TranType>();

   static{
       for(TranType enums : TranType.values()){
           enumMap.put(enums.getValue(),enums);
       }
   }

   public String getValue() {
       return value;
   }

   public String getName() {
       return name;
   }

   public static TranType parseEnumByValue(String value){
       if(StringUtils.isEmpty(value))
           return null;
       return enumMap.get(value);
   }
}

