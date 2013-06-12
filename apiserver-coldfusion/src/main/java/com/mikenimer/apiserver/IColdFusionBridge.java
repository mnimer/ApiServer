package com.mikenimer.apiserver;


import javax.servlet.http.HttpServletRequest;

/**
 *
 * User: mikenimer
 * Date: 6/09/13
 */
public interface IColdFusionBridge
{

    public Object invoke(String cfcPath, String method, Object[] myArgs, HttpServletRequest request) throws Throwable;
}
