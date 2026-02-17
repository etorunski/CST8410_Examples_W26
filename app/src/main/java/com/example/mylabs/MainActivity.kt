package com.example.mylabs

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothGattServer
import android.bluetooth.BluetoothGattServerCallback
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mylabs.ui.theme.AppTheme

class MainActivity : ComponentActivity  (){ // means call constructor from parent
    val TAG = "MainActivity";

    //Step 1
    private var bluetoothManager : BluetoothManager? = null
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var gattServer : BluetoothGattServer? = null




    //this gets called first on loading
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //this implements the server functions:
        val gattCallbacks = object: BluetoothGattServerCallback() { } //We will implement the inherited functions one at a time and understand what each one does
        var gattServer : BluetoothGattServer? = null

        Log.w(TAG, "In onCreate() - Loading Widgets")

        bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager

//the permissions window asking for bluetooth:
        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission() ) {
                isGranted: Boolean ->
            if (isGranted) {  //The dialog showed and the user clicked "Ok"
                gattServer = bluetoothManager?.openGattServer(this, gattCallbacks )
            } else
            {
                // Explain to the user that the feature is unavailable because the
                // feature requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
            }
        }


        if(bluetoothManager!= null){ //you might not have bluetooth

            bluetoothAdapter = bluetoothManager?.getAdapter() //get connection to radio transmitter

            //this is for the server to advertise its existence:
            val bluetoothLeAdvertiser = bluetoothAdapter?.getBluetoothLeAdvertiser()


        }

        enableEdgeToEdge()

        setContent { // ( ) { }
            //your color theme:
            AppTheme( content = { //MyLabsTheme is a lambda function

                Scaffold( modifier = Modifier.fillMaxSize())
                    { innerPadding -> //body of the page
                        Greeting(  modifier = Modifier.padding(innerPadding) )
                    }
            })
        }

       if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) //31 or more
       {
           requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT)
       }
        else //30 or older
       {
           requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH)
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
fun Greeting( modifier: Modifier = Modifier) {
    Text(
        //color = Color(red=255, green = 255, blue=0),

        text = stringResource(R.string.hello_message)  ,
        fontSize= 32.sp,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun doesntMatter() {
    AppTheme {
        Greeting()
    }
}