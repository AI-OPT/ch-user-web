package com.ai.ch.user.web.model.user;

import java.util.List;

/**
 * @author YangHan
 * @date 2015-3-15
 */
public class MsgString{

    private String headerMsg;
    private String xmlBody;
    private String DigitalSign;
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
