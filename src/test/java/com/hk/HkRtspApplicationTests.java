package com.hk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hikvision.cms.api.common.util.Digests;
import com.hikvision.cms.api.common.util.HttpClientSSLUtils;
import com.hk.domain.CamerasEx;
import com.hk.domain.Encoders;
import com.hk.domain.Equip;
import com.hk.dto.JsonCamerasExDTO;
import com.hk.dto.JsonEncodersDTO;
import com.hk.service.CamerasExService;
import com.hk.service.EncodersService;
import com.hk.service.EquipService;
import com.hk.service.MedioInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class HkRtspApplicationTests {
    @Autowired
    private EncodersService encodersService;
    @Autowired
    private CamerasExService camerasExService;
    @Value("${hk.httpUrl}")
    private String url;
    @Value("${hk.appkey}")
    private String appkey;
    @Value("${hk.secret}")
    private String secret;
    @Value("${hk.opUserUuid}")
    private String opUserUuid;

    @Test
    public void contextLoads() {
    }

    /**
     * 查询设备
     *
     * @throws Exception
     */
    @Test
    public void saveEncodersService() throws Exception {
        String path = url + "/openapi/service/vss/res/getEncoders";
        Map<String, Object> map = new HashMap<String, Object>();
        //设置APPKEY
        map.put("appkey", appkey);
        //设置时间参数
        map.put("time", System.currentTimeMillis());
        map.put("pageNo", 1);
        map.put("pageSize", 58);
        map.put("opUserUuid", opUserUuid);
        String params = JSON.toJSONString(map);
        log.info("====================>>>GetDefaultUserUUID_Https 请求参数：[{}]", params);
        String data = HttpClientSSLUtils.doPost(path + "?token=" + Digests.buildToken(path + "?" + params, null, secret), params);
        System.out.println("================>>>" + JSON.parseObject(data).get("data"));
        JSON.parseObject(data).get("data");
        List<Encoders> encoders = JSONObject.parseObject(JSON.parseObject(data).get("data").toString(), JsonEncodersDTO.class).getList();
        encodersService.save(encoders);
        //System.out.println(JSON.toJSONString());
        //	System.out.println(" ====== testGetDefaultUserUUID 请求返回结果：【{" + data + "}】");
    }

    /**
     * 查询监控点
     */
    @Test
    public void getCamerasEx() throws Exception {
        String path = url + "/openapi/service/vss/res/getCamerasEx";
        Map<String, Object> map = new HashMap<String, Object>();
        //设置APPKEY
        map.put("appkey", appkey);
        //设置时间参数
        map.put("time", System.currentTimeMillis());
        map.put("pageNo", 1);
        map.put("pageSize", 869);
        map.put("opUserUuid", opUserUuid);
        String params = JSON.toJSONString(map);
        log.info("====================>>>GetDefaultUserUUID_Https 请求参数：[{}]", params);
        String data = HttpClientSSLUtils.doPost(path + "?token=" + Digests.buildToken(path + "?" + params, null, secret), params);
        System.out.println("================>>>" + JSON.parseObject(data).get("data"));
        JSON.parseObject(data).get("data");
        List<CamerasEx> encoders = JSONObject.parseObject(JSON.parseObject(data).get("data").toString(), JsonCamerasExDTO.class).getList();
        System.out.println(encoders);
        camerasExService.save(encoders);
    }

    /**
     * 获取网域UUID
     */
    @Test
    public void wangyuUUID() throws Exception {
        String path = url + "/openapi/service/base/netZone/getNetZones";
        Map<String, Object> map = new HashMap<String, Object>();
        //设置APPKEY
        map.put("appkey", appkey);
        //设置时间参数
        map.put("time", System.currentTimeMillis());
        map.put("opUserUuid", opUserUuid);
        String params = JSON.toJSONString(map);
        log.info("====================>>>GetDefaultUserUUID_Https 请求参数：[{}]", params);
        String data = HttpClientSSLUtils.doPost(path + "?token=" + Digests.buildToken(path + "?" + params, null, secret), params);
        System.out.println("================>>>" + data);

    }
//7b0b464bc27f4bb1b227cc6ea213b46f   //a456954df0434e398d5e1b5a5a624ae7

    /**
     * 根据监控点UUID和网域UUID获取预览参数
     */
    @Autowired
    MedioInfoService previewParamService;


    public String params() throws Exception {
        String path = url + "/openapi/service/vss/preview/getPreviewParamByCameraUuid";
        Map<String, Object> map = new HashMap<String, Object>();
        //设置APPKEY
        map.put("appkey", appkey);
        //设置时间参数
        map.put("time", System.currentTimeMillis());
        map.put("opUserUuid", opUserUuid);
        map.put("cameraUuid", "7b0b464bc27f4bb1b227cc6ea213b46f");
        map.put("netZoneUuid", "a456954df0434e398d5e1b5a5a624ae7");
        String params = JSON.toJSONString(map);
        log.info("====================>>>GetDefaultUserUUID_Https 请求参数：[{}]", params);
        String data = HttpClientSSLUtils.doPost(path + "?token=" + Digests.buildToken(path + "?" + params, null, secret), params);
        //	System.out.println("================>>>"+data);
        String a = StringUtils.replace(data, "\\", "");
        //String b = StringUtils.replace(a, ",", ":");

        String s = new String(a.getBytes(), "UTF-8");
        // System.out.println("================>>>" + a);
        // Long sss = System.currentTimeMillis();
        // PreviewParam previewParam = new PreviewParam();
        // Map list = new HashMap();
        // list.put("cameraUuid:like", "7b0b464bc27f4bb1b227cc6ea213b46f");
        //List<CamerasEx> camerasExes = camerasExService.list(list);
        //previewParam.setData(data).setEndData(a)
        //.setCamera(camerasExes.get(0).getCameraName());
        //previewParamService.save(previewParam);
        //JsonPreviewDTO.DateResult result=JSONObject.parseObject(a, JsonPreviewDTO.class).getData();
        //Document document=DocumentHelper.parseText(data);
        //System.out.println(result);
        return data;
    }

    @Test
    public void aaa() throws InterruptedException {

        List<CamerasEx> camerasExes = camerasExService.findAll();
        CountDownLatch countDownLatch = new CountDownLatch(camerasExes.size());
        for (CamerasEx camerasEx : camerasExes) {
            executorService.submit(new SaveDataTask(camerasEx, previewParamService, countDownLatch));

        }
        countDownLatch.await();
    }

    private static ExecutorService executorService = Executors.newFixedThreadPool(30);

    public static String getStringFile(String xml) throws Exception {
        String downupload = "";

        String statusResult = ""; // 存放应答消息中<status>元素的result属性
        String statusText = ""; // 存放应答消息中<status>元素的text

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        org.w3c.dom.Document doc = db.parse(new ByteArrayInputStream(xml.getBytes()));

        // xml中元素的变量命名均以_符号起始.下同
        Element _message = doc.getDocumentElement();

        // 取二级元素content
        NodeList _contentList = _message.getElementsByTagName("content");

        if (_contentList.getLength() == 1) {
            Element _content = (Element) _contentList.item(0);

            // 取三级元素status
            NodeList _statusList = _content.getElementsByTagName("status");
            if (_statusList.getLength() == 1) {
                Element _status = (Element) _statusList.item(0);
                Text t = (Text) _status.getFirstChild();
                statusText = t.getNodeValue();
                statusResult = _status.getAttribute("result");
            }

            if (statusResult.equals("exception") || statusResult.equals("error")) {
                System.out.println(statusText);
                throw new Exception(statusText);
            } else if (statusResult.equals("normal")) {
                downupload = _content.getElementsByTagName("resultPath").item(0).getFirstChild().getNodeValue();
                //resultPath节点的value值
                System.out.println("resultPath节点的value值:" + downupload);
            }
        }
        return downupload;
    }

    @Autowired
    private EquipService equipService;

    /**
     * 轩辕故里收费站  97f982df58f944dc95ffce0daefc7f8f
     *
     * @throws Exception
     */
    @Test
    public void main22() throws Exception {
        Map param = Maps.newHashMap();
        param.put("encoderUuid", "65c8b29042c043548b4f7a590d819fb1");
        List<CamerasEx> equips = camerasExService.list(param);
        for (CamerasEx camerasEx : equips) {
            StringBuilder sb=new StringBuilder();
            sb.append("ws://218.28.235.126:9090/ch2?vedio=")
                    .append(camerasEx.getCameraUuid());
            Equip basEquip=new Equip();
            basEquip.setEquipConnection(camerasEx.getCameraUuid())
                    .setCreateTime(new Date())
                    .setEquipName(camerasEx.getCameraName())
            .setEquipType("equip1").setRoad("西南绕城高速")
            .setUnderWho("轩辕故里收费站").setType(3L)
            .setVideoAddress(sb.toString());
            equipService.save(basEquip);
        }
        System.out.println(equips.size());
    }
    /**
     * 十八里河
     */
    @Test
    public void main223() throws Exception {
        Map param = Maps.newHashMap();
        param.put("encoderUuid", "4f211706e73145b380e3d136c908e002");
        List<CamerasEx> equips = camerasExService.list(param);
        for (CamerasEx camerasEx : equips) {
            StringBuilder sb=new StringBuilder();
            sb.append("ws://218.28.235.126:9090/ch2?vedio=")
                    .append(camerasEx.getCameraUuid());
            Equip basEquip=new Equip();
            basEquip.setEquipConnection(camerasEx.getCameraUuid())
                    .setCreateTime(new Date())
                    .setEquipName(camerasEx.getCameraName())
                    .setEquipType("equip1").setRoad("G4京港澳高速")
                    .setUnderWho("圃田收费站").setType(2L)
                    .setVideoAddress(sb.toString());
            equipService.save(basEquip);
        }
        System.out.println(equips.size());
    }


}
