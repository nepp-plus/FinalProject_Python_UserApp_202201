package com.neppplus.finalproject_python_userapp_202201.utils

import java.text.NumberFormat

class WonFormatUtil {
    
    companion object {
        fun getWonFormat(money: Int) : String {
            return "${NumberFormat.getNumberInstance().format(money)}원"
        }
    }
}