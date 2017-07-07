/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.scrollupload.util;

import java.util.Date;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class ApplicationProperties {

    public static boolean allowConn = true;
    public static Date lastConnTime = null;
    public static int dbWaitTime = 15;
}
