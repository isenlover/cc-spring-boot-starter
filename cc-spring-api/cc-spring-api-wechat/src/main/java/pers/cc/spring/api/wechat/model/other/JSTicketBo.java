package pers.cc.spring.api.wechat.model.other;


/**
 * @author chengce
 * @version 2016-12-22 21:13
 */
public class JSTicketBo extends WxBaseResponse {
    private String ticket;
    private int expires_in;
    private static JSTicketBo jsTicketBo;

    private JSTicketBo() {
    }

    public static JSTicketBo getInstance() {
        return jsTicketBo == null ? (jsTicketBo = new JSTicketBo()) : jsTicketBo;
    }

    public static void setJsTicketBo(JSTicketBo jsTicketBo) {
        JSTicketBo.jsTicketBo = jsTicketBo;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
