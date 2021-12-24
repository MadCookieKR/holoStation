package com.madcookie.holostation.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import kotlin.jvm.Throws


fun <T> createDefaultDiffUtil(): DiffUtil.ItemCallback<T> {
    return object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return false
        }
    }
}

inline fun <reified T : DiffUtilData<T>> createDiffUtil(): DiffUtil.ItemCallback<T> {
    return object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.areItemsTheSame(newItem)
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.areContentsTheSame(newItem)
        }
    }
}


inline fun <reified R> Any?.whatIfNotNullAs(
    whatIf: (R) -> Unit
): Any? {
    return whatIfNotNullAs(
        whatIf = whatIf,
        whatIfNot = { }
    )
}

inline fun <reified R> Any?.whatIfNotNullAs(
    whatIf: (R) -> Unit,
    whatIfNot: () -> Unit
): Any? {

    if (this != null && this is R) {
        whatIf(this as R)
        return this
    }
    whatIfNot()
    return this
}


fun Boolean?.toSafe() : Boolean{
    return this ?: false
}

@Throws
 fun Context.writeObject(fileName: String, toWrite: Any) {
    openFileOutput(fileName, AppCompatActivity.MODE_PRIVATE).use { out ->
        ObjectOutputStream(out).use {
            it.writeObject(toWrite)
        }
    }
}

@Throws
 fun <T> Context.readObject(fileName: String): T {
    return openFileInput(fileName).use { input ->
        ObjectInputStream(input).readObject() as T
    }
}
