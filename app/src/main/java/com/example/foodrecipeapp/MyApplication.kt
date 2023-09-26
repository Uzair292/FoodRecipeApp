package com.example.foodrecipeapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp         //this annotation will behind the scene create necessary dagger component and dagger code for us
class MyApplication : Application(){
}