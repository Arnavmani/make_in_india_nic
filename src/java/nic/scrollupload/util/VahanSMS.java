/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.scrollupload.util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

/**
 *
 * @author tranC075
 */
public class VahanSMS {
    
    private static final Logger logger = Logger.getLogger(VahanSMS.class);

    public VahanSMS() {
    }

    public static String vahanEncryption(String strVal) {
        StringBuffer encVal = new StringBuffer();
        char ch[] = strVal.toCharArray();
        for (char c : ch) {
            encVal.append(Integer.toHexString((byte) c));
        }
        return encVal.toString();
    }

    public void sendVahanSms(String strMobileNo, String strMessage) {
        int respcd;
        try {
            strMobileNo = strMobileNo.replaceAll("[^0-9]", "");
            if (!strMobileNo.equalsIgnoreCase("")) {
                Boolean blnSendSms = Long.parseLong(strMobileNo) > 999999999 ? true : false;
                if (blnSendSms) {
                    strMessage = strMessage.replaceAll(" ", "%20");
                    String smsString = "mobileno=" + strMobileNo + "|" + "message=" + strMessage;

                    smsString = vahanEncryption(smsString);
                    // URL httpUrl = new URL("http://smsgw.nic.in/sendsms_nic/sendmsg.php?uname=vahan.auth&pass=Auth.vahan@123&send=VAAHAN&dest=91" + strMobileNo + "&msg=" + strMessage);
                    URL httpUrl = new URL("http://10.248.93.4:7080/SMS/proactivesms?encdata" + smsString);
                    URLConnection con = httpUrl.openConnection();
                    con.connect();
                    HttpURLConnection httpConnection = (HttpURLConnection) con;
                    respcd = httpConnection.getResponseCode();
                }
            }
        } catch (Exception ex) {
            logger.error("SMS Exception :", ex);
        }
    }

}
