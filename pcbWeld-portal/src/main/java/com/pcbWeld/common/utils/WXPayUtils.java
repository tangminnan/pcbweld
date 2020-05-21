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
    public static final String APPID="123456";//公众号id
    public static final String APPSECRET="";//公众号secret
    public static final String MERID="123456";//微信商户号
    public static final String APPKEY="ABCDEFG" ;//支付秘钥
    public static final String CALLBACK = "";//支付回调
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
        Set<Map.Entry<String,String>> es = params.entrySet();
        Iterator<Map.Entry<String,String>> it = es.iterator();
        while(it.hasNext()){
            Map.Entry<String,String> entry = it.next();
            String k = entry.getKey();
            String v = entry.getValue();
            if(null!=v &&!"".equals(v) && !"sign".equals(v)){
                sb.append(k+"="+v+"&");
            }
        }
        sb.append("key=").append(key);
        String sign  =CommonUtils.MD5(sb.toString().toUpperCase());
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
