package com.groupb1.phonefreedom.permissionCheck

import android.Manifest
import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.groupb1.phonefreedom.R
import com.groupb1.phonefreedom.databinding.FragmentPermissionBinding
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import kotlinx.coroutines.withTimeout

class PermissionFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    companion object {
        const val PERMISSION_POLICY_REQUEST_CODE = 1
    }

    private var _binding: FragmentPermissionBinding? = null
    private val binding get() = _binding!!
    private lateinit var button: Button
    private lateinit var textView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var mNotificationManager: NotificationManager
    private var mContext: Context? = null
    private var mActivity: Activity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPermissionBinding.inflate(inflater, container, false)
        button = binding.button
        textView = binding.textView
        progressBar = binding.progressBar2
        mActivity = this.requireActivity()
        mContext = activity



        button.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            textView.text = "Checking"
            Thread.sleep(500)
            setViewVisibility()
            Thread.sleep(1000)
            requestLocationPermission()
            if (hasLocationPermission()) {
                Navigation.findNavController(it).navigate(
                    R.id.action_permissionFragment_to_firstFragment
                )
            }
        }

        return binding.root
    }

    private fun hasLocationPermission() =
        EasyPermissions.hasPermissions(
            requireContext(),
            Manifest.permission.ACCESS_NOTIFICATION_POLICY

        )

    private fun requestLocationPermission() {
        EasyPermissions.requestPermissions(
            this,
            "This application cannot work without Location Permission.",
            PERMISSION_POLICY_REQUEST_CODE,
            Manifest.permission.ACCESS_NOTIFICATION_POLICY
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(requireActivity()).build().show()
        } else {
            requestLocationPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Toast.makeText(
            requireContext(),
            "Permission Granted!",
            Toast.LENGTH_SHORT
        ).show()
        setViewVisibility()
    }

    private fun setViewVisibility() {
        if (hasLocationPermission()) {
            textView.visibility = View.VISIBLE
            textView.text = "Permission Granted"
            button.visibility = View.GONE

        } else {
            textView.visibility = View.GONE
            textView.text = "Permission Denied"
            button.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}