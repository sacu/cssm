package org.jiira.chapter11.game.interceptor;

import org.jiira.chapter11.game.Interceptor;

public class RoleInterceptor implements Interceptor {
	@Override
    public void before(Object obj) {
        System.out.println("1.开始打印");
    }

    @Override
    public void after(Object obj) {
        System.out.println("2.打印结束");
    }

    @Override
    public void afterReturning(Object obj) {
         System.out.println("info:完成打印");
    }

    @Override
    public void afterThrowing(Object obj) {
        System.out.println("info:打印异常");
    }
}
