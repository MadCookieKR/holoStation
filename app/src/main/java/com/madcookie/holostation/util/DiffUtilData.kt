package com.madcookie.holostation.util

interface DiffUtilData<T> {
     fun areItemsTheSame(newItem: T): Boolean
     fun areContentsTheSame(newItem: T): Boolean
}