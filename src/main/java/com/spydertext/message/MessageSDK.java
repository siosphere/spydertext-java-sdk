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
package com.spydertext.message;

import com.spydertext.message.sdk.Message;
import com.spydertext.message.sdk.MessageSDKException;
import com.spydertext.base.BaseApiException;
import com.spydertext.BaseSDK;
import java.io.IOException;
import org.json.JSONObject;

/**
 *
 * @author mkramer
 */
public class MessageSDK extends BaseSDK
{
    public static final String API_SINGLE_MESSAGE_URL = "/api/v/1/message/{messageId}";
    public static final String API_MESSAGE_URL = "/api/v/1/message";
    
    public Message get(Integer messageId) throws MessageSDKException
    {
        try {
            JSONObject request = new JSONObject();
            request.put("messageId", messageId);

            Message result = new Message(_get(API_SINGLE_MESSAGE_URL, request));
            
            return result;
        } catch(BaseApiException|IOException ex) {
            throw new MessageSDKException("Failed to get message: " + ex.getMessage());
        }
    }
    
    public JSONObject collection(JSONObject filters) throws MessageSDKException
    {
       try {

            JSONObject result = _get(API_MESSAGE_URL, filters);
            
            return result;
        } catch(BaseApiException|IOException ex) {
            throw new MessageSDKException("Failed to get message collection: " + ex.getMessage());
        } 
    }
    
    public Message send(Message message) throws MessageSDKException
    {
        try {

            Message result = new Message(_post(API_MESSAGE_URL, message));
            
            return result;
        } catch(BaseApiException|IOException ex) {
            throw new MessageSDKException("Failed save message: " + ex.getMessage());
        }
    }
}
