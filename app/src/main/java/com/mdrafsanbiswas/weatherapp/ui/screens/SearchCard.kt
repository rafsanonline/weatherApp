package com.mdrafsanbiswas.weatherapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mdrafsanbiswas.weatherapp.ui.theme.Snow
import com.mdrafsanbiswas.weatherapp.ui.theme.Violet
import com.mdrafsanbiswas.weatherapp.ui.theme.poppins

@Composable
fun SearchCard(onSearchClick: () -> Unit) {

    var searchText by remember {
        mutableStateOf("")
    }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)) {
        Box(
            modifier = Modifier
                .height(55.dp)
                .weight(1f)
                .background(color = Snow.copy(.6f), shape = RoundedCornerShape(12.dp))
        ) {
            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                modifier = Modifier
                    .align(Alignment.Center),
                placeholder = {
                    Text(
                        text = "Search by city, country",
                        fontFamily = poppins,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Violet
                )
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Icon(
            Icons.Rounded.Search,
            contentDescription = "",
            tint = Violet,
            modifier = Modifier
                .size(50.dp)
                .padding(10.dp)
                .align(Alignment.CenterVertically)
                .clickable {
                    onSearchClick()
                }
        )
    }

}