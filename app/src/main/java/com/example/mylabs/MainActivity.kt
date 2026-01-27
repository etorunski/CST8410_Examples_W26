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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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



                Scaffold( modifier = Modifier.fillMaxSize())
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
    Text(
        //color = Color(red=255, green = 255, blue=0),

        text = stringResource(R.string.lighting_value) + lightingValue.toString()
        ,
        fontSize= 32.sp,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun doesntMatter() {
    AppTheme {
        DisplayLighting(1.5f)
    }
}