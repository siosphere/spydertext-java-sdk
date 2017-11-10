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
package com.spydertext.account;

import com.spydertext.account.sdk.AccountSDKException;
import com.spydertext.base.BaseApiException;
import com.spydertext.BaseSDK;
import java.io.IOException;
import org.json.JSONObject;

/**
 *
 * @author mkramer
 */
public class AccountSDK extends BaseSDK
{
    public static final String API_SINGLE_ACCOUNT_URL = "/api/v/1/account/{accountId}";
    public static final String API_ACCOUNT_URL = "/api/v/1/account";
    
    public JSONObject get(Integer accountId) throws AccountSDKException
    {
        try {
            JSONObject request = new JSONObject();
            request.put("accountId", accountId);

            JSONObject result = _get(API_SINGLE_ACCOUNT_URL, request);
            
            return result;
        } catch(BaseApiException|IOException ex) {
            throw new AccountSDKException("Failed to get account: " + ex.getMessage());
        }
    }
    
    public JSONObject getByPhone(String mobilePhone) throws AccountSDKException
    {
        try {
            JSONObject request = new JSONObject();
            request.put("mobile_phone", mobilePhone);

            JSONObject result = _get(API_ACCOUNT_URL, request);
            
            return result;
        } catch(BaseApiException|IOException ex) {
            throw new AccountSDKException("Failed to get account: " + ex.getMessage());
        }
    }
    
    public JSONObject getByEmail(String email) throws AccountSDKException
    {
        try {
            JSONObject request = new JSONObject();
            request.put("email", email);

            JSONObject result = _get(API_ACCOUNT_URL, request);
            
            return result;
        } catch(BaseApiException|IOException ex) {
            throw new AccountSDKException("Failed to get account: " + ex.getMessage());
        }
    }
    
    public JSONObject delete(Integer accountId) throws AccountSDKException
    {
        try {
            JSONObject request = new JSONObject();
            request.put("accountId", accountId);

            JSONObject result = _delete(API_SINGLE_ACCOUNT_URL, request);
            
            return result;
        } catch(BaseApiException|IOException ex) {
            throw new AccountSDKException("Failed to get account: " + ex.getMessage());
        }
    }
    
    public JSONObject save(JSONObject request) throws AccountSDKException
    {
        try {

            JSONObject result = _post(API_ACCOUNT_URL, request);
            
            return result;
        } catch(BaseApiException|IOException ex) {
            throw new AccountSDKException("Failed to save account: " + ex.getMessage());
        }
    }
    
    public JSONObject collection(JSONObject filters) throws AccountSDKException
    {
       try {

            JSONObject result = _get(API_ACCOUNT_URL, filters);
            
            return result;
        } catch(BaseApiException|IOException ex) {
            throw new AccountSDKException("Failed to get account collection: " + ex.getMessage());
        } 
    }
}
