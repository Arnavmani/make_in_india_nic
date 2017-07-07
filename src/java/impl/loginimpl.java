/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impl;

import Dobjdetails.Logindobj;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;  
import javax.faces.bean.RequestScoped;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import nic.scrollupload.connection.TransactionManager;


/**
 *
 * @author babu9
 */
public class loginimpl {
    
     public boolean giveResult(String email, String password) {

        boolean result = false;
         PreparedStatement ps= null;
         TransactionManager tmgr = null;
         
         try
        {
            tmgr=new TransactionManager("hello");
            String sql = "SELECT * FROM registration WHERE email='"+email+"' and password='"+password+"'";
            ps=tmgr.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
             result=true;   
             }
        }catch(SQLException e)
        {
        e.printStackTrace();
        }
        
        finally{
        if(tmgr!=null)
        {
            try {
                tmgr.release();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }  
        
    }
         return result;
    }
        
}


