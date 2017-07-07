/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impl;

import Dobjdetails.Dobjdetails;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import nic.scrollupload.connection.TransactionManager;

/**
 *
 * @author sourabh
 */
public class Database {

    public void hello(Dobjdetails dobj) {
        try {

            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/getdata", "postgres", "9807074172");
             String sql = "INSERT INTO registration(firstname,lastname,email,aadharno,password,conpassword,gender,fname,dob,phoneno,peradd,tempadd,state,pincode,loc,seatno,ip,emercont,emerrel)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?, ?, ?, ?, ? )";


            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, dobj.getFirstname());
            stmt.setString(2, dobj.getLastname());
            stmt.setString(3, dobj.getEmail());
            stmt.setString(4, dobj.getPassword());
            stmt.setString(5, dobj.getGender());
            stmt.setString(6, dobj.getFathersname());
            stmt.setDate(7,  (Date)dobj.getDob());
            int i = stmt.executeUpdate();
            con.close();

        } catch (Exception e) {
            System.out.println("connection failed");
            e.printStackTrace();

        }
    }
    
        
        


    public void hello1(Dobjdetails dobj) {
        PreparedStatement ps = null;
        TransactionManager tmgr = null;

        try {
            tmgr = new TransactionManager("hellooooooo");
          String sql = "INSERT INTO registration(firstname,lastname,email,aadharno,password,conpassword,gender,fname,dob,phoneno,peradd,tempadd,state,pincode,loc,seatno,ip,emercont,emerrel)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?, ?, ?, ?, ? )";

            ps = tmgr.prepareStatement(sql);

            ps.setString(1, dobj.getFirstname());
            ps.setString(2, dobj.getLastname());
            ps.setString(3, dobj.getEmail());
            ps.setInt(4, dobj.getAadharno());
            ps.setString(5, dobj.getPassword());
            ps.setString(6, dobj.getConfirmpassword());
            ps.setString(7, dobj.getGender());
            ps.setString(8, dobj.getFathersname());
            ps.setDate(9,  new java.sql.Date(dobj.getDob().getTime()));
            ps.setInt(10, dobj.getPhoneno());
            ps.setString(11, dobj.getPermanentaddress());
            ps.setString(12, dobj.getTemporaryaddress());
            ps.setString(13, dobj.getState());
            ps.setInt(14, dobj.getPincode());
            ps.setString(15, dobj.getLoc());
            ps.setString(16, dobj.getSeatno());
            ps.setInt(17, dobj.getIP());
            ps.setInt(18, dobj.getEmergencycontact());
            ps.setString(19, dobj.getEmergencyrelation());
            int i = ps.executeUpdate();
            tmgr.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        } 
        finally
        {
        if(tmgr!=null)
        {
            try {
                tmgr.release();
            } catch (Exception ex) {
               ex.printStackTrace();
            }
        }
        
        }
    }
}
