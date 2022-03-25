package com.example.movieapplication.presentation.ui.main.map

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.movieapplication.R
import com.example.movieapplication.databinding.MapFragmentBinding
import com.example.movieapplication.domain.Resource
import com.example.movieapplication.domain.events.NewLocationEvent
import com.example.movieapplication.domain.models.LocationModel
import com.example.movieapplication.domain.services.LocationService
import com.example.movieapplication.presentation.ui.dialogs.loader.LoaderFragment
import com.example.movieapplication.utils.AlertDialogUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@AndroidEntryPoint
class MapFragment : Fragment(R.layout.map_fragment), OnMapReadyCallback {

    private var _binding: MapFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var mMap: GoogleMap
    private lateinit var viewModel: MapViewModel
    private var loaderFragment: LoaderFragment? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = MapFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(MapViewModel::class.java)


        binding.buttonStartService.setOnClickListener {
            ContextCompat.startForegroundService(requireActivity(), Intent(requireContext(), LocationService::class.java))
        }

        binding.buttonStopService.setOnClickListener {
            activity?.stopService(Intent(requireContext(), LocationService::class.java))
        }

        if (!checkPermission()) {
            requestPermission()
        }
        createMapFragment()

        viewModel.getLocations()
        observeResponse()
    }

    private fun createMapFragment() {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        val result1 = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMarkerClickListener {
            AlertDialogUtil.confirmationDialog(
                context = requireContext(),
                title = R.string.notice,
                message = it.title!!,
            )
            true
        }
    }

    private fun observeResponse() = lifecycleScope.launchWhenStarted {

        viewModel.locations.observe(viewLifecycleOwner) {
            val response = it ?: return@observe
            when (response) {
                is Resource.Success -> {
                    addMarkers(response.value)
                }
                is Resource.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        response.errorBody.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> {
                    if (loaderFragment == null) {
                        loaderFragment = LoaderFragment.newInstance(getString(R.string.loading))
                        loaderFragment?.show(parentFragmentManager, "LOADER_SIGNUP")
                    }
                }
            }

            if (!response.isLoading && loaderFragment != null) {
                loaderFragment?.dismiss()
                loaderFragment = null
            }
        }
    }

    private fun addMarkers(locations:List<LocationModel>){
        for (location in locations){
            addMarker(location)
        }
    }

    private fun addMarker(location: LocationModel){
        val point = LatLng(location.latitude!!.toDouble(), location.longitude!!.toDouble())
        mMap.addMarker(
            MarkerOptions()
                .position(point)
                .title(getString(R.string.point_format, location.date, location.latitude, location.longitude)))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(point))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15F), 2000, null);

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public fun onNewLocationEvent(locationEvent: NewLocationEvent){
        addMarker(locationEvent.location)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}