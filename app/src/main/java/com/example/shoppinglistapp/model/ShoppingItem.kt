package com.example.shoppinglistapp.model

data class ShoppingItem(val id: Int,
                        var name: String,
                        var quantity: Int,
                        var isEditing: Boolean = false)
