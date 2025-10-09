package au.fowenix.xen.gradle.internal

import org.gradle.api.plugins.ExtensionContainer
import org.gradle.kotlin.dsl.create
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass

internal fun <T : Any> ExtensionContainer.creating(type: KClass<T>) = PropertyDelegateProvider { _: Any?, property ->
    val value = create(property.name, type)

    ReadOnlyProperty { _: Any?, _ -> value }
}