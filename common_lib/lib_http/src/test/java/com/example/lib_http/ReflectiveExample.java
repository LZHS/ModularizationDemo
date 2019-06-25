package com.example.lib_http;

import org.junit.Test;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019-06-10 : 11:57<br/>
 */
public class ReflectiveExample {

    interface Hello {
        default String sayHello() {
            return "你好呀";
        }
    }

    interface TestHellp extends Hello {
        @Override
        default String sayHello() {
            return "TestHellp";
        }
    }

    @Test
    public void testMain() {
        try {
            TestJavaApiService target = (TestJavaApiService) Proxy.newProxyInstance(this.getClass().getClassLoader()
                    , new Class[]{TestJavaApiService.class}
                    , (proxy, method, args) -> null);

            Method method = TestApi.class.getMethod("getApiHost");

            Constructor<MethodHandles.Lookup> constructor=MethodHandles.Lookup.class
                    .getDeclaredConstructor(Class.class,int.class);
            constructor.setAccessible(true);

            Class<?> declaringClass=method.getDeclaringClass();

            int allModes=MethodHandles.Lookup.PUBLIC|MethodHandles.Lookup.PRIVATE|MethodHandles.Lookup.PROTECTED;

            Object result=constructor.newInstance(declaringClass,allModes)
                    .unreflectSpecial(method,declaringClass)
                    .bindTo(target)
                    .invokeWithArguments();

            System.out.println(result);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }
}
