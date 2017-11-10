/*
 * The MIT License
 *
 * Copyright 2017 SpyderText Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.spydertext;

import com.spydertext.base.BaseApiException;
import java.io.IOException;
import java.io.File;
import java.net.URLEncoder;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.FileEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 *
 * @author mkramer
 */
public class BaseSDK 
{
    protected static final String BASE_URL = "dev.spydertext.com";
    protected static final String TOKEN_HEADER = "SPYDR-Token";
    protected static final String DEVICE_TOKEN_HEADER = "SPYDR-DeviceToken";
    
    public JSONObject next(String url) throws BaseApiException, IOException
    {
        return _get(url, null);
    }
    
    protected JSONObject _get(String method, JSONObject data) throws BaseApiException, IOException
    {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
        try {
            String url = buildUrl(method, data, true);
            HttpGet httpget = new HttpGet(url);
            signRequest(httpget);

            System.out.println("REQUEST: " + httpget.getRequestLine());
            String responseBody = httpclient.execute(httpget, getResponseHandler());
            httpget.releaseConnection();
            return new JSONObject(responseBody);
        }
        finally {
            httpclient.close();
        }
    }
    
    protected JSONObject _post(String method, JSONObject data) throws BaseApiException, IOException
    {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
        try {
            String url = buildUrl(method, data, false);
            HttpPost httppost = new HttpPost(url);
            httppost.addHeader("Content-Type", "application/json");
            StringEntity jsonEntity = new StringEntity(data != null ? data.toString() : "");
            httppost.setEntity(jsonEntity);
            signRequest(httppost);

            System.out.println("REQUEST: " + httppost.getRequestLine());
            String responseBody = httpclient.execute(httppost, getResponseHandler());
            httppost.releaseConnection();
            return new JSONObject(responseBody);
        }
        finally {
            httpclient.close();
        }
    }
    
    protected JSONObject _file(String method, File file) throws BaseApiException, IOException
    {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
        try {
            String url = buildUrl(method, null, false);
            HttpPost httppost = new HttpPost(url);
            httppost.addHeader("Content-Type", "multipart/form-data");
            FileEntity fileEntity = new FileEntity(file);
            httppost.setEntity(fileEntity);
            signRequest(httppost);

            System.out.println("REQUEST: " + httppost.getRequestLine());
            String responseBody = httpclient.execute(httppost, getResponseHandler());
            httppost.releaseConnection();
            return new JSONObject(responseBody);
        }
        finally {
            httpclient.close();
        }
    }
    
    protected JSONObject _delete(String method, JSONObject data) throws BaseApiException, IOException
    {
        System.out.println(data.toString());
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
        try {
            String url = buildUrl(method, data, true);
            HttpDelete httpdelete = new HttpDelete(url);
            signRequest(httpdelete);

            System.out.println("REQUEST: " + httpdelete.getRequestLine());
            String responseBody = httpclient.execute(httpdelete, getResponseHandler());
            httpdelete.releaseConnection();
            return new JSONObject(responseBody);
        }
        finally {
            httpclient.close();
        }
    }
    
    protected void signRequest(HttpRequestBase request) throws BaseApiException
    {
        if(SpyderText.getApiKey() == null) {
            throw new BaseApiException("Invalid API Credentials");
        }
        request.addHeader(TOKEN_HEADER, SpyderText.getApiKey());
        
        if(SpyderText.getDeviceToken() != null) {
            request.addHeader(DEVICE_TOKEN_HEADER, SpyderText.getDeviceToken());
        }
    }
    
    
    protected ResponseHandler<String> getResponseHandler()
    {
        return new ResponseHandler<String>() {

            @Override
            public String handleResponse(
                    final HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else if(status == 400) {
                    throw new ClientProtocolException("Invalid Resource");
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }

        };
    }
    
    protected String buildUrl(String method, JSONObject params, Boolean addToQuery)
    {
        String finalMethod = method;
        if(params != null) {
            for( int i = 0; params.names() != null && i < params.names().length(); i++){

                String key = params.names().getString(i);
                String currentMethod = finalMethod;

                finalMethod = finalMethod.replace("{" + key + "}", params.get(key).toString());
                if(!finalMethod.equals(currentMethod)) {
                    params.remove(key);
                }
            }
        }
        
        String queryStr = "";
        if(params != null && addToQuery && params.keySet().size() > 0) {
            for( int i = 0; params.names() != null && i < params.names().length(); i++){
                String key = params.names().getString(i);
                if(i == 0) {
                    queryStr = "?";
                } else {
                    queryStr = queryStr + "&";
                }
                queryStr = queryStr + URLEncoder.encode(key) + "=" + URLEncoder.encode(params.getString(key));
                i++;
            }
        }
        
        return "https://" + BASE_URL + finalMethod + queryStr;
    }
}
