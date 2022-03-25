package com.example.movieapplication.domain.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.movieapplication.R
import com.example.movieapplication.domain.events.NewLocationEvent
import com.example.movieapplication.domain.helper.LocationHelper
import com.example.movieapplication.domain.helper.MyLocationListener
import com.example.movieapplication.domain.models.LocationModel
import com.example.movieapplication.utils.DateFormatUtil.getCurrentDateTime
import com.example.movieapplication.utils.DateFormatUtil.getDateToString
import com.example.movieapplication.utils.NotificationUtil
import com.google.firebase.firestore.FirebaseFirestore
import org.greenrobot.eventbus.EventBus

class LocationService : Service() {
    private val NOTIFICATION_CHANNEL_ID = "my_notification_location"
    private val TAG = "LocationService"
    override fun onCreate() {
        super.onCreate()
        isServiceStarted = true
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setOngoing(false)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_ID, NotificationManager.IMPORTANCE_LOW
            )
            notificationChannel.description = NOTIFICATION_CHANNEL_ID
            notificationChannel.setSound(null, null)
            notificationManager.createNotificationChannel(notificationChannel)
            startForeground(1, builder.build())
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val db = FirebaseFirestore.getInstance()
        LocationHelper().startListeningUserLocation(
            this, object : MyLocationListener {
                override fun onLocationChanged(location: Location?) {
                    mLocation = location
                    mLocation?.let {
                        Log.d(
                            TAG,
                            "onLocationChanged: Latitude ${it.latitude} , Longitude ${it.longitude}"
                        )
                        Log.d(TAG, "run: Running = Location Update Successful")
                        val locationModel = LocationModel(
                            it.latitude.toString(),
                            it.longitude.toString(),
                            getDateToString(getCurrentDateTime())
                        )
                        NotificationUtil.notify(
                            getString(R.string.location_point_title),
                            getString(
                                R.string.location_point_body,
                                locationModel.latitude,
                                locationModel.longitude
                            ),
                            applicationContext
                        )
                        db.collection("locations")
                            .add(locationModel)
                            .addOnSuccessListener { documentReference ->
                                Log.d(
                                    TAG,
                                    "DocumentSnapshot added with ID: ${documentReference.id}"
                                )
                                EventBus.getDefault().post(NewLocationEvent(locationModel))
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                            }
                    }
                }
            })
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        isServiceStarted = false

    }

    companion object {
        var mLocation: Location? = null
        var isServiceStarted = false
    }
}