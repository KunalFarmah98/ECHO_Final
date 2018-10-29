package com.apps.kunalfarmah.echo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.apps.kunalfarmah.echo.activities.MainActivity;
import com.apps.kunalfarmah.echo.fragments.MainScreenFragment;
import com.apps.kunalfarmah.echo.fragments.SongPlayingFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * http://www.tutorialsface.com/2015/08/android-custom-notification-tutorial/
 * https://stackoverflow.com/questions/22789588/how-to-update-notification-with-remoteviews
 */

public class mNotification extends Service {


    ArrayList<String> thoughts;

    MainActivity main;
    static MediaPlayer mMediaPlayer;


    String title = "";
    String artist = "";
    SongPlayingFragment msong;
    RemoteViews views;
    RemoteViews smallviews;
    ImageView imageView;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }


    @Override
    public void onStart(Intent intent, int startId) {
        //Toast.makeText(this, "Start", //Toast.LENGTH_SHORT).show();
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this, "Destroy", //Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate() {
        msong = new SongPlayingFragment();
        main = new MainActivity();
        thoughts = new ArrayList<String>();

        thoughts.add("ECHO TIME!");
        thoughts.add("FUN TIME!");
        thoughts.add("LOST DREAMS!");
//        thoughts.add("MOOD IMPROVED!");
        thoughts.add("DANCE BABY!");
        thoughts.add("GO ECHO");
        thoughts.add("NIRVANA!");
        thoughts.add("LIVE MUSIC!");
        thoughts.add("CAN'T SLEEP?");
        thoughts.add("PAIN GONE");
        thoughts.add("LOST WORLD");
        thoughts.add("BE HAPPY");
        thoughts.add("TUNES ON");
        thoughts.add("FULL ON");
        thoughts.add("SING ALONG");
        thoughts.add("PLAY ALONG");


        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {


            if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {


//            if(main.getnotify_val() ==false) {

                title = intent.getStringExtra("title");
                artist = intent.getStringExtra("artist");
                main.setNotify_val(true);

                showNotification();
                //Toast.makeText(this, "Service Started", //Toast.LENGTH_SHORT).show();
//            }

            } else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {
                //Toast.makeText(this, "Clicked Previous", //Toast.LENGTH_SHORT).show();
                msong.previous();
//            title=intent.getStringExtra("title");
//            artist=intent.getStringExtra("artist");
                views.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);
                smallviews.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);
                updateNotiUI();


//            Log.i(LOG_TAG, "Clicked Previous");
            } else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {

                //Toast.makeText(this, "Clicked Play", //Toast.LENGTH_SHORT).show();
                msong.setPlay(msong.playorpause());

                if (msong.getPlay() == false) {

                    views.setImageViewResource(R.id.playpausebutton_not, R.drawable.play_icon);
                    smallviews.setImageViewResource(R.id.playpausebutton_not, R.drawable.play_icon);
                } else {

                    views.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);
                    smallviews.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);
                }


                updateNotiUI();

            } else if (intent.getAction().equals(Constants.ACTION.CHANGE_TO_PAUSE)) {
                views.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);
                smallviews.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);
                updateNotiUI();
            } else if (intent.getAction().equals(Constants.ACTION.CHANGE_TO_PLAY)) {
                views.setImageViewResource(R.id.playpausebutton_not, R.drawable.play_icon);
                smallviews.setImageViewResource(R.id.playpausebutton_not, R.drawable.play_icon);

                updateNotiUI();

            } else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
                //Toast.makeText(this, "Clicked Next", //Toast.LENGTH_SHORT).show();
                msong.next();
//            title=intent.getStringExtra("title");
//            artist=intent.getStringExtra("artist");
//            views.setTextViewText(R.id.song_title_nav, title);
//            views.setTextViewText(R.id.song_artist_nav, artist);
                views.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);
                smallviews.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);
                updateNotiUI();


//            Log.i(LOG_TAG, "Clicked Next");
            } else if (intent.getAction().equals(Constants.ACTION.NEXT_UPDATE)) {
                //Toast.makeText(this, "Clicked Next", //Toast.LENGTH_SHORT).show();
//            msong.playNextsong("PlayNextNormal");
                title = intent.getStringExtra("title");
                artist = intent.getStringExtra("artist");

                if (title.equals("<unknown>"))
                    title = "Unknown";

                if (artist.equals("<unknown>"))
                    artist = "unknown";

//            try {
                views.setTextViewText(R.id.song_title_nav, title);
                views.setTextViewText(R.id.song_artist_nav, artist);
                views.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);
                smallviews.setTextViewText(R.id.song_title_nav, title);
                smallviews.setTextViewText(R.id.song_artist_nav, artist);
                smallviews.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);
                updateNotiUI();
            }
//            catch (Exception e) {
//                msong.getMediaPlayer().stop();
//                msong.unregister();
//                main.finishAffinity();
//            }

//        }

            else if (intent.getAction().equals(Constants.ACTION.PREV_UPDATE)) {
                //Toast.makeText(this, "Clicked Next", //Toast.LENGTH_SHORT).show();
//            msong.playNextsong("PlayNextNormal");
                title = intent.getStringExtra("title");
                artist = intent.getStringExtra("artist");
                if (title.equals("<unknown>"))
                    title = "Unknown";

                if (artist.equals("<unknown>"))
                    artist = "unknown";
                views.setTextViewText(R.id.song_title_nav, title);
                views.setTextViewText(R.id.song_artist_nav, artist);
                views.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);

                smallviews.setTextViewText(R.id.song_title_nav, title);
                smallviews.setTextViewText(R.id.song_artist_nav, artist);
                smallviews.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);
                updateNotiUI();


//            Log.i(LOG_TAG, "Clicked Next");
            } else if (intent.getAction().equals(
                    Constants.ACTION.STOPFOREGROUND_ACTION)) {
//            Log.i(LOG_TAG, "Received Stop Foreground Intent");
                //Toast.makeText(this, "Service Stoped", //Toast.LENGTH_SHORT).show();

                LocalBroadcastManager localBroadcastManager = LocalBroadcastManager
                        .getInstance(this);
                localBroadcastManager.sendBroadcast(new Intent(
                        "com.durga.action.close"));
//            main.finishAffinity();

                msong.unregister();

                mMediaPlayer = msong.getMediaPlayer();
                mMediaPlayer.stop();

                main.setNotify_val(false);
                stopForeground(true);
                stopSelf();

            }

        }

    catch(Exception e) {
        main.finishAffinity();
    }
    finally {
        return START_STICKY;
    }

}

    Notification status;
//    private final String LOG_TAG = "Notification";

    private void showNotification() {
// Using RemoteViews to bind custom layouts into Notification
        views = new RemoteViews(getPackageName(),
                R.layout.notification_bar);

        smallviews = new RemoteViews(getPackageName(),
                R.layout.notificaiton_smalll);



        Intent openIntent = new Intent(this, mNotification.class);
        PendingIntent pOpenIntent = PendingIntent.getActivity(this, 0, openIntent, 0);



        Intent notificationIntent = new Intent(this,MainActivity.class);
        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Intent previousIntent = new Intent(this, mNotification.class);
        previousIntent.setAction(Constants.ACTION.PREV_ACTION);
        PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                previousIntent, 0);

        Intent playIntent = new Intent(this, mNotification.class);
        playIntent.setAction(Constants.ACTION.PLAY_ACTION);
        PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                playIntent, 0);

        Intent nextIntent = new Intent(this, mNotification.class);
        nextIntent.setAction(Constants.ACTION.NEXT_ACTION);
        PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                nextIntent, 0);

        Intent closeIntent = new Intent(this, mNotification.class);
        closeIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        PendingIntent pcloseIntent = PendingIntent.getService(this, 0,
                closeIntent, 0);

        views.setOnClickPendingIntent(R.id.playpausebutton_not, pplayIntent);
        smallviews.setOnClickPendingIntent(R.id.playpausebutton_not, pplayIntent);


        views.setOnClickPendingIntent(R.id.nextbutton_not, pnextIntent);
        smallviews.setOnClickPendingIntent(R.id.nextbutton_not, pnextIntent);


        views.setOnClickPendingIntent(R.id.previousbutton_not, ppreviousIntent);
        smallviews.setOnClickPendingIntent(R.id.previousbutton_not, ppreviousIntent);


        views.setOnClickPendingIntent(R.id.close, pcloseIntent);
        smallviews.setOnClickPendingIntent(R.id.close,pcloseIntent);


        // setting thoughts of the day
        Random randomObject =new  Random() ;                                                            // initialising a random object of the random class
        int randomPosition = randomObject.nextInt(thoughts.size()+1);                // setting range of random to size+1
        int currentPosition = randomPosition;

        if (currentPosition == thoughts.size()) {    // if the currentposition exceeds the size, start over
            currentPosition = 0;
        }

        views.setTextViewText(R.id.logo,thoughts.get(currentPosition).toString());



        if(title.equals("<unknown>"))
            title="Unknown";

        if(artist.equals("<unknown>"))
            artist="unknown";

        views.setTextViewText(R.id.song_title_nav, title);
        smallviews.setTextViewText(R.id.song_title_nav, title);


        views.setTextViewText(R.id.song_artist_nav, artist);
        smallviews.setTextViewText(R.id.song_artist_nav, artist);



        views.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);
        smallviews.setImageViewResource(R.id.playpausebutton_not, R.drawable.pause_icon);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // Sets an ID for the notification, so it can be updated.
            int notifyID = 1;
            String CHANNEL_ID = "my_channel_011";// The id of the channel.
            CharSequence name = "Notify";
            int importance = NotificationManager.IMPORTANCE_MAX;


            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name,  NotificationManager.IMPORTANCE_HIGH);

           // NotificationChannel mChannel=  mNotificationManager.getNotificationChannel("my_channel_07");

//            int importance = mChannel.getImportance();
//            if (importance < NotificationManager.IMPORTANCE_HIGH && importance > 0 ) {
//                mChannel.setImportance(NotificationManager.IMPORTANCE_MAX);
//            }

            // Create a notification and set the notification channel.
            mChannel.setSound(null, null);
            mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            mChannel.enableVibration(false);

            mNotificationManager.createNotificationChannel(mChannel);

            status = new Notification.Builder(this, CHANNEL_ID).setOnlyAlertOnce(true)
                    .setVisibility(Notification.VISIBILITY_PUBLIC).setContentIntent(pOpenIntent).build();
            status.contentView=smallviews;
            status.bigContentView = views;
            status.priority=Notification.PRIORITY_MAX;
            status.when =0;


            status.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
            status.icon = R.drawable.now_playing_bar_eq_image;
            status.contentIntent = pendingIntent;


            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, status);

        }

        else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.O ){

            status = new Notification.Builder(this).setWhen(0).setContentIntent(pOpenIntent).build();
            status.contentView=smallviews;
            status.bigContentView = views;
            status.visibility=Notification.VISIBILITY_PUBLIC;
            status.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
            status.icon = R.drawable.now_playing_bar_eq_image;
            status.contentIntent = pendingIntent;
            status.priority = Notification.PRIORITY_MAX;

            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, status);

        }


        else {
            status = new Notification.Builder(this).setWhen(0).setContentIntent(pOpenIntent).build();


            status.contentView=smallviews;
            status.bigContentView = views;

            status.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
            status.icon = R.drawable.now_playing_bar_eq_image;
            status.contentIntent = pendingIntent;
            status.priority = Notification.PRIORITY_MAX;

            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, status);
        }

        // binding sensor in notification

        /*Here we call the function*/
//        msong.bindShakeListener();


    }



    public void updateNotiUI() {
        this.startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, this.status);
    }


}