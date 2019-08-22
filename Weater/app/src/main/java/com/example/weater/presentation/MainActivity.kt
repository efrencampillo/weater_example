package com.example.weater.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weater.CURRENT_LAT
import com.example.weater.CURRENT_LON
import com.example.weater.CustomApp
import com.example.weater.R
import com.example.weater.domain.BaseResponse
import com.example.weater.viewmodel.ErrorEvent
import com.example.weater.viewmodel.EventClass
import com.example.weater.viewmodel.SuccessEvent
import com.example.weater.viewmodel.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: ViewModel

    lateinit var adapter: RecyclerAdapter

    lateinit var fusedLocationClient: FusedLocationProviderClient

    private val observer: Observer<EventClass> = Observer { event -> handleEvent(event) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CustomApp.injector?.inject(this)
        viewModel.emitter.observe(this, observer)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setUIRecycler()
    }

    override fun onResume() {
        super.onResume()
        checkPermissions()
    }

    private fun setUIRecycler() {
        val linearLayoutManager = LinearLayoutManager(this)
        recycler.layoutManager = linearLayoutManager
        adapter = RecyclerAdapter()
        recycler.adapter = adapter

    }


    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {

            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    PERMISSION_LOCATION_REQUEST
                )
            }
        } else {
            startRequestLocation()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_LOCATION_REQUEST -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    startRequestLocation()
                } else {
                    Toast.makeText(this, "Permissions Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }

            else -> {
                // Ignore all other requests.
            }
        }
    }


    fun startRequestLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    viewModel.retrieveInfo(it.latitude, it.longitude)
                }
                if (location == null) {
                    viewModel.retrieveInfo(CURRENT_LAT, CURRENT_LON)
                }
            }

        }
    }

    private fun handleEvent(event: EventClass) {
        when (event) {
            is SuccessEvent -> {
                displayInfo(event.response)
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            }
            is ErrorEvent -> {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayInfo(response: BaseResponse) {
        adapter.items = response.hourly.data?: listOf()
        adapter.notifyDataSetChanged()
    }


    companion object {
        const val PERMISSION_LOCATION_REQUEST = 15000
    }
}
