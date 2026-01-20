package com.example.mylabs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mylabs.ui.theme.MyLabsTheme

class MainActivity : ComponentActivity  (){ // means call constructor from parent


    //this gets called first on loading
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent { // ( ) { }
            MyLabsTheme( content = { //MyLabsTheme is a lambda function
                Scaffold( modifier = Modifier.fillMaxSize())
                    { innerPadding -> //body of the page
                        Greeting( name = printName(), modifier = Modifier.padding(innerPadding) )
                    }
            })
        }

/*
        //all from this lab:
        takeOtherString ( ::function1 )

                    //f is the last parameter, when ( ) are empty, remove them

        takeOtherString { str : String-> var result =   str.length}    // no ( )
        takeOtherString( ) { str : String-> var result =   str.length} //( ) before { }
        takeOtherString( { str : String-> var result =   str.length} ) // ( { } )
*/
    }




    fun function1 (s : String )
    {
        var result = s.contains("Hello")
    }

    fun takeOtherString( f : (String) -> Unit ) //f is at the end of the list
    {
        var aString = "This is a string"
        f(aString) //false, no "Hello"
    }

    fun printStrings ( str1 : String = "Hello", str2: String = "World") //Unit means void
    {
        var result = "Str1:${str1.toString()} Str2:${str2.length}"

    }

}


//this is our function:
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        color = Color(red=255, green = 255, blue=0),

        text = "Hello $name!", //=concatenation : "Hello " + name
        fontSize= 32.sp,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun doesntMatter() {
    MyLabsTheme {
        Greeting("Sahar")
    }
}