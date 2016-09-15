package com.katana.sdk.components;

import com.katana.api.Request;
import com.katana.api.Response;
import com.katana.sdk.common.Callable;

/**
 * Created by juan on 27/08/16.
 */
public class Middleware {

    private final String[] args;

    public Middleware(String[] args) {
        this.args = args;
    }

    public void runRequest(Callable<Request> callable){
        RequestMiddleware middleware = new RequestMiddleware(args);
        middleware.run(callable);
    }

    public void runResponse(Callable<Response> callable){
        ResponseMiddleware middleware = new ResponseMiddleware(args);
        middleware.run(callable);
    }
}
