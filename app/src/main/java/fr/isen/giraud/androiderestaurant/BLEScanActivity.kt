package fr.isen.giraud.androiderestaurant

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.isen.giraud.androiderestaurant.databinding.ActivityBlescanBinding
import fr.isen.giraud.androiderestaurant.model.CartLine
import fr.isen.giraud.androiderestaurant.view.BLEAdapter
import fr.isen.giraud.androiderestaurant.view.CartAdapter


class BLEScanActivity : AppCompatActivity() {

    private val itemsList = ArrayList<ScanResult>()
    private lateinit var bleAdapter: BLEAdapter

    companion object{
        private const val ALL_PERMISSIONS_REQUEST_CODE = 1
    }

    private var scanning:Boolean = false
    private lateinit var binding : ActivityBlescanBinding
    private val bluetoothAdapter: BluetoothAdapter ? by lazy {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlescanBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //setup du recycler view
        val recyclerBLE: RecyclerView = binding.listBle
        bleAdapter = BLEAdapter(itemsList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerBLE.layoutManager = layoutManager
        recyclerBLE.adapter = bleAdapter


        when {
            bluetoothAdapter?.isEnabled == true ->
            binding.buttonState.setOnClickListener {
                startLeScanBLEWithPermission(!scanning)
            }
            bluetoothAdapter != null ->
                askBluetoothPermission()
            else -> {
                displayNoBleAvailable()
            }
        }
    }


    override fun onStop(){
        super.onStop()
        startLeScanBLEWithPermission(false)
    }

    private fun startLeScanBLEWithPermission(enable:Boolean) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startLeScanBLE(enable)
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), ALL_PERMISSIONS_REQUEST_CODE)
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLeScanBLE(enable: Boolean) {
        bluetoothAdapter?.bluetoothLeScanner?.apply {
            if(enable) {
                scanning = true
                startScan(scanCallBack)
            }else{
                scanning = false
                stopScan(scanCallBack)
            }
            btnPlayclick()
        }
    }

    private val scanCallBack = object : ScanCallback(){
        override fun onScanResult(callBackType : Int, result:ScanResult){
            itemsList.add(result)
            bleAdapter.notifyDataSetChanged()
        }
    }

    private fun displayNoBleAvailable() {
        binding.buttonState.isVisible = false
        binding.textBLE.text = "Pas de device"
        binding.progressBar.isIndeterminate = true
    }

    private fun btnPlayclick() {
        if (!scanning) {
            binding.buttonState.setImageResource(R.drawable.playlogo)
            binding.textBLE.text = "Lancer le scan BLE"
            binding.progressBar.isIndeterminate = false
        } else {
            binding.buttonState.setImageResource(R.drawable.stoplogo)
            binding.textBLE.text = "Scan BLE en cours..."
            binding.progressBar.isIndeterminate = true
        }
    }

    private fun askBluetoothPermission() {
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            startActivityForResult(enableBtIntent, ALL_PERMISSIONS_REQUEST_CODE)
        }
    }

}