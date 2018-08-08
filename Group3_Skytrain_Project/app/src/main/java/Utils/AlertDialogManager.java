package Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;

import com.yamibo.bbs.group3_skytrain_project.R;
import com.yamibo.bbs.group3_skytrain_project.activities.MainActivity;

import java.util.concurrent.TimeUnit;

import static android.support.v4.app.NotificationCompat.DEFAULT_ALL;

public class AlertDialogManager {
    private static AlertDialog.Builder dialogBuilder;
    private Context context;
    private static AlertDialogManager alertMrg;

    public static AlertDialogManager getInstance(Context _context) {
        if(alertMrg==null){
            alertMrg=new AlertDialogManager(_context);
        }
        return alertMrg;
    }

    public AlertDialogManager(Context context){
        this.context=context;
    }
    public static void getAlertDialog(Context context){
        //Alert Dialog
        dialogBuilder = new AlertDialog.Builder(context);
        //Set title
        dialogBuilder.setTitle("Warning!");

        //Set dialogue Message
        dialogBuilder.setCancelable(false).setMessage("").setPositiveButton
                ("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //continue with delete
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if this button is clicked, just close
                //the dialog
                dialog.cancel();
            }

            //AlertDialog dialog = dialogBuilder.create();

        }).setIcon(R.drawable.ic_alert);//default
        //Create alert dialog for countdown
        final AlertDialog alert2=dialogBuilder.create();
        alert2.show();
    }
    //Alert Dialog
    public void alertDialog() {
        dialogBuilder = new AlertDialog.Builder(context);
        //Set title
        dialogBuilder.setTitle("Warning!");

        //Set dialogue Message
        dialogBuilder.setCancelable(false).setMessage("")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //continue with delete
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if this button is clicked, just close
                //the dialog
                dialog.cancel();
            }

            //AlertDialog dialog = dialogBuilder.create();

        }).setIcon(android.R.drawable.ic_dialog_alert);
        //Create alert dialog for countdown
        final AlertDialog alert2=dialogBuilder.create();
        alert2.show();
        CountDownTimer countdown=new CountDownTimer(300000,1000){
            @Override
            public void onTick(long l) {
                alert2.setMessage("Living organism inside your car!\nStarts Countdown"
                        +String.format("\n%d:%d mins",
                        TimeUnit.MILLISECONDS.toMinutes(l),
                        TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit
                                .MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l))));
            }

            @Override
            public void onFinish() {
                alert2.setMessage("Finished!");
            }

        }.start();
    }
    public void notification() {
        Context notifContext=null;
        String mainMsg = "Notification message from TransLink Vancouver...";

        //Create Notification Builder and setting properties
        Notification.Builder notifBuilder =
                new Notification.Builder(context)
                        .setStyle(new Notification.BigTextStyle().bigText(mainMsg))
                        .setSmallIcon(R.drawable.ic_subway)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setDefaults(DEFAULT_ALL)
                        .setContentTitle("Transit Push Notification Simulator")
                        .setContentText(mainMsg);

        //Attach Actions with action buttons
        Intent notifyIntent = new Intent(context,MainActivity.class);

        //Answer intent
        Intent answerIntent=new Intent(context,MainActivity.class);
        answerIntent.setAction("Confirm");

        //Answer pendingIntent for Confirm
        PendingIntent pdConfirm= PendingIntent
                .getActivity(context,1,answerIntent,
                        0);
        answerIntent.setAction("Cancel");

        PendingIntent contentIntent = PendingIntent
                .getActivity(context, 0, notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);

        notifBuilder.setContentIntent(contentIntent);
        // Add as notification
        NotificationManager manager = (NotificationManager)context.
                getSystemService(Context.NOTIFICATION_SERVICE);

        //Send notification
        int currentNoticeId=+1;
        int noticeId=currentNoticeId;

        if(noticeId== Integer.MAX_VALUE-1){

            noticeId=0;
        }else {
            manager.notify(noticeId,notifBuilder.build());
        }

        //Sound, vibration, and LED light
        notifBuilder.setDefaults(Notification.DEFAULT_SOUND);
        notifBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
        notifBuilder.setLights(Color.GREEN, 3000, 3000);
    }
}
