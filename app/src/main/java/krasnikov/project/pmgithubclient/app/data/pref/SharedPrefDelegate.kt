package krasnikov.project.pmgithubclient.app.data.pref

import android.content.SharedPreferences
import java.lang.IllegalArgumentException
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SharedPrefDelegate<T>(
    private val prefs: SharedPreferences,
    private val key: String,
    private val default: T,
) : ReadWriteProperty<Any?, T> {

    @Suppress("UNCHECKED_CAST")
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = with(prefs.edit()) {
        when (value) {
            is Boolean -> putBoolean(key, value)
            is Int -> putInt(key, value)
            is Float -> putFloat(key, value)
            is Long -> putLong(key, value)
            is String -> putString(key, value)
            else -> throw IllegalArgumentException()
        }.apply()
    }

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Any?, property: KProperty<*>): T = with(prefs) {
        return when (default) {
            is Boolean -> getBoolean(key, default)
            is Int -> getInt(key, default)
            is Float -> getFloat(key, default)
            is Long -> getLong(key, default)
            is String -> getString(key, default)
            else -> throw IllegalArgumentException()
        } as T
    }
}
