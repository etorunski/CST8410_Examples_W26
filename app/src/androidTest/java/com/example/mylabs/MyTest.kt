package com.example.mylabs

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mylabs.ui.theme.AppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MyTest {


    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun testLaunchApp(){
        composeTestRule.setContent{
            //test composeable
            AppTheme {
                DisplayLighting(123.5f) //initialized the app
            }
        }
        //lookup of item on screen
        composeTestRule.onNodeWithText("The lighting is: 123.5").assertExists()
    }
}