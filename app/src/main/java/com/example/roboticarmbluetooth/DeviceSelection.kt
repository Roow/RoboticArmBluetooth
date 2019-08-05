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
import android.widget.Toast
import kotlinx.android.synthetic.main.device_selection.*

class DeviceSelection : AppCompatActivity() {

    var btAdapter: BluetoothAdapter? = null
    lateinit var pairedDevice: Set<BluetoothDevice>
    val requestBluetooth = 1

    companion object{
        val extraAddress: String = "Device_address"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.device_selection)

        btAdapter = BluetoothAdapter.getDefaultAdapter()
        if (btAdapter == null){
            Toast.makeText(this, "Does not support bluetooth", Toast.LENGTH_LONG).show()
            return
        }
        if (!btAdapter!!.isEnabled){
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, requestBluetooth)
        }

        device_selection_refresh.setOnClickListener { deviceList() }


    }

    private fun deviceList(){
        pairedDevice = btAdapter!!.bondedDevices
        val list : ArrayList<BluetoothDevice> = ArrayList()

        if (pairedDevice.isNullOrEmpty()){
            for (device: BluetoothDevice in pairedDevice){
                list.add(device)
                Log.i("devices", ""+device)
            }
        } else {
            Toast.makeText(this, "No device found", Toast.LENGTH_LONG).show()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)




    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == requestBluetooth) {
            if (resultCode == Activity.RESULT_OK){
                if (btAdapter!!.isEnabled) {
                    Toast.makeText(this, "Enabled", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Not enabled", Toast.LENGTH_LONG).show()
                }
            } else if (resultCode==Activity.RESULT_CANCELED){
                Toast.makeText(this, "bluetooth enabling is cancelled", Toast.LENGTH_LONG).show()
            }
        }
    }
}