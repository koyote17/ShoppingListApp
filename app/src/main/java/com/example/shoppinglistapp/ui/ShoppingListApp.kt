package com.example.shoppinglistapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglistapp.model.ShoppingItem
import com.example.shoppinglistapp.ui.components.ShoppingItemEditor
import com.example.shoppinglistapp.ui.components.ShoppingListItem

@Composable
fun ShoppingListApp(){
    var sItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var showDialog by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {showDialog = true},
            modifier = Modifier.align(Alignment.CenterHorizontally). padding(25.dp),
            shape = MaterialTheme.shapes.large) {
            Text("Add Item")
        }
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        ) {
            items(sItems){
                    item ->
                if (item.isEditing) {
                    ShoppingItemEditor(item = item,
                        onEditComplete = { editedName, editedQuantity ->
                            sItems = sItems.map { it.copy(isEditing = false) }
                            val editedItem = sItems.find { item.id == it.id }
                            editedItem?.let {
                                it.name = editedName
                                it.quantity = editedQuantity
                            }
                        })
                } else {
                    ShoppingListItem(item = item,
                        onEditClick = {
                            //finding out which edit button was clicked
                            sItems = sItems.map { it.copy(isEditing = it.id == item.id )}
                        },  onDeleteClick = {sItems = sItems - item})
                }
            }
        }
    }
    if (showDialog){
        AlertDialog(onDismissRequest = {showDialog = false},
            confirmButton = {
                Row(modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(onClick = {
                        if(itemName.isNotBlank()){
                            val newItem = ShoppingItem(
                                id = sItems.size+1,
                                name = itemName,
                                quantity = itemQuantity.toInt()
                            )
                            sItems = sItems + newItem
                            showDialog = false
                            itemName = ""
                        }
                    }, colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                        ) {
                        Text("ADD",
                            color = Color.Black)
                    }
                    Button(onClick = {showDialog = false},
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                        Text("CANCEL",
                            color = Color.Black)
                    }
                }
            },
            title = { Text(text = "Add Shopping Item",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )},
            containerColor = Color.LightGray,
            text = {
                Column {
                    OutlinedTextField(
                        value = itemName,
                        onValueChange = { itemName = it },
                        singleLine = true,
                        placeholder = { Text("Enter item") },
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    )

                    OutlinedTextField(
                        value = itemQuantity,
                        onValueChange = { itemQuantity = it },
                        singleLine = true,
                        placeholder = { Text("Enter quantity") },
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListAppPreview() {
    ShoppingListApp()
}