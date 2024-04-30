package com.froggengo.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

/**
 * @author fly
 * @create 2024-04-29-22:58
 **/
public class R0002_ReflectType_01 {
    public static void main(String[] args) throws NoSuchMethodException {
        List<Set<List<String>>> list;
        Method method = R0002_ReflectType_01.class.getDeclaredMethod("list", List.class);
        Parameter parameter = method.getParameters()[0];
        Type type = parameter.getParameterizedType();
        if (type instanceof ParameterizedType) {
            Type ownerType = ((ParameterizedType) type).getOwnerType();
            Type[] actualTypeArguments_list = ((ParameterizedType) type).getActualTypeArguments();
            Type actualTypeArgument_list = actualTypeArguments_list[0];
            if (actualTypeArgument_list instanceof ParameterizedType) {
                Type[] actualTypeArguments_list_set = ((ParameterizedType) actualTypeArgument_list).getActualTypeArguments();
                Type actualTypeArgument_list_set = actualTypeArguments_list_set[0];
                if (actualTypeArgument_list_set instanceof ParameterizedType) {
                    Type[] actualTypeArguments_list_set_list = ((ParameterizedType) actualTypeArgument_list_set).getActualTypeArguments();
                    Type actualTypeArguments_list_set_list_str = actualTypeArguments_list_set_list[0];
                    if (actualTypeArguments_list_set_list_str instanceof ParameterizedType) {
                        System.out.println("last is also ParameterizedType: "+actualTypeArguments_list_set_list_str);
                    }else {
                        System.out.println("last is not ParameterizedType: "+actualTypeArguments_list_set_list_str);
                    }
                }
            }
        }
        //System.out.println(method);
    }

    public void list(List<Set<List<String>>> list) {
        System.out.println(list.size());
        return;
    }
}
