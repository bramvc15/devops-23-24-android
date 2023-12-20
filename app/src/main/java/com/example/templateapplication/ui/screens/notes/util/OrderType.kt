package com.example.templateapplication.ui.screens.notes.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}