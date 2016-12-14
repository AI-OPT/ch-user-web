package com.ai.ch.user.web.constants;

public class ChWebConstants {

	/**
     * 通用租户标识
     */
    public static final String COM_TENANT_ID = "changhong";
    /**
     * 合同类型 供应商  =1
     */
    public static final String CONTRACT_TYPE_SUPPLIER="1";
    
    /**
     * 合同类型 店铺  =2
     */
    public static final String CONTRACT_TYPE_SHOP="2";
    
    public static final String SCAN_CONTRACT_SUPPLIER="30001";
    
    public static final String ELECTRONIC_CONTRACT_SUPPLIER="30002";
	
    public static final String SCAN_CONTRACT_SHOP="40001";
    
    public static final String ELECTRONIC_CONTRACT_SHOP="40002";
    
    public static final String INFOTYPE_SUPPLIER="30";
    
    public static final String INFOTYPE_SHOP="40";
    
	public final static class Tenant {
		private Tenant() {
		}
		/**
		 * 租户Id
		 */
		public static final String TENANT_ID = "changhong";
	}
	
	
	public final static class OperateCode {
		private OperateCode() {
		}

		/**
		 * 成功，成功时取返回报文体
		 */
		public static final String SUCCESS = "000000";

		/**
		 * 失败
		 */
		public static final String Fail = "999999";
		/**
		 * 数据为空
		 */
		public static final String ISNULL = "111111";
	}
	
	public static final class ExceptionCode {
        private ExceptionCode() {
        }
        /**
         * 成功ID
         */
        public static final java.lang.String SUCCESS_CODE = "000000";// 成功
        /*** 失败ID */
        public static final String ERROR_CODE = "111111";
        public static final java.lang.String SYSTEM_ERROR = "999999";// 系统错误

        public static final java.lang.String PARAM_IS_NULL = "888888";// 参数为空

        public static final java.lang.String NO_RESULT = "000001";// 无结果

        public static final java.lang.String PARAM_TYPE_NOT_RIGHT = "000002";// 参数类型错误

        public static final java.lang.String NO_DATA_OR_CACAE_ERROR = "000003";// 无数据或缓存错误

        public static final java.lang.String PARAM_VALUE_NOT_RIGHT = "000004";// 参数取值错误

        public static final java.lang.String PARAM_VALUE_EXIST_ERROR = "000005";// 参数值重复错误

        public static final java.lang.String RESULT_IS_NULL = "000006";// 结果为空
        
        public static final java.lang.String CONTRACT_NAME_ERROR="100005";
        public static final java.lang.String CONTRACT_CODE_ERROR="100005";
    }

       public static final class UploadImg{
            private UploadImg(){}
            /**
             * 上传成功
             */
            public static final String UploadImg_SUCCESS="success";
            /**
             * 上传失败
             */
            public static final String UploadImg_ERROR="fail";
        }
        
}
