package nic.scrollupload.exception;
//////////////////////////////////////////////////////////////////////////////
// CLASS            : VahanException
// PURPOSE          : Top parent expception class for Vahan
// NOTES            : None
// LAST MODIFIED    :
//  20030919 GUM019 Changed package structure
//  20030519 RCN007 InstanceCount added
//  20030306 RCN001 Created
//////////////////////////////////////////////////////////////////////////////
// Copyright 2003 National Informatics Centre, NIC. http://www.nic.in
// All Rights Reserved.
//////////////////////////////////////////////////////////////////////////////
//
// Importing standard java packages/classes
//
// NONE 
//
// Importing VahanCommon java packages/classes
//
import nic.java.util.InstanceCount;


/**
 * Top parent expception class for Vahan Server
 *
 * @author RCN
 */
public class VahanException extends Exception {
    /**
     * Constructor
     */
    public VahanException() {
        super();
        InstanceCount.add(this);
    }

    /**
     * Constructor
     */
    public VahanException(String msg) {
        super(msg);
        InstanceCount.add(this);        
    }
    
    /** 
     * Override finalize() 
     */
    @Override
    public void finalize() throws Throwable {
        InstanceCount.remove(this);
        super.finalize();
    }    
}