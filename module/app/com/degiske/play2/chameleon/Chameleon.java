package com.degiske.play2.chameleon;

import play.Logger;
import play.mvc.Content;
import play.Play;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Chameleon {

    private static ConcurrentHashMap<String,Method> methodConcurrentHashMap = new ConcurrentHashMap<String,Method>();

    private static final String self = Chameleon.class.getCanonicalName();

    public static Content render(String className, Object[] params)
    {
        String key = getKey(className, params);

        if(Play.isDev() || !methodConcurrentHashMap.containsKey(key))
        {
            Logger.debug("{} - Trying to get render method for : {} with params: {}", self, className, params);
            try
            {
                Class clas = Play.application().classloader().loadClass(className);
                Method renderMethod = null;
                boolean allOk = true;

                for(Method method : clas.getDeclaredMethods())
                {
                    if(method.getName().equals("render"))
                    {
                        Type[] types = method.getParameterTypes();
                        if(types.length == params.length)
                        {
                            for(int i = 0 ; i < types.length; i++)
                            {
                                if(null != params[i])
                                {
                                    allOk &= params[i].getClass() == types[i];
                                }
                            }

                            if(allOk)
                            {
                                renderMethod = method;
                                if(null != renderMethod)
                                {
                                    methodConcurrentHashMap.put(key, renderMethod);
                                    break;
                                }
                                else
                                {
                                    throw new ThemeRenderMethodNotFoundException("render method can not be found for theme : " + className);
                                }
                            }

                        }
                    }
                }

                if(methodConcurrentHashMap.containsKey(key))
                {
                    return (Content)renderMethod.invoke(null, params);
                }
                else
                {
                    throw new ThemeRenderMethodNotFoundException("render method can not be found for theme : " + className);
                }

            }
            catch (ClassNotFoundException e1)
            {
                throw new ThemeRenderException(e1.getMessage(), e1);
            }
            catch (InvocationTargetException e1)
            {
                throw new ThemeRenderException(e1.getMessage(), e1);
            }
            catch (IllegalAccessException e1)
            {
                throw new ThemeRenderException(e1.getMessage(), e1);
            }
        }
        else
        {
            Method renderMethod = methodConcurrentHashMap.get(key);
            Logger.debug("{} - Invoking method : {} from method cache", self, renderMethod.toGenericString());

            try
            {
                return (Content)renderMethod.invoke(null, params);
            }
            catch (IllegalAccessException e)
            {
                throw new ThemeRenderException(e.getMessage(), e);
            }
            catch (InvocationTargetException e)
            {
                throw new ThemeRenderException(e.getMessage(), e);
            }
        }
    }

    private static String getKey(String className,Object[] params)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(className);
        stringBuilder.append("_");
        for(Object param : params)
        {
            if(null != param)
            {
                stringBuilder.append(param.getClass().toString());
                stringBuilder.append("_");
            }
        }
        return stringBuilder.toString();
    }
}