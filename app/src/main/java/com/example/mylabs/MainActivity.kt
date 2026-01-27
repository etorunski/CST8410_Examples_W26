package com.example.mylabs

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


        var sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager


        enableEdgeToEdge()

        setContent {
            //inside here is a Composable

            //local variable, remember means save the previous value
            var lightReading = remember {mutableStateOf(0.0f) }//initially 0

            //your color theme:
            AppTheme( content = { //MyLabsTheme is a lambda function
                Scaffold( modifier = Modifier.fillMaxSize().background(Color.Yellow),
                    topBar ={ Text("The app")},
                    bottomBar = {  },
                    contentWindowInsets = WindowInsets.safeDrawing
                )
                    { innerPadding -> //body of the page
                        DisplayLighting( lightingValue = lightReading.value, modifier = Modifier.padding(innerPadding) )
                    }
            })
            val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) //get the light sensor

            if(lightSensor != null){
                //the sensor is on the phone:
                val sensorListener  = object : SensorEventListener {
                    //the two functions required by the Interface

                    //the sensor has a new hardware reading
                    override fun onSensorChanged(reading: SensorEvent) {
                        val readings = reading.values //is an array, either 1-d or 3-d

                        //cause a recomposition by changing the value:
                        lightReading.value = readings[0]//the new value of light intensity
                    }
                    //the sensor's accuracy is different
                    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                    }
                }
                sensorManager.registerListener(sensorListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
            }
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
fun DisplayLighting(lightingValue: Float, modifier: Modifier = Modifier) {

    Column(
        modifier=modifier.background(Color.Yellow),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.lighting_value) + lightingValue.toString(),
            fontSize = 30.sp,
            modifier = modifier
        )
        Icon(Icons.Filled.Email, contentDescription = "Favorite")
        Button( onClick = {  }){
            //content can be Text, or image
            Image(painter = painterResource(R.drawable.beach),
                modifier = Modifier.width(200.dp).height(200.dp),
            contentDescription = "A beach")
        }
        //for screen readers of visually impaired
        Image(painter = painterResource(R.drawable.app_icon),
            modifier = Modifier.fillMaxWidth(0.25f).fillMaxHeight(0.25f),
            contentDescription = "an app icon")


    }
}

@Preview(showBackground = true)
@Composable
fun doesntMatter() {
    AppTheme {
        DisplayLighting(123.5f)
    }
}