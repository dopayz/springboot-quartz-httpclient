package com.yudu.face.job;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yudu.face.config.FaceKqConfiguration;
import com.yudu.face.model.InspectInfo;
import com.yudu.face.model.UserInfo;
import com.yudu.face.repository.InspectInfoRepository;
import com.yudu.face.repository.UserInfoRepository;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class FaceKqFifthTask {
    @Autowired
    private InspectInfoRepository inspectInfoRepository;

    @Autowired
    private FaceKqConfiguration faceKqConfiguration;

    @Autowired
    private UserInfoRepository userInfoRepository;

    private static String overTime = null;

    public static boolean isToday(String d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (d != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(sdf.parse(d));
                cal.add(Calendar.SECOND,1);
                Date date=cal.getTime();
                d = sdf.format(date);
                overTime = d;
                String d1 = d.split(" ")[0];
                String d2 = sdf.format(new Date()).split(" ")[0];
                return d1.equals(d2);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
    //job
    public void face(){
        //读取人脸识别api接口数据，并做入库操作
        String url =faceKqConfiguration.faceKqUrl5;
        String[] strArr = url.split(":");
        String ip = strArr[1].replace("//", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject resultJson = null;
        CloseableHttpClient  client = HttpClients.createDefault();
        try {
            String now = sdf.format(new Date());
            now=now.split(" ")[0]+" 06:00:00";
            String nowEnd = now.split(" ")[0]+" 23:59:59";
            if(!isToday(overTime)){
                url+="?q=info&start="+URLEncoder.encode(now,"UTF-8")+"&end="+URLEncoder.encode(nowEnd,"UTF-8");
            }else{
                url+="?q=info&start="+URLEncoder.encode(overTime,"UTF-8")+"&end="+URLEncoder.encode(nowEnd,"UTF-8");
            }
            //1.httpclient获取人脸考勤接口数据
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000)   //设置连接超时时间
                    .setConnectionRequestTimeout(5000) // 设置请求超时时间
                    .setSocketTimeout(5000)
                    .setRedirectsEnabled(true)//默认允许自动重定向
                    .build();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            String srtResult =null;
            HttpResponse httpResponse  = client.execute(httpGet);
            httpResponse.setHeader("Content-Type","application/json");//设置返回数据格式
            if(httpResponse.getStatusLine().getStatusCode() == 200){
                srtResult = EntityUtils.toString(httpResponse.getEntity());//获得返回的结果
                resultJson = JSONObject.parseObject(srtResult);
                JSONArray arr=resultJson.getJSONArray("data");
                saveInspectInfo(arr,ip);
                if(!arr.isEmpty()){
                    overTime = arr.getJSONObject(arr.size()-1).getString("recordtime");
                    face();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void saveInspectInfo(JSONArray arr,String ip){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Calendar cal = Calendar.getInstance();
                for(int i=0;i<arr.size();i++){
                    InspectInfo inspectInfo = new InspectInfo();
                    String recordtime=arr.getJSONObject(i).getString("recordtime");
                    String userno=arr.getJSONObject(i).getString("userno");
                    String name=arr.getJSONObject(i).getString("name");
                    int result=arr.getJSONObject(i).getInteger("result");
                    String roletype =arr.getJSONObject(i).getString("roletype");
                    Date sortTime = sdf.parse(recordtime);
                    cal.setTime(sortTime);
                    if(result ==1 && roletype.equals("staffer")){
                        //验证数据是否重复
                        if(inspectInfoRepository.checkRepeat(recordtime) ==0) {
                            if ("曾衤".equals(name))
                                name = "曾祎";
                            if ("殷坤".equals(name))
                                name = "殷堃";
                            UserInfo userInfo = userInfoRepository.getUserInfo(name);
                            if(userInfo!=null) {
                                inspectInfo.setId(UUID.randomUUID().toString().replace("-", ""));
                                inspectInfo.setUserName(name);
                                inspectInfo.setMip((new StringBuilder("在")).append(ip).append("中的用户名为:*").append(name).append("*").toString());
                                inspectInfo.setUserInfo(userInfoRepository.getUserInfo(name).getUserInfoId());
                                inspectInfo.setTimes(recordtime);
                                inspectInfo.setSortTime(sortTime);
                                inspectInfo.setCountTime(new Date());
                                inspectInfo.setYear(cal.get(Calendar.YEAR));
                                inspectInfo.setMonth(cal.get(Calendar.MONTH) + 1);
                                inspectInfo.setDay(cal.get(Calendar.DATE));
                                inspectInfo.setHour(cal.get(Calendar.HOUR_OF_DAY));
                                inspectInfo.setMinute(cal.get(Calendar.MINUTE));
                                inspectInfo.setSecond(cal.get(Calendar.SECOND));
                                inspectInfoRepository.insert(inspectInfo);
                            }
                        }
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
