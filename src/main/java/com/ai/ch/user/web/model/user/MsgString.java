package com.ai.ch.user.web.model.user;

import java.util.List;

/**
 * 支付中心报文信息
 * @author YangHan
 * @date 2015-3-15
 */
public class MsgString{

	/**
	 * 报文头信息
	 */
    private String headerMsg;
    /**
     * 报文
     */
    private String xmlBody;
    /**
     * 数字签名
     */
    private String DigitalSign;
    /**
     * 详情
     */
    private List<String> detail;

    public String getHeaderMsg() {
        return headerMsg;
    }
    public void setHeaderMsg(String headerMsg) {
        this.headerMsg = headerMsg;
    }
    public String getXmlBody() {
        return xmlBody;
    }
    public void setXmlBody(String xmlBody) {
        this.xmlBody = xmlBody;
    }
    public List<String> getDetail() {
        return detail;
    }
    public void setDetail(List<String> detail) {
        this.detail = detail;
    }
    public String getDigitalSign() {
        return DigitalSign;
    }
    public void setDigitalSign(String digitalSign) {
        DigitalSign = digitalSign;
    }
}
