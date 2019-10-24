package com.example.roomshoppinglist

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ShoppingListItem::class], version = 1, exportSchema = false)
abstract class ShoppingListRoomDatabase : RoomDatabase() {
    abstract fun shoppingListDao(): ShoppingListDao
}