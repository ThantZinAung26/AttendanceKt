package com.soft.attendancekt.util

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

open class PermissionUtil {


    companion object {
        val PERMISSION_CAMERA: Int = 100
        fun hasCameraPermission(fragment: Fragment): Boolean {
            if (ContextCompat.checkSelfPermission(
                    fragment.requireContext(),
                    Manifest.permission.CAMERA
                ) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                fragment.requestPermissions(arrayOf(Manifest.permission.CAMERA), PERMISSION_CAMERA)
                return false
            }
            return true
        }
    }
}