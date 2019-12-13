/**
 * WDO2OListLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jeeplus.modules.app.api.dx;

import com.jeeplus.modules.app.api.product.PropertiesUtil;

public class WDO2OListLocator extends org.apache.axis.client.Service implements com.jeeplus.modules.app.api.dx.WDO2OList {

    public WDO2OListLocator() {
    }


    public WDO2OListLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WDO2OListLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WDO2OListPort
    private java.lang.String WDO2OListPort_address =  PropertiesUtil.getString("dx.address");

    public java.lang.String getWDO2OListPortAddress() {
        return WDO2OListPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WDO2OListPortWSDDServiceName = "WDO2OListPort";

    public java.lang.String getWDO2OListPortWSDDServiceName() {
        return WDO2OListPortWSDDServiceName;
    }

    public void setWDO2OListPortWSDDServiceName(java.lang.String name) {
        WDO2OListPortWSDDServiceName = name;
    }

    public com.jeeplus.modules.app.api.dx.WDO2OListPortType getWDO2OListPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WDO2OListPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWDO2OListPort(endpoint);
    }

    public com.jeeplus.modules.app.api.dx.WDO2OListPortType getWDO2OListPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
        	com.jeeplus.modules.app.api.dx.WDO2OListSoapBindingStub _stub = new com.jeeplus.modules.app.api.dx.WDO2OListSoapBindingStub(portAddress, this);
            _stub.setPortName(getWDO2OListPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWDO2OListPortEndpointAddress(java.lang.String address) {
        WDO2OListPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.jeeplus.modules.app.api.dx.WDO2OListPortType.class.isAssignableFrom(serviceEndpointInterface)) {
            	com.jeeplus.modules.app.api.dx.WDO2OListSoapBindingStub _stub = new com.jeeplus.modules.app.api.dx.WDO2OListSoapBindingStub(new java.net.URL(WDO2OListPort_address), this);
                _stub.setPortName(getWDO2OListPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("WDO2OListPort".equals(inputPortName)) {
            return getWDO2OListPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName(PropertiesUtil.getString("dx.namespace"), "WDO2OList");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName(PropertiesUtil.getString("dx.namespace"), "WDO2OListPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WDO2OListPort".equals(portName)) {
            setWDO2OListPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
