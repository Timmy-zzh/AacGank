package com.timmy.thirdframework.net.core.chain;


import com.timmy.thirdframework.net.core.Response;

import java.io.IOException;

public interface Interceptor {

    Response interceptor(InterceptorChain chain) throws IOException;
}
