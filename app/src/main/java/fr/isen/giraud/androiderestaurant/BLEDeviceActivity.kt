package fr.isen.giraud.androiderestaurant

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import fr.isen.giraud.androiderestaurant.databinding.ActivityBledeviceBinding

class BLEDeviceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBledeviceBinding

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBledeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val device = intent.getParcelableExtra<BluetoothDevice?>("Device")
        Toast.makeText(this, device?.address, Toast.LENGTH_SHORT).show()
        binding.deviceName.text = device?.name ?: "Unknown Name"
        binding.deviceStatut.setImageResource(R.drawable.statut_disconnected)


    }
}