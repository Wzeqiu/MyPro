package com.wzq.common.utils

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * 通过反射,获得定义Class时声明的父类的范型参数的类型.
 */
fun Any.getSuperClassGenericType(index: Int): Class<*> {
    return getSuperClassGenericType(javaClass, index)
}

/**
 * 通过反射,获得定义Class时声明的父类的范型参数的类型.
 */
@Throws(IndexOutOfBoundsException::class)
fun getSuperClassGenericType(clazz: Class<*>, index: Int): Class<*>{
    val genType: Type = clazz.genericSuperclass as? ParameterizedType ?: return Any::class.java
    val params: Array<Type> = (genType as ParameterizedType).actualTypeArguments
    if (index >= params.size || index < 0) {
        return Any::class.java
    }
    return if (params[index] !is Class<*>) {
        Any::class.java
    } else params[index] as Class<*>
}


