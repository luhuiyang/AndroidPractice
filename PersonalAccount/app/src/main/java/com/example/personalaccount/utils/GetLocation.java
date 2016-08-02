package com.example.personalaccount.utils;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;

import com.example.personalaccount.activity.AddGetAccount;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2015/3/12.
 */
public class GetLocation {

//    public static void showLocation() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                    StringBuilder url = new StringBuilder();
//                    url.append("http://maps.googleapis.com/maps/api/geocode/json?latlng=");
//            }
//        });
//    }

    public static String getLocation() {
        return getDeviceAddressbyGeoPoint();
    }

    /**
     * 获取地址
     *
     * @return
     */

    private static String getDeviceAddressbyGeoPoint() {
        // 自经纬度取得地址
        LocationManager locationManager = (LocationManager) MyApplication.getContext()
                .getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location == null) {
            location = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);// 如果gps取不到就取网络的
        }

        // Log.d("H3c",
        // "k:" + location.getLatitude() + "-" + location.getLongitude());

        if (location == null) {
            String locaStr = getLocationByGSMCell();// 如果wifi网络无法获取就获取基站的
            if (locaStr != null) {
                return locaStr;
            }
        }

        if (location == null) {
            return "";// 无法获取该位置
        }

        String add = GetAddr(String.valueOf(location.getLatitude()),
                String.valueOf(location.getLongitude()));

        if (add != null) {
            return add;
        }

        return location.getLatitude() + "-" + location.getLongitude();
    }

    /**
     * 根据经纬度反向解析地址，有时需要多尝试几次
     * 注意:(摘自：http://code.google.com/intl/zh-CN/apis/maps/faq.html
     * 提交的地址解析请求次数是否有限制？) 如果在 24 小时时段内收到来自一个 IP 地址超过 2500 个地址解析请求， 或从一个 IP
     * 地址提交的地址解析请求速率过快，Google 地图 API 编码器将用 620 状态代码开始响应。 如果地址解析器的使用仍然过多，则从该 IP
     * 地址对 Google 地图 API 地址解析器的访问可能被永久阻止。
     *
     * @param latitude
     *            纬度
     * @param longitude
     *            经度
     * @return
     */
    private static String GetAddr(String latitude, String longitude) {
        String addr = "";
        // 也可以是http://maps.google.cn/maps/geo?output=csv&key=abcdef&q=%s,%s，不过解析出来的是英文地址
        // 密钥可以随便写一个key=abc
        // output=csv,也可以是xml或json，不过使用csv返回的数据最简洁方便解析
        String url = String.format(
                "http://ditu.google.cn/maps/geo?output=csv&key=abcdef&q=%s,%s",
                latitude, longitude);
        URL myURL = null;
        URLConnection httpsConn = null;
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

        try {
            httpsConn = (URLConnection) myURL.openConnection();
            if (httpsConn != null) {
                InputStreamReader insr = new InputStreamReader(
                        httpsConn.getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(insr);
                String data = null;
                if ((data = br.readLine()) != null) {
                    String[] retList = data.split(",");
                    if (retList.length > 2 && ("200".equals(retList[0]))) {
                        addr = retList[2];
                        addr = addr.replace("\"", "");
                        addr = addr.split(" ")[0];// 只获得地址，否则是地址+邮编
                    } else {
                        addr = "";
                    }
                }
                insr.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return addr;
    }

    private static String getLocationByGSMCell() {
        TelephonyManager tm = (TelephonyManager) MyApplication
                .getContext().getSystemService(Context.TELEPHONY_SERVICE);
        GsmCellLocation gsmCell = (GsmCellLocation) tm.getCellLocation();
        int cid = gsmCell.getCid();
        int lac = gsmCell.getLac();
        String netOperator = tm.getNetworkOperator();
        int mcc = Integer.valueOf(netOperator.substring(0, 3));
        int mnc = Integer.valueOf(netOperator.substring(3, 5));
        JSONObject holder = new JSONObject();
        JSONArray array = new JSONArray();
        JSONObject data = new JSONObject();
        try {
            holder.put("version", "1.1.0");
            holder.put("host", "maps.google.com");
            holder.put("address_language", "zh_CN");
            holder.put("request_address", true);
            holder.put("radio_type", "gsm");
            holder.put("carrier", "HTC");
            data.put("cell_id", cid);
            data.put("location_area_code", lac);
            data.put("mobile_countyr_code", mcc);
            data.put("mobile_network_code", mnc);
            array.put(data);
            holder.put("cell_towers", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://www.google.com/loc/json");
        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(holder.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpPost.setEntity(stringEntity);
        HttpResponse httpResponse = null;
        try {
            httpResponse = client.execute(httpPost);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity httpEntity = httpResponse.getEntity();
        InputStream is = null;
        try {
            is = httpEntity.getContent();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isr);
        StringBuffer stringBuffer = new StringBuffer();
        try {
            String result = "";
            while ((result = reader.readLine()) != null) {
                stringBuffer.append(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String location = stringBuffer.toString();
        try {
            JSONTokener jsonParser = new JSONTokener(location);
            JSONObject person = (JSONObject) jsonParser.nextValue();
            JSONObject oj1 = person.getJSONObject("location");
            JSONObject oj2 = oj1.getJSONObject("address");
            String region = oj2.getString("region");
            String city = oj2.getString("city");
            String street = oj2.getString("street");
            return region + city + street;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
