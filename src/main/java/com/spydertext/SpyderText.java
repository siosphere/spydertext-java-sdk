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

import com.spydertext.account.AccountSDK;
import com.spydertext.authentication.AuthenticationSDK;
import com.spydertext.message.MessageSDK;

/**
 *
 * @author mkramer
 */
public class SpyderText 
{
    public static AccountSDK accountSDK;
    public static AuthenticationSDK authenticationSDK;
    public static MessageSDK messageSDK;
    
    private static String apiKey;
    
    private static String deviceToken;
    
    public static void init(String apiKey)
    {
        SpyderText.apiKey = apiKey;
    }
    
    public static void init(String apiKey, String deviceToken)
    {
        SpyderText.apiKey = apiKey;
        SpyderText.deviceToken = deviceToken;
    }
    
    public static AccountSDK Account()
    {
        if(accountSDK != null) {
            return accountSDK;
        }
        
        return accountSDK = new AccountSDK();
    }
    
    public static AuthenticationSDK Authentication()
    {
        if(authenticationSDK != null) {
            return authenticationSDK;
        }
        
        return authenticationSDK = new AuthenticationSDK();
    }
    
    public static MessageSDK Message()
    {
        if(messageSDK != null) {
            return messageSDK;
        }
        
        return messageSDK = new MessageSDK();
    }
    
    public static String getApiKey()
    {
        return apiKey;
    }
    
    public static String getDeviceToken()
    {
        return deviceToken;
    }
}
