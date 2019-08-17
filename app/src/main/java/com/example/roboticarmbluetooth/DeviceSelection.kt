package com.example.roboticarmbluetooth


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.widget.*
import kotlinx.android.synthetic.main.device_selection.*

class DeviceSelection : AppCompatActivity() {

    private val btAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private val requestBluetooth = 1
    lateinit var pairedDevices: Set<BluetoothDevice>

    companion object {
        val EXTRA_ADDRESS: String = "Device_address"
    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.device_selection)


        if (btAdapter == null){
            Toast.makeText(this, "Device does not support bluetooth", Toast.LENGTH_LONG).show()
            return
        }
        if (btAdapter.isEnabled == false){
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, requestBluetooth)
        }

        device_selection_refresh.setOnClickListener { devicesList() }


    }

    private fun devicesList(){
        pairedDevices = btAdapter?.bondedDevices as Set<BluetoothDevice>
        val list : ArrayList<String> = ArrayList()


        if (pairedDevices.isNotEmpty()) {
            for(device: BluetoothDevice in pairedDevices){
                val deviceName = device.name
                val deviceAddress = device.address
                list.add(deviceName + "\n" + deviceAddress)
            }
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        device_selection_list.adapter = adapter
        device_selection_list.onItemClickListener = AdapterView.OnItemClickListener{ _, _, position, _ ->
            val device: String = list[position]
            val address: String = device.substring(device.length - 17)

            val intent = Intent(this, Controls::class.java)
            intent.putExtra(EXTRA_ADDRESS, address)

            startActivity(intent)
        }



    }

}