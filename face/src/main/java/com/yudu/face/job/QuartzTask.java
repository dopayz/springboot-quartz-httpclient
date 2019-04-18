package com.yudu.face.job;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yudu.face.config.FaceKqConfiguration;
import com.yudu.face.model.InspectInfo;
import com.yudu.face.repository.InspectInfoRepository;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class QuartzTask {
    @Autowired
    private InspectInfoRepository inspectInfoRepository;

    @Autowired
    private FaceKqConfiguration faceKqConfiguration;

    public static boolean isToday(Date d){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(d);
        cal2.setTime(new Date());
        return cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE);
    }
    //job
    public void face(){
        //读取人脸识别api接口数据，并做入库操作
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String url =faceKqConfiguration.url;
        String[] strArr = url.split(":");
        String ip = strArr[1].replace("//", "");
        if(!isToday(inspectInfoRepository.getLastRecord().getSortTime())){
            url+="?q=info&start="+sdf.format(new Date());
        }else{
            url+="?q=info&start="+sdf.format(inspectInfoRepository.getLastRecord().getSortTime());
        }
        //URL中的空格可以用+号或者编码(%20)
        url= url.replace(" ","+");
        //1.httpclient获取人脸考勤接口数据
        CloseableHttpClient  client = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)   //设置连接超时时间
                .setConnectionRequestTimeout(5000) // 设置请求超时时间
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)//默认允许自动重定向
                .build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        String srtResult =null;
        JSONObject resultJson = null;
        try {
            HttpResponse httpResponse  = client.execute(httpGet);
            httpResponse.setHeader("Content-Type","application/json");//设置返回数据格式
            if(httpResponse.getStatusLine().getStatusCode() == 200){
                srtResult = EntityUtils.toString(httpResponse.getEntity());//获得返回的结果
                resultJson = JSONObject.parseObject(srtResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                //测试数据
                resultJson = new JSONObject();
                resultJson.put("code", 200);
                resultJson.put("msg", "成功");
                resultJson.put("data", "[{'id':1,'result':1,'recordtime':'2019-04-15 8:35:00','userno':'1','name':'ccc'}," +
                        "{'id':2,'result':0,'recordtime':'2019-04-15 8:35:00','userno':'10','name':'刘仁菊'}," +
                        "{'id':3,'result':1,'recordtime':'2019-04-15 9:35:00','userno':'15','name':'韩小刚'}," +
                        "{'id':4,'result':1,'recordtime':'2019-04-15 12:35:00','userno':'4028828b581c1dc901581ddd9b610240','name':'殷堃'}," +
                        "{'id':5,'result':0,'recordtime':'2019-04-15 16:35:00','userno':'24','name':'曾祎'}]");
                Calendar cal = Calendar.getInstance();
                JSONArray arr=resultJson.getJSONArray("data");
                for(int i=0;i<arr.size();i++){
                    InspectInfo inspectInfo = new InspectInfo();
                    String recordtime=arr.getJSONObject(i).getString("recordtime");
                    String userno=arr.getJSONObject(i).getString("userno");
                    String name=arr.getJSONObject(i).getString("name");
                    int result=arr.getJSONObject(i).getInteger("result");
                    Date sortTime = sdf.parse(recordtime);
                    cal.setTime(sortTime);
                    if(result ==1){
                        //验证数据是否重复
                        if(inspectInfoRepository.checkRepeat(userno,cal.get(Calendar.DATE)) ==1) {
                            if ("曾衤".equals(name))
                                name = "曾祎";
                            if ("殷坤".equals(name))
                                name = "殷堃";
                            inspectInfo.setId(UUID.randomUUID().toString().replace("-", ""));
                            inspectInfo.setUserName(name);
                            inspectInfo.setMip((new StringBuilder("在")).append(ip).append("中的用户名为:*").append(name).append("*").toString());
                            inspectInfo.setUserInfo(userno);
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
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
