package com.holler.twilioSms;

/**
 * Created by pravina on 29/03/16.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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

    public static final String BULK_SMS_USERNAME = HollerProperties.getInstance().getValue("bulksmsgateway.account.username");
    public static final String BULK_SMS_PASSWORD =HollerProperties.getInstance().getValue("bulksmsgateway.account.password");
    public static final String BULK_SMS_SENDER_ID =HollerProperties.getInstance().getValue("bulksmsgateway.account.sender.id");
    public static final String JOB_POSTED_UPDATE_NUMBER =HollerProperties.getInstance().getValue("job.posted.update.sms.number");
    public static final String BULK_SMS_TYPE =HollerProperties.getInstance().getValue("bulksmsgateway.account.type");

    public void sendSMS(Map<String, Object> result) throws TwilioRestException {
        log.info("sendSMS :: called");
        String phoneNumber = (String) result.get("phoneNumber");
        String msg = result.get("otp") + " is your verification code for hollerindia. This is usable only once and is only valid for 2 min. PLS DO NOT SHARE WITH ANYONE ";
        try
        {
        // Construct data
            String data="user=" + URLEncoder.encode(BULK_SMS_USERNAME, "UTF-8");
            data +="&password=" + URLEncoder.encode(BULK_SMS_PASSWORD, "UTF-8");
            data +="&message=" + URLEncoder.encode(msg , "UTF-8");
            data +="&sender=" + URLEncoder.encode(BULK_SMS_SENDER_ID, "UTF-8");
            data +="&mobile=" + URLEncoder.encode(phoneNumber, "UTF-8");
            data +="&type=" + URLEncoder.encode(BULK_SMS_TYPE, "UTF-8");
            URL url = new URL("http://login.bulksmsgateway.in/sendmessage.php?"+data);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String sResult1="";
            while ((line = rd.readLine()) != null)
            {
            // Process line...
                sResult1=sResult1+line+" ";
            }
            wr.close();
            rd.close();
        }
        catch (Exception e)
        {
            log.info("Error in sendSMS :: phoneNumber {} will be sent msg {}", phoneNumber, msg);
        }

        log.info("sendSMS :: phoneNumber {} will be sent msg {}", phoneNumber, msg);
    }

    public void sendJobPostedSMS(String jobTitle, String jobDescription) throws TwilioRestException {
        log.info("sendJobPostedSMS :: called");
        StringBuilder msg =  new StringBuilder("New job had been posted with title: ").append(jobTitle).append(" and description: ").append(jobDescription);
        try
        {
            // Construct data
            String data="user=" + URLEncoder.encode(BULK_SMS_USERNAME, "UTF-8");
            data +="&password=" + URLEncoder.encode(BULK_SMS_PASSWORD, "UTF-8");
            data +="&message=" + URLEncoder.encode(msg.toString() , "UTF-8");
            data +="&sender=" + URLEncoder.encode(BULK_SMS_SENDER_ID, "UTF-8");
            data +="&mobile=" + URLEncoder.encode(JOB_POSTED_UPDATE_NUMBER, "UTF-8");
            data +="&type=" + URLEncoder.encode(BULK_SMS_TYPE, "UTF-8");
            URL url = new URL("http://login.bulksmsgateway.in/sendmessage.php?"+data);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String sResult1="";
            while ((line = rd.readLine()) != null)
            {
                // Process line...
                sResult1=sResult1+line+" ";
            }
            wr.close();
            rd.close();
        }
        catch (Exception e)
        {
            log.info("Error in sendJobPostedSMS");
        }
    }

    public void sendWelcomeMsgSMS(String phoneNumber, String name) throws TwilioRestException {
        log.info("sendWelcomeMsgSMS :: called");
        String msg =  "Dear " + name + ",\n" +
                "Thank - You for joining Holler.\n" +
                "\n" +
                "Our goal is to help you connect with right people around, to get your work done.\n" +
                "In parallel, it gives opportunities to job seekers around you.\n" +
                "\n" +
                "you can also search for opportunities and help others. ";
        try
        {
            // Construct data
            String data="user=" + URLEncoder.encode(BULK_SMS_USERNAME, "UTF-8");
            data +="&password=" + URLEncoder.encode(BULK_SMS_PASSWORD, "UTF-8");
            data +="&message=" + URLEncoder.encode(msg.toString() , "UTF-8");
            data +="&sender=" + URLEncoder.encode(BULK_SMS_SENDER_ID, "UTF-8");
            data +="&mobile=" + URLEncoder.encode(phoneNumber, "UTF-8");
            data +="&type=" + URLEncoder.encode(BULK_SMS_TYPE, "UTF-8");
            URL url = new URL("http://login.bulksmsgateway.in/sendmessage.php?"+data);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String sResult1="";
            while ((line = rd.readLine()) != null)
            {
                // Process line...
                sResult1=sResult1+line+" ";
            }
            wr.close();
            rd.close();
        }
        catch (Exception e)
        {
            log.info("Error in sendWelcomeMsgSMS");
        }
    }

}