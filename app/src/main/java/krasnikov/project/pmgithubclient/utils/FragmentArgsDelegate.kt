package krasnikov.project.pmgithubclient.utils

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import java.io.Serializable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class FragmentArgsDelegate<T : Any>(
    private val key: String,
) : ReadWriteProperty<Fragment, T> {

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) =
        with(thisRef.arguments ?: Bundle().also(thisRef::setArguments)) {
            when (value) {
                is Boolean -> putBoolean(key, value)
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Short -> putShort(key, value)
                is Long -> putLong(key, value)
                is Byte -> putByte(key, value)
                is ByteArray -> putByteArray(key, value)
                is Char -> putChar(key, value)
                is CharArray -> putCharArray(key, value)
                is CharSequence -> putCharSequence(key, value)
                is Float -> putFloat(key, value)
                is Bundle -> putBundle(key, value)
                is Parcelable -> putParcelable(key, value)
                is Serializable -> putSerializable(key, value)
                else -> throw IllegalStateException("Type of property $key is not supported")
            }
        }

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return thisRef.arguments?.get(key) as? T
            ?: throw IllegalStateException("Property ${property.name} could not be read")
    }
}