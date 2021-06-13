package com.example.testtasknew

import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.testtasknew.MyAdapter.Companion.i

const val INTENT_EXTRA_NOTIFICATION = "noti"

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var mAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val extras = intent.extras
        if (extras != null) {
            val bIsLaunchedFromNotification = extras.getBoolean(INTENT_EXTRA_NOTIFICATION)
            if (bIsLaunchedFromNotification) {
                viewPager.setCurrentItem(extras.getInt(INTENT_EXTRA_NOTIFICATION), true)
            }
        } else {
            mAdapter = MyAdapter(this)
            viewPager = findViewById(R.id.pager)
            viewPager.adapter = mAdapter
            mAdapter.fragment = BlankFragment()
            mAdapter.mFragmentList.add(mAdapter.fragment)
        }
    }

    fun plusButton(v: View?) {
        i++
        mAdapter.addFragment(BlankFragment(), i)
    }

    fun minus(v: View?) {
        with(NotificationManagerCompat.from(this))
        { cancel(mAdapter.getItemId(viewPager.currentItem + 1).toInt()) }
        mAdapter.removeFragment()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun notificationButton(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(
            INTENT_EXTRA_NOTIFICATION,
            (mAdapter.getItemId(viewPager.currentItem + 1)).toInt()
        )
        intent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            // flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(
            application.baseContext,
            NotificationApp.CHANNEL_1_ID
        )
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle("Notification ")
            .setContentText("Notification ${mAdapter.getItemId(viewPager.currentItem + 1)}")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(this)) {
            notify(mAdapter.getItemId(viewPager.currentItem + 1).toInt(), builder.build())
        }
    }
}