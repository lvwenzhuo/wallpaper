package com.yuren.tech.main

import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment

data class DockItem(var target: Class<out Fragment?>, var title: String,@DrawableRes var iconResId:Int)