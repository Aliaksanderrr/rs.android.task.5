package com.minchinovich.rsandroidtask5.utils

import androidx.fragment.app.Fragment
import com.minchinovich.rsandroidtask5.App
import com.minchinovich.rsandroidtask5.MainActivity
import com.minchinovich.rsandroidtask5.SimpleNavigator

fun Fragment.requireMainActivity() = requireActivity() as MainActivity

fun Fragment.requireApp() = requireActivity().application as App

val Fragment.navigator: SimpleNavigator
    get() = requireMainActivity()