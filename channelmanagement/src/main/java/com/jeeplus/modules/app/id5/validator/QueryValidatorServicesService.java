package com.jeeplus.modules.app.id5.validator;

import java.net.URL;

import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public interface QueryValidatorServicesService extends Service {
    public java.lang.String getQueryValidatorServicesAddress();

    public QueryValidatorServices getQueryValidatorServices() throws ServiceException;

    public QueryValidatorServices getQueryValidatorServices(URL portAddress) throws ServiceException;
}
