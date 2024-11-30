package mobilepaymentapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class ResetUsersReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("com.group4.mobilepaymentapplication.RESET_USERS".equals(intent.getAction())) {
            Intent resetIntent = new Intent(context, MainActivity.class);
            resetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            resetIntent.putExtra("reset_action", true);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resetIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            createNotification(context, pendingIntent);
        }
    }

    private void createNotification(Context context, PendingIntent pendingIntent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "reset_channel_id";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Reset Channel";
            String description = "Channel for User Reset Notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(com.google.android.material.R.drawable.mtrl_checkbox_button)
                .setContentTitle("Reset Users")
                .setContentText("Tap to reset user data.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(1, notification);
    }
}

