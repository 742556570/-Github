 package com.jeeplus.modules.app.id5.validator;
 
 import java.rmi.RemoteException;
 import javax.xml.rpc.ServiceException;
 import javax.xml.rpc.Stub;
 
 public class QueryValidatorServicesProxy
   implements QueryValidatorServices
 {
   private String _endpoint = null;
   private QueryValidatorServices queryValidatorServices = null;
 
   public QueryValidatorServicesProxy() {
     _initQueryValidatorServicesProxy();
   }
 
   public QueryValidatorServicesProxy(String endpoint) {
     this._endpoint = endpoint;
     _initQueryValidatorServicesProxy();
   }
 
   private void _initQueryValidatorServicesProxy() {
     try {
       this.queryValidatorServices = new QueryValidatorServicesServiceLocator().getQueryValidatorServices();
       if (this.queryValidatorServices != null)
         if (this._endpoint != null)
           ((Stub)this.queryValidatorServices)._setProperty("javax.xml.rpc.service.endpoint.address", this._endpoint);
         else
           this._endpoint = ((String)((Stub)this.queryValidatorServices)._getProperty("javax.xml.rpc.service.endpoint.address"));
     }
     catch (ServiceException localServiceException)
     {
     }
   }
 
   public String getEndpoint() {
     return this._endpoint;
   }
 
   public void setEndpoint(String endpoint) {
     this._endpoint = endpoint;
     if (this.queryValidatorServices != null)
       ((Stub)this.queryValidatorServices)._setProperty("javax.xml.rpc.service.endpoint.address", this._endpoint);
   }
 
   public QueryValidatorServices getQueryValidatorServices()
   {
     if (this.queryValidatorServices == null)
       _initQueryValidatorServicesProxy();
     return this.queryValidatorServices;
   }
 
   public String querySingle(String userName_, String password_, String type_, String param_) throws RemoteException {
     if (this.queryValidatorServices == null)
       _initQueryValidatorServicesProxy();
     return this.queryValidatorServices.querySingle(userName_, password_, type_, param_);
   }
 
   public String queryBatch(String userName_, String password_, String type_, String param_) throws RemoteException {
     if (this.queryValidatorServices == null)
       _initQueryValidatorServicesProxy();
     return this.queryValidatorServices.queryBatch(userName_, password_, type_, param_);
   }
 }
