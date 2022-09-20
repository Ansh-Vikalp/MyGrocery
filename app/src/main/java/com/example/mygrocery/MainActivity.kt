package com.example.mygrocery

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), GroceryRVAdapter.GroceryItemClickInterface {

    lateinit var itemsRV:RecyclerView
    lateinit var addFAB:FloatingActionButton
    lateinit var list: List<GroceryItems>
    lateinit var groceryRVAdapter: GroceryRVAdapter
    lateinit var groceryViewModel: GroceryViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        itemsRV=findViewById(R.id.idRVItems)
        addFAB=findViewById(R.id.FABAdd)
        list = ArrayList<GroceryItems>()
        groceryRVAdapter = GroceryRVAdapter(list, this)
        itemsRV.layoutManager= LinearLayoutManager(this)
        itemsRV.adapter=groceryRVAdapter
        val groceryRepository= GroceryRepository(GroceryDatabase(this))
        val factory= GroceryViewModelFactory(groceryRepository)
        groceryViewModel= ViewModelProvider(this,factory).get(GroceryViewModel::class.java)
        groceryViewModel.getAllGroceryItems().observe(this, Observer{
            groceryRVAdapter.list= it
            groceryRVAdapter.notifyDataSetChanged()
        })

        addFAB.setOnClickListener{
            addFAB.show()
            openDialog()
        }

    }


    @SuppressLint("NotifyDataSetChanged")
    fun openDialog(){
        val dialog= Dialog(this)
        dialog.setContentView(R.layout.grocery_add_dialog)
        val cancelBtn = dialog.findViewById<Button>(R.id.idBtnCancel)
        val addBtn = dialog.findViewById<Button>(R.id.idBtnAdd)
        val itmEdt = dialog.findViewById<EditText>(R.id.idEdItemName)
        val itmPriceEdt = dialog.findViewById<EditText>(R.id.idEdItemPrice)
        val itmQuantityEdt = dialog.findViewById<EditText>(R.id.idEdItemQuantity)

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        addBtn.setOnClickListener {
            val itemName: String= itmEdt.text.toString()
            val itemPrice: String= itmPriceEdt.text.toString()
            val itemQuantity: String= itmQuantityEdt.text.toString()

            val qty: Int= itemQuantity.toInt()
            val pr: Int= itemPrice.toInt()

            if(itemName.isNotEmpty() && itemPrice.isNotEmpty() && itemQuantity.isNotEmpty()) {
                val items = GroceryItems(itemName, qty, pr)
                groceryViewModel.insert(items)
                Toast.makeText(applicationContext, "Item Inserted...", Toast.LENGTH_SHORT).show()
                groceryRVAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }


            else{
                    Toast.makeText(applicationContext,"Please Enter all data..", Toast.LENGTH_SHORT).show()

            }


        }

        dialog.show()

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onItemClick(groceryItems: GroceryItems) {

        groceryViewModel.delete(groceryItems)
        groceryRVAdapter.notifyDataSetChanged()
        Toast.makeText(applicationContext,"Item Deleted...", Toast.LENGTH_SHORT).show()

    }
}