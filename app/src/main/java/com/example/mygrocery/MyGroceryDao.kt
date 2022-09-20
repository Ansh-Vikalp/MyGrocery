package com.example.mygrocery

import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MyGroceryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: GroceryItems)

    @Delete
    suspend fun delete(item: GroceryItems)

    @Query("SELECT * FROM grocery_items")
    fun getAllGroceryItems(): LiveData<List<GroceryItems>>

}