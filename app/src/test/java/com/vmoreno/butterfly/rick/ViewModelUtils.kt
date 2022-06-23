package com.vmoreno.butterfly.rick

import org.mockito.internal.util.reflection.FieldSetter
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

/**
 * This method helps us to call private functions using reflection
 * @param name is the name of the private function
 * @param args are the arguments of the private function
 * @sample 'sut.callPrivateFun("privateFunctionName", param1, param2, ...param2)'
 */
inline fun <reified T> T.callPrivateFun(name: String, vararg args: Any?): Any? =
    T::class.declaredMemberFunctions
        .firstOrNull { it.name == name }
        ?.apply { isAccessible = true }
        ?.call(this, *args)

fun <V, T : Any> setFieldHelper(
    fieldName: String,
    value: V,
    target: T
) {
    FieldSetter.setField(
        target,
        target.javaClass.getDeclaredField(fieldName),
        value
    )
}

@Suppress("UNCHECKED_CAST")
fun <T> getField(target: Any, fieldName: String): T {
    val field = target::class.java.getDeclaredField(fieldName)
    field.isAccessible = true
    return field.get(target) as T
}

inline fun <reified T : Any, R> T.getPrivateProperty(name: String): R? =
    T::class
        .memberProperties
        .firstOrNull { it.name == name }
        ?.apply { isAccessible = true }
        ?.get(this) as? R