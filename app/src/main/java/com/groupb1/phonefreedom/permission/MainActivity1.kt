package com.groupb1.phonefreedom.permission

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.groupb1.phonefreedom.R

class MainActivity1 : AppCompatActivity() {
    private val DND_PERMISSION_CODE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        checkPermission(android.Manifest.permission.ACCESS_NOTIFICATION_POLICY, DND_PERMISSION_CODE)

    }

    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this@MainActivity1, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this@MainActivity1, arrayOf(permission), requestCode)

        } else {
            Toast.makeText(this@MainActivity1, "Permission Granted Already", Toast.LENGTH_LONG).show()

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == DND_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity1, "ACCESS_NOTIFICATION_POLICY Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity1, "ACCESS_NOTIFICATION_POLICY Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

}

