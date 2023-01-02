package com.shang.taipeitour.utility

import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.shang.taipeitour.R

object NavigationAnimation {

    fun getSlideIn(): NavOptions {
        return navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
    }
}