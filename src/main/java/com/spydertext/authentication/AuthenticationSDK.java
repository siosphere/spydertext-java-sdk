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
package com.spydertext.authentication;

import com.spydertext.authentication.sdk.AuthenticationSDKException;
import com.spydertext.base.BaseApiException;
import com.spydertext.BaseSDK;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;
import org.json.JSONObject;

/**
 *
 * @author mkramer
 */
public class AuthenticationSDK extends BaseSDK
{
    public static final String API_DEVICE_TOKEN_URL = "/api/v/1/device/token";
    
    public JSONObject createDeviceToken() throws AuthenticationSDKException
    {
        try {
            String uniqueID = UUID.randomUUID().toString() + ":" + getHostname();
            JSONObject request = new JSONObject();
            request.put("device_id", uniqueID);

            return _post(API_DEVICE_TOKEN_URL, request);
        } catch(BaseApiException | IOException ex) {
            throw new AuthenticationSDKException("Failed to create device token: " + ex.getMessage());
        }
    }
    
    protected String getHostname()
    {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch(UnknownHostException ex) {
            return "";
        }
    }
}
