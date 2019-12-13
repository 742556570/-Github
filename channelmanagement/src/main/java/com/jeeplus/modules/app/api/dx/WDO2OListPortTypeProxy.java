package com.jeeplus.modules.app.api.dx;

public class WDO2OListPortTypeProxy implements com.jeeplus.modules.app.api.dx.WDO2OListPortType {
  private String _endpoint = null;
  private com.jeeplus.modules.app.api.dx.WDO2OListPortType wDO2OListPortType = null;
  
  public WDO2OListPortTypeProxy() {
    _initWDO2OListPortTypeProxy();
  }
  
  public WDO2OListPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initWDO2OListPortTypeProxy();
  }
  
  private void _initWDO2OListPortTypeProxy() {
    try {
      wDO2OListPortType = (new com.jeeplus.modules.app.api.dx.WDO2OListLocator()).getWDO2OListPort();
      if (wDO2OListPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)wDO2OListPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)wDO2OListPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wDO2OListPortType != null)
      ((javax.xml.rpc.Stub)wDO2OListPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.jeeplus.modules.app.api.dx.WDO2OListPortType getWDO2OListPortType() {
    if (wDO2OListPortType == null)
      _initWDO2OListPortTypeProxy();
    return wDO2OListPortType;
  }
  
  public com.jeeplus.modules.app.api.dx.ResponseInfo getWDO2OList(com.jeeplus.modules.app.api.dx.CustomerInfo arg0) throws java.rmi.RemoteException{
    if (wDO2OListPortType == null)
      _initWDO2OListPortTypeProxy();
    return wDO2OListPortType.getWDO2OList(arg0);
  }
  
  
}