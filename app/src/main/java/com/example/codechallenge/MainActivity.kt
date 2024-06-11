package com.example.codechallenge

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.codechallenge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCheckConnection.setOnClickListener {
            val isConnected = checkConnectionAvailability(this)
            if (isConnected) {
                binding.txtNetworkWifiStatus.text = "Connected"
            } else {
                binding.txtNetworkWifiStatus.text = "Disconnected"
            }
        }

        binding.btnCheckCurrentNetworkConnection.setOnClickListener {
            checkCurrentNetworkConnection(this)
        }
    }

    private fun checkConnectionAvailability(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    @SuppressLint("SetTextI18n")
    private fun checkCurrentNetworkConnection(context: Context){
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return

        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            binding.txtCurrentNetworkConnection.text = "Connected via Wi-Fi"
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            binding.txtCurrentNetworkConnection.text = "Connected via mobile network"
        } else {
            binding.txtCurrentNetworkConnection.text = "Connected via other type of network"
        }
    }
}
