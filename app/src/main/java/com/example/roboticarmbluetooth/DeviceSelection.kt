package com.example.roboticarmbluetooth

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.device_selection.*

class DeviceSelection : AppCompatActivity() {

    val btAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    val requestBluetooth = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.device_selection)

        if (btAdapter == null){
            Toast.makeText(this, "Device does not support bluetooth", Toast.LENGTH_LONG).show()
            return
        }
        if (btAdapter.isEnabled){
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, requestBluetooth)
        }


    }

    private fun devicesList(){
        val pairedDevices: Set<BluetoothDevice>? = btAdapter?.bondedDevices
        pairedDevices?.forEach { device ->
            val deviceName = device.name
            val deviceAddress = device.address //MAC address
        }
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {


        super.onActivityReenter(resultCode, data)
    }



}