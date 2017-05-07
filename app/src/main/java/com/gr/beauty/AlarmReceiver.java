package com.gr.beauty;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.gr.beauty.View.SalonBookings;


/**
 * Created by gaurav on 14/3/17.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context,SalonBookings.class);
        notificationIntent.putExtra("MobileNo",intent.getLongExtra("MobileNo",0));
        Log.d("msg",""+intent.getLongExtra("MobileNo",0));
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notification = (NotificationCompat.Builder) new NotificationCompat.Builder(context).setSmallIcon(R.drawable.smallicon)
                .setTicker("New Booking")
                .setContentTitle(intent.getStringExtra("CustomerName"))
                .setContentText(intent.getStringExtra("ServiceName"))
                .setContentIntent(pendingIntent)
                .setSound(alarmSound)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000,1000,1000,1000,1000})
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.largeicon));

        notificationManager.notify(0,notification.build());
    }
}

