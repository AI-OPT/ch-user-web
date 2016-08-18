package com.ai.ch.user.web.constants;

public class ChWebConstants {

	public final static class Tenant {
		private Tenant() {
		}
		/**
		 * 租户Id
		 */
		public static final String TENANT_ID = "ch";
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
	}
	
}
