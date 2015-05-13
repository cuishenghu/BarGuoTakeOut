package zykj.com.barguotakeout.http;

import java.util.List;

/**
 * Created by ss on 15-4-17.
 */
public class HttpHandlerFactory {

    private static HttpHandlerFactory factory;

    public static HttpHandlerFactory getInstance(){
        if(factory == null){
            synchronized (HttpHandlerFactory.class){
                if(factory == null){
                    factory=new HttpHandlerFactory();
                }
            }
        }
        return factory;
    }





}
