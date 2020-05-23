package com.pcbWeld.common.utils;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.*;

/**
 * 微信支付工具类
 */
public class WXPayUtils {
    /**
     * 微信支付相关参数
     */
   public static final String APPID="wxe23f18d7b8947aa6";//公众号id
    public static final String APPSECRET="889ef7319d78c271e75b8d858bb2b5f1";//登录秘钥
    public static final String MERID="1553709101";//微信商户号
    public static final String APPKEY="779ef7321d78c271e84c8d858bc3b1e3" ;//支付秘钥
    public static final String CALLBACK = "http://www2.jingtu99.com/payCallback/";//支付回调
    public static final String OPENURL  = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * XML格式字符串转换为Map
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                // do nothing
            }
            return data;
        } catch (Exception ex) {
            throw ex;
        }

    }

    /**
     * 将Map转换为XML格式的字符串
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.newDocument();
        org.w3c.dom.Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key: data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
        try {
            writer.close();
        }
        catch (Exception ex) {
        }
        return output;
    }

    /**
     *   生成微信支付签名
     */
    public static String createSign(SortedMap<String,String> params,String key){
        StringBuilder sb  =new StringBuilder();
        Set es = params.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()){
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null!=v &&!"".equals(v) && !"sign".equals(k) && !"key".equals(key)){
                sb.append(k+"="+v+"&");
            }
        }
        sb.append("key=").append(key);
        System.out.println(sb.toString());
   //     String sign  =CommonUtils.MD5(sb.toString().toUpperCase());
        String sign = MD5Utils.MD5Encode(sb.toString(),"UTF-8").toUpperCase();
        return sign;
    }

    /**
     *  校验签名
     */
    public static boolean isCorrectSign(SortedMap<String,String> params,String key){
        String sign  =createSign(params,key);
        String weixinPaySign=params.get("sign").toUpperCase();
        return sign.equals(weixinPaySign);
    }

    /**
     * 获取有序的Map
     */

    public static SortedMap<String,String>  getSortedMap(Map<String,String> map){
        SortedMap<String,String> sortedMap = new TreeMap<>();
        Iterator<String> it = map.keySet().iterator();
        while(it.hasNext()){
            String key = it.next();
            String value = map.get(key);
            String temp="";
            if(null != value){
                temp = value.trim();
            }
            sortedMap.put(key,temp);
        }
        return sortedMap;
    }
}
