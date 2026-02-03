package com.example.mylabs

import android.R.attr.label
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylabs.ui.theme.AppTheme

class MainActivity : ComponentActivity  (){ // means call constructor from parent
    val TAG = "MainActivity";

    //this gets called first on loading
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w(TAG, "In onCreate() - Loading Widgets")

        enableEdgeToEdge()
        setContent {
            //inside here is a Composable
            //your color theme:
            AppTheme( content = { //MyLabsTheme is a lambda function
                Scaffold( modifier = Modifier.fillMaxSize().background(Color.Yellow),
                    topBar ={ Text("The app")},
                    bottomBar = {  },
                    contentWindowInsets = WindowInsets.safeDrawing
                )
                    { innerPadding -> //body of the page
                        DisplayText( modifier = Modifier.padding(innerPadding) )
                    }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        Log.w(TAG, "In onStart() - Now visible")
    }

    override fun onResume() {
        super.onResume()
        Log.w(TAG, "In onResume() - accepting input")
    }

    override fun onPause() {
        super.onPause()
        Log.w(TAG, "In onPause() - stopped listening for input")
    }

    override fun onStop() {
        super.onStop()
        Log.w(TAG, "In onStop() - Not visible")
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}


//this is our function:
@Composable
fun DisplayText(modifier: Modifier = Modifier) {
                    //store the value for next call
    var currentValue = remember { mutableStateOf("Hello world") }

    Column(
        modifier=modifier.fillMaxHeight(),//.background(Color.Yellow),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text =  "Our currentValue is ${currentValue.value}" ,
            fontSize = 30.sp,
            modifier = modifier
        )
        TextField( value = currentValue.value,
            onValueChange = { newText ->//user types something in:
                currentValue.value = newText}, //changing, do redraw
                label = { Text("Type below") },
                placeholder = { Text("Type here") }
            )
    }
} //variable disappears

@Preview(showBackground = true)
@Composable
fun doesntMatter() {
    AppTheme {
        DisplayText()
    }
}