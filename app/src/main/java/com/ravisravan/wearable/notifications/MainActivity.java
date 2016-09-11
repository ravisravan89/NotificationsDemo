package com.ravisravan.wearable.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the ic_action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle ic_action bar item clicks here. The ic_action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void triggerButtonPress(View view) {
        switch (view.getId()) {
            case R.id.normal:
                generateSimpleNotification();
                break;
            case R.id.action:
                generateNotificationWithAction();
                break;
            case R.id.waction:
                generateWearableActionNotification();
                break;
            case R.id.bigtext:
                generateBigTextNotification();
                break;
            case R.id.wearableextend:
                generateWearableExtendedNotification();
                break;
            case R.id.voiceinput:
                generateVoiceInputNotification();
                break;
            case R.id.pages:
                generateNotificationWithPages();
                break;
            case R.id.stacking:
                generateNotificationsAsstack();
                break;
            case R.id.localonly:
                generateOnlyLocalNotification();
                break;
        }
    }

    //This generates a simple notification
    private void generateSimpleNotification() {
        int notificationId = 001;
        Intent messageIntent = new Intent(MainActivity.this, MessageActivity.class);
        messageIntent.putExtra("MESSAGE", "You just clicked a simple notification");
        PendingIntent messagePendingIntent = PendingIntent.getActivity(this, 0, messageIntent, 0);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_normal)
                .setContentTitle("Simple Notification")
                .setContentText("Click on notification to open next screen to read message and smile")
                .setContentIntent(messagePendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notificationId, notificationBuilder.build());
    }

    //This generates a notification with an action visible in both phone and wearable.
    private void generateNotificationWithAction() {
        int notificationId = 001;
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        Uri geoUri = Uri.parse("geo:0,0?q=" + Uri.encode("Hyderabad"));
        mapIntent.setData(geoUri);
        PendingIntent mapPendingIntent =
                PendingIntent.getActivity(this, 1, mapIntent, 0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_action)
                        .setContentTitle("Notification With common Action")
                        .setContentText("Performing action will open map on phone.")
                        .setContentIntent(mapPendingIntent)
                        .setAutoCancel(true)
                        .addAction(R.drawable.ic_map,
                                "View on Map", mapPendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notificationId, notificationBuilder.build());
    }
//PendingIntent.FLAG_UPDATE_CURRENT
    //This generates notification with only action visible on wearable.
    private void generateWearableActionNotification() {
        int notificationId = 001;
// Create an intent for the reply action
        Intent actionIntent = new Intent(this, MessageActivity.class);
        actionIntent.putExtra("MESSAGE", "You just clicked wearable action notification");
        PendingIntent actionPendingIntent =
                PendingIntent.getActivity(this, 2, actionIntent,
                        0);

// Create the action
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_waction,
                        "Click to see message screen on phone and smile", actionPendingIntent)
                        .build();

// Build the notification and add the action via WearableExtender
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(MainActivity.this)
                        .setSmallIcon(R.drawable.ic_waction)
                        .setContentTitle("Wearable action Notification")
                        .setContentText("Its action can be seen only on wearable.")
                        .setAutoCancel(true)
                        .extend(new NotificationCompat.WearableExtender().addAction(action));

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notificationId, notificationBuilder.build());
    }

    //This generates notification in bigtext style
    private void generateBigTextNotification() {
        int notificationId = 001;
        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
        bigStyle.setBigContentTitle("This is the big text Title");
        bigStyle.bigText(getString(R.string.big_text));

        Intent bigtextIntent = new Intent(this, MessageActivity.class);
        bigtextIntent.putExtra("MESSAGE", "You just clicked on big text intent. The content you showed is : " + getString(R.string.big_text));
        PendingIntent bigtextPendingIntent =
                PendingIntent.getActivity(this, 3, bigtextIntent,
                        0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(MainActivity.this)
                        .setSmallIcon(R.drawable.ic_bigtext)
                        .setContentTitle("Notification Title")
                        .setContentText("Notification content like subject")
                        .setAutoCancel(true)
                        .setContentIntent(bigtextPendingIntent)
                        .addAction(R.drawable.ic_bigtext,
                                "Click to open next screen!", bigtextPendingIntent)
                        .setStyle(bigStyle);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notificationId, notificationBuilder.build());
    }

    //This shows how to set background to notification on wearable.
    private void generateWearableExtendedNotification() {
        int notificationId = 001;
        // Create a WearableExtender to add functionality for wearables
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.imageforbitmap);

        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender()
                        .setHintHideIcon(true) //Removes app icon from notification card.
                        .setBackground(mBitmap);

        Intent backgroundIntent = new Intent(this, MessageActivity.class);
        backgroundIntent.putExtra("MESSAGE", "You just clicked on notification with background visible on wearable");
        PendingIntent backgroundPendingIntent =
                PendingIntent.getActivity(this, 4, backgroundIntent,
                        0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(MainActivity.this)
                        .setSmallIcon(R.drawable.ic_wearableextend)
                        .setContentTitle("Notification With Background Image")
                        .setContentText("Hey there!! you can see the background image. That's it, it's simple!")
                        .setAutoCancel(true)
                        .extend(wearableExtender)
                        .setContentIntent(backgroundPendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notificationId, notificationBuilder.build());
    }

    private void generateVoiceInputNotification() {
        int notificationId = 001;
        // Key for the string that's delivered in the action's intent
        String[] replyChoices = getResources().getStringArray(R.array.reply_choices);
        RemoteInput remoteInput = new RemoteInput.Builder("MESSAGE")
                .setLabel("Reply")
                .setChoices(replyChoices)
                .build();
        Intent replyIntent = new Intent(this, MessageActivity.class);
        PendingIntent replyPendingIntent =
                PendingIntent.getActivity(this, 5, replyIntent,
                        0);
        // Create the reply action and add the remote input
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.reply,
                        "Reply", replyPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();
        // Build the notification and add the action via WearableExtender
        Notification notification =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_voice)
                        .setContentTitle("Voice Input")
                        .setContentText("You can reply with voice input, or pick from options available.")
                        .extend(new NotificationCompat.WearableExtender().addAction(action))
                        .setAutoCancel(true)
                        .build();

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, notification);
    }

    private void generateNotificationWithPages() {
        // Create builder for the main notification
        int notificationId = 001;

        Intent pagesIntent = new Intent(this, MessageActivity.class);
        pagesIntent.putExtra("MESSAGE", "You just clicked on notification with Pages");
        PendingIntent pagesPendingIntent =
                PendingIntent.getActivity(this, 6, pagesIntent,
                        0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_page)
                        .setContentTitle("Page 1")
                        .setContentText("Short message")
                        .setContentIntent(pagesPendingIntent)
                        .setAutoCancel(true)
                        .addAction(new NotificationCompat.Action(R.drawable.ic_map,
                                "Action to Page1", pagesPendingIntent));

// Create a big text style for the second page
        NotificationCompat.BigTextStyle secondPageStyle = new NotificationCompat.BigTextStyle();
        secondPageStyle.setBigContentTitle("Page 2")
                .bigText("A lot of text...");

// Create second page notification
        Notification secondPageNotification =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Page 2")
                        .setStyle(secondPageStyle)
                        .setAutoCancel(true)
                        .addAction(new NotificationCompat.Action(R.drawable.ic_map,
                                "Action to Page2", pagesPendingIntent))
                        .build();

// Extend the notification builder with the second page
        Notification notification = notificationBuilder
                .extend(new NotificationCompat.WearableExtender()
                        .addPage(secondPageNotification))
                .build();

// Issue the notification
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, notification);
    }

    private void generateNotificationsAsstack(){
        int notificationId = 001;
        String GROUP_KEY_EMAILS = "group_key_emails";
        // Build the notification, setting the group appropriately
        Notification notif = new NotificationCompat.Builder(this)
                .setContentTitle("New mail from sender1")
                .setContentText("sender1 sent u wishes")
                .setSmallIcon(R.drawable.mail)
                .setGroup(GROUP_KEY_EMAILS)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, notif);
//
        Notification notif2 = new NotificationCompat.Builder(this)
                .setContentTitle("New mail from sender2")
                .setContentText("sender2 sent u wishes")
                .setSmallIcon(R.drawable.mail)
                .setGroup(GROUP_KEY_EMAILS)
                .build();

        notificationManager.notify(++notificationId, notif2);

        Bitmap largeicon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_map);
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.imageforbitmap);
        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender()
                        .setBackground(mBitmap);
        // Create an InboxStyle notification
        Notification summaryNotification = new NotificationCompat.Builder(this)
                .setContentTitle("2 new messages")
                .setSmallIcon(R.drawable.mail)
                .setLargeIcon(largeicon)
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("Alex Faaborg   Check this out")
                        .addLine("Jeff Chang   Launch Party")
                        .setBigContentTitle("2 new messages")
                        .setSummaryText("johndoe@gmail.com"))
                .extend(wearableExtender)
                .setGroup(GROUP_KEY_EMAILS)
                .setGroupSummary(true)
                .build();

        notificationManager.notify(++notificationId, summaryNotification);
    }

    private void generateOnlyLocalNotification(){
        int notificationId = 001;
        Intent messageIntent = new Intent(MainActivity.this, MessageActivity.class);
        messageIntent.putExtra("MESSAGE", "You just clicked a simple notification, which is only local");
        PendingIntent messagePendingIntent = PendingIntent.getActivity(this, 7, messageIntent, 0);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_normal)
                .setContentTitle("Simple Notification")
                .setContentText("This notification doesn't appear on wearable")
                .setContentIntent(messagePendingIntent)
                .setLocalOnly(true)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notificationId, notificationBuilder.build());
    }
}
