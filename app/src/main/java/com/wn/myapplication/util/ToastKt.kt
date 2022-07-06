package com.wn.myapplication.util

import android.widget.Toast
import com.wn.myapplication.App


fun toast(str: String){
    Toast.makeText(App.INSTANCE,str,Toast.LENGTH_SHORT).show()
}