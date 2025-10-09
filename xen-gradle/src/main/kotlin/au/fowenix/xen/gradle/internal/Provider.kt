package au.fowenix.xen.gradle.internal

import org.gradle.api.provider.Provider
import kotlin.reflect.KProperty

internal operator fun <T : Any> Provider<T>.getValue(thisRef: Any?, property: KProperty<*>) = get()