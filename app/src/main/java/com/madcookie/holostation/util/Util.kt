package com.madcookie.holostation.util

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


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
