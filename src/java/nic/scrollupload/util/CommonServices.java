/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.scrollupload.util;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import nic.scrollupload.connection.TransactionManager;

import org.primefaces.context.RequestContext;

/**
 *
 * @author tranC075
 */
public class CommonServices {

    public static final String ERROR = "ERROR";
    public static final String INFO = "INFO";
    public static final String WARN = "WARN";
    public static final String FATAL = "FATAL";

    public static String getIpPath() {
        InetAddress ip;
        String ipPath = "";
        try {

            ip = InetAddress.getLocalHost();
            ipPath = ip.getHostAddress();

        } catch (UnknownHostException e) {

            e.printStackTrace();

        }
        return ipPath;
    }

    private static String getRandomNumber() {
        return String.valueOf(generateRandomNumber(0, 9));
    }

    private static char getRandomChar() {
        char c = 'a';
        if ((generateRandomNumber(1, 200) % 2) == 1) {
            c = (char) generateRandomNumber(65, 90);
        } else {
            c = (char) generateRandomNumber(97, 122);
        }
        return c;
    }

    public static int generateRandomNumber(int min, int max) {
        return ((int) (Math.random() * (max - min + 1)) + min);
    }

    public static String generateRandomAlphaNumeric(int no) {
        String rtnStr = "";
        for (int i = 0; i < no; i++) {
            if ((generateRandomNumber(1, 200) % 2) == 1) {
                rtnStr = rtnStr + getRandomNumber();
            } else {
                rtnStr = rtnStr + getRandomChar();
            }
        }
        return rtnStr;
    }

    public static String generateMD5(String password) {
        String md5pass = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(password.getBytes());

            byte byteData[] = md.digest();
            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            md5pass = sb.toString();

        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

        return md5pass;
    }

    private static FacesMessage.Severity getMessageSeverity(String severityCode) {
        if (CommonServices.ERROR.equalsIgnoreCase(severityCode)) {
            return FacesMessage.SEVERITY_ERROR;
        } else if (CommonServices.INFO.equalsIgnoreCase(severityCode)) {
            return FacesMessage.SEVERITY_INFO;
        } else if (CommonServices.WARN.equalsIgnoreCase(severityCode)) {
            return FacesMessage.SEVERITY_WARN;
        } else if (CommonServices.FATAL.equalsIgnoreCase(severityCode)) {
            return FacesMessage.SEVERITY_FATAL;
        } else {
            return FacesMessage.SEVERITY_ERROR;
        }
    }

    public static void showMessagesInDialog(String summary, String details, String severityCode) {
        RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(getMessageSeverity(severityCode), summary, details));
    }

    public static boolean isAlphaNumeric(String str) {
        //return varaible
        boolean result = true;
        //get the length of the string and stores in len of type int
        int len = str.length();
        for (int i = 0; i < len; i++) {
            //checks if specified character is letter or digit or unicode space character
            if (!(Character.isLetterOrDigit(str.charAt(i)) || Character.isSpaceChar(str.charAt(i)))) {
                result = false;
                break;
            }
        }
        return result;
    }

    public  String generateUniqueTransId() {

        String whereiam = "CommonServices.getUniqueTempTransactionId()";
        String strTempTransactionNo = "";
        TransactionManager tmgr = null;
        String tempSeries = "";
        long tempSequence = 0;

        try {
            tmgr = new TransactionManager(whereiam);
            Calendar cal = Calendar.getInstance();
            String monthYear = String.valueOf(cal.get(Calendar.YEAR)).substring(2, 4) + String.format("%02d", cal.get(Calendar.MONTH) + 1);

            String strSQL = "UPDATE recon.vm_id_seq SET file_SEQUENCE_NO=file_SEQUENCE_NO+1";
            PreparedStatement psmt = tmgr.prepareStatement(strSQL);

            psmt.executeUpdate();

            strSQL = "SELECT file_PREFIX FROM recon.vm_id_seq";
            psmt = tmgr.prepareStatement(strSQL);

            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                if (!monthYear.equalsIgnoreCase(rs.getString("file_PREFIX").trim())) {

                    strSQL = "UPDATE recon.vm_id_seq SET file_PREFIX = ?, file_SEQUENCE_NO = 1";
                    psmt = tmgr.prepareStatement(strSQL);
                    psmt.setString(1, monthYear);
                    psmt.executeUpdate();
                }
            }
            rs.close();

            strSQL = "SELECT file_PREFIX,file_SEQUENCE_NO FROM recon.vm_id_seq";
            psmt = tmgr.prepareStatement(strSQL);

            rs = psmt.executeQuery();
            if (rs.next()) {
                tempSeries = rs.getString("file_PREFIX").trim();
                tempSequence = rs.getLong("file_SEQUENCE_NO");
                               
            }

            String format = String.format("%%0%dd", 7);
          
            String z = String.format(format, tempSequence);
         
            strTempTransactionNo = tempSeries.concat(z);
         
         
//            try {
//                strTempTransactionNo = strTempTransactionNo + generateHashCode(strTempTransactionNo);
//            } catch (NoSuchAlgorithmException ex) {
//               ex.printStackTrace();
//            } catch (UnsupportedEncodingException ex) {
//              ex.printStackTrace();
//            }
          

            rs.close();
            psmt.close();
            rs = null;
            psmt = null;
            tmgr.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            try {
                tmgr.release();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return strTempTransactionNo;

    }

    public static int generateHashCode(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        int hashCode = 0;
        if (str != null && str.length() > 0) {
            String md5String = MD5(str);
            long asciiSum = getAsciiSum(md5String);
            System.out.println("asciiSum========="+asciiSum);
            hashCode = (int) (asciiSum % 10);
            System.out.println("hashCode======"+hashCode);
        }
        return hashCode;
    }

    public static String MD5(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] md5hash = new byte[32];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        md5hash = md.digest();
        return convertToHex(md5hash);
    }

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static long getAsciiSum(String str) {
        long asciiSum = 0;
        if (str != null && str.length() > 0) {
            for (int i = 0; i < str.length(); i++) {
                asciiSum = asciiSum + str.charAt(i);
            }
        }
        return asciiSum;
    }
}
