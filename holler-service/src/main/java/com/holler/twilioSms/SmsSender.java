package com.holler.twilioSms;

/**
 * Created by pravina on 29/03/16.
 */

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SmsSender {

    /* Find your sid and token at twilio.com/user/account */
    public static final String ACCOUNT_SID = "AC6f9019dfb615480f936cfd4957966e27";
    public static final String AUTH_TOKEN = "0b119b26cf69868d2a4e2381235f0e8d";

    public void sendSMS(Map<String, Object> result) throws TwilioRestException {

        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
        String msg = "Your verification code is" + result.get("otp");
        Account account = client.getAccount();

        MessageFactory messageFactory = account.getMessageFactory();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("To", "+919930297373")); // Replace with a valid phone number for your account.
        params.add(new BasicNameValuePair("From", "+14846854344")); // Replace with a valid phone number for your account.
        params.add(new BasicNameValuePair("Body", "msg"));
        Message sms = messageFactory.create(params);
    }

}