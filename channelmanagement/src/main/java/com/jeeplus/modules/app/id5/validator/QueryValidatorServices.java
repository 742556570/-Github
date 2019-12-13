package com.jeeplus.modules.app.id5.validator;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface QueryValidatorServices extends Remote {
    public String querySingle(String userName_, String password_, String type_, String param_) throws RemoteException;
    public String queryBatch(String userName_, String password_, String type_, String param_) throws RemoteException;
}
