package webservice;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import common.ServerResponse;

public class InvokeWebService {
    public ServerResponse saveTORemoteDB(Map request) throws IOException, DocumentException{
        HttpClient httpClient = new HttpClient();
        //添加用户验证信息
        Credentials defaultCreds = new UsernamePasswordCredentials("username", "password");
        httpClient.getState().setCredentials(AuthScope.ANY, defaultCreds);
        PostMethod postMethod = new PostMethod("http://eip.sioc.ac.cn/ECardApi/services/Sync");
        StringBuffer sb = new StringBuffer();
        String soapRequestData = sb.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.sioc.org\" xmlns:bean=\"http://bean.sioc.org\">")
                                .append("<soapenv:Header/>")
                                .append("<soapenv:Body>")
                                .append("<ser:pay>")
                                .append("<ser:data>")
                                .append("<bean:accCode>214</bean:accCode>")
                                .append("<bean:cardASN>"+request.get("cardASN")+"</bean:cardASN>")
                                .append("<bean:cardNo>"+request.get("cardNo")+"</bean:cardNo>")
                                .append("<bean:cardSn>"+request.get("cardSn")+"</bean:cardSn>")
                                .append("<bean:collectAddress></bean:collectAddress>")
                                .append("<bean:collectDt>"+request.get("collectDt")+"</bean:collectDt>")
                                .append("<bean:customerId>"+request.get("customerId")+"</bean:customerId>")
                                .append("<bean:empID>0</bean:empID>")
                                .append("<bean:id></bean:id>")
                                .append("<bean:isCpuCard>2</bean:isCpuCard>")
                                .append("<bean:isOffline>1</bean:isOffline>")
                                .append("<bean:mealId>0</bean:mealId>")
                                .append("<bean:mngFare>0</bean:mngFare>")
                                .append("<bean:oddFare>"+request.get("oddFare")+"</bean:oddFare>")
                                .append("<bean:opCount>"+request.get("opCount")+"</bean:opCount>")
                                .append("<bean:opFare>"+request.get("opFare")+"</bean:opFare>")
                                .append("<bean:opType>1</bean:opType>")
                                .append("<bean:opdt>"+request.get("opdt")+"</bean:opdt>")
                                .append("<bean:postCode>0</bean:postCode>")
                                .append("<bean:recNo>0</bean:recNo>")
                                .append("<bean:recState>0</bean:recState>")
                                .append("<bean:samCardNo>"+request.get("samCardNo")+"</bean:samCardNo>")
                                .append("<bean:samTradeNo>"+request.get("samTradeNo")+"</bean:samTradeNo>")
                                .append("<bean:saveOpcount>"+request.get("saveOpcount")+"</bean:saveOpcount>")
                                .append("<bean:strCollectDt>"+request.get("strCollectDt")+"</bean:strCollectDt>")
                                .append("<bean:strOpdt>"+request.get("strOpdt")+"</bean:strOpdt>")
                                .append("<bean:sumFare>"+request.get("sumFare")+"</bean:sumFare>")
                                .append("<bean:tac>"+request.get("tac")+"</bean:tac>")
                                .append("<bean:termID>"+request.get("termId")+"</bean:termID>")
                                .append("<bean:tradeNo>0</bean:tradeNo>")
                                .append("<bean:walletType>0</bean:walletType>")
                                .append("</ser:data>")
                                .append("</ser:pay>")
                                .append("</soapenv:Body>")
                                .append("</soapenv:Envelope>")
                                .toString();
        System.out.println(soapRequestData);
        StringRequestEntity requestEntity = new StringRequestEntity(soapRequestData,"text/xml","UTF-8");
        postMethod.setRequestEntity(requestEntity);
        postMethod.addRequestHeader("SOAPAction","");
        int statusCode = httpClient.executeMethod(postMethod);
        InputStream in = null;
        if(statusCode == 200) {
            try{
                in = postMethod.getResponseBodyAsStream();
                SAXReader reader = new SAXReader();
                Document document = reader.read(in);
                Element root = document.getRootElement();
                Element node1 = root.element("Body");
                Element node2 = node1.element("payResponse");
                Element node3 = node2.element("payReturn");
                String info = node3.getText();
                System.out.println(info);
                if("1".equals(info)){
                    return ServerResponse.createBySuccess();
                }
                return ServerResponse.createByErrorMessage("远程数据库返回值异常");
            }finally{
                in.close();
            }
        }
        return ServerResponse.createByErrorMessage("连接远程数据库异常");
    }
}
