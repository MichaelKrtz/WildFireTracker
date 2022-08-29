package com.example.wildfiretracker.services

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LifecycleService
import com.example.wildfiretracker.other.Constants.ACTION_PAUSE_SERVICE
import com.example.wildfiretracker.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.example.wildfiretracker.other.Constants.ACTION_STOP_SERVICE


class AlertsService : LifecycleService(){

    private val TAG = "AlertsService"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when(it.action) {
                  ACTION_START_OR_RESUME_SERVICE -> {
                      Log.v(TAG, "Started or resumed service")
                  }
                ACTION_PAUSE_SERVICE -> {
                    Log.v(TAG, "Paused service")
                }
                ACTION_STOP_SERVICE -> {
                    Log.v(TAG, "Stopped service")
                }
                else -> {
                    Log.v(TAG, "Something went Reaaaaaaaally wrong")
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

}