package com.example.assignmentcliqueraft;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final int NOTIFICATION_ID = 01;
    Button toast, notification, popUp, complete;
    EditText inputMessage;
    TextView outputMessage;
    TextView popUpTv;
    public String CHANNEL_ID = "channel_id01";
    Dialog popUpDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toast = findViewById(R.id.toastButton);
        notification = findViewById(R.id.notificationButton);
        complete = findViewById(R.id.completeButton);
        inputMessage = findViewById(R.id.messageText);
        outputMessage = findViewById(R.id.outputTv);
        popUpDialog = new Dialog(MainActivity.this);
        popUpDialog.setContentView(R.layout.popup_menu);
        popUp = findViewById(R.id.popUpButton);
        popUpTv = popUpDialog.findViewById(R.id.popUpTv);
        toast.setOnClickListener(view -> {
            if(validateText()){
                Toast.makeText(this, "Please enter a valid text", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, ""+inputMessage.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (validateText()){
                   Toast.makeText(MainActivity.this, "Please enter a valid text", Toast.LENGTH_SHORT).show();
               }
               else {
                   getNotification();
               }
            }
               });
        popUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateText()){
                    Toast.makeText(MainActivity.this, "Please enter a valid text", Toast.LENGTH_SHORT).show();
                }
                else {
                    popUpTv.setText(inputMessage.getText().toString());
                    popUpDialog.show();
                }

            }
        });
        complete.setOnClickListener(view -> {
            if (validateText()){
                Toast.makeText(this, "Please enter a valid text", Toast.LENGTH_SHORT).show();
            }
            else {
                outputMessage.setText("COMPLETED");
            }

        });
       }

    private boolean validateText() {
        String inputString = inputMessage.getText().toString().trim();
        if(TextUtils.isEmpty(inputString)){
            return true;
        }
        else {
            return false;
        }
    }


    private void getNotification() {
        PendingIntent dismissIntent = NotificationActivity.getDismissIntent(NOTIFICATION_ID,getApplicationContext());
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle(inputMessage.getText())
                .setContentText("This is a sample notification")
                .setAutoCancel(true)
                .addAction(R.drawable.ic_baseline_cancel_24,"Dismiss",dismissIntent);
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = " My Notification ";
            String description = " My notification description ";

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}