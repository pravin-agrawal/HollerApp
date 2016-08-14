package com.holler.twilioSms;

/**
 * Created by pravina on 29/03/16.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.holler.holler_dao.util.HollerProperties;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Message;

@Service
public class SmsSender {

	static final Logger log = LogManager.getLogger(SmsSender.class.getName());
	
    /* Find your sid and token at twilio.com/user/account */
    public static final String ACCOUNT_SID = HollerProperties.getInstance().getValue("twilio.account.sid");
    public static final String AUTH_TOKEN = HollerProperties.getInstance().getValue("twilio.auth.token");

    public void sendSMS(Map<String, Object> result) throws TwilioRestException {
    	log.info("sendSMS :: called");
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
        String phoneNumber = (String) result.get("phoneNumber");
        String msg = "Your verification code is" + result.get("otp");
        log.info("sendSMS :: phoneNumber {} will be sent msg {}", phoneNumber, msg);
        Account account = client.getAccount();
        
        MessageFactory messageFactory = account.getMessageFactory();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("To", "+91"+phoneNumber)); // Replace with a valid phone number for your account.
        params.add(new BasicNameValuePair("From", "+14846854344")); // Replace with a valid phone number for your account.
        params.add(new BasicNameValuePair("Body", msg));
        Message sms = messageFactory.create(params);
    }

}