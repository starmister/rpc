package com.swy.protocol;

import com.swy.framework.RpcRequest;
import com.swy.framework.URL;


public interface Procotol {

    public void start(URL url);
    public Object send(URL url, RpcRequest invocation);
}
