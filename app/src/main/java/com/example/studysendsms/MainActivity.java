package com.example.studysendsms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etPhone,etMessage;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPhone = findViewById(R.id.etPhone);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // בודק האם יש אישור לשליחת sms
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED)
                {
                    sendSMS();
                }
                else { // מבקש אישור לשליחת sms
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},100);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) // בודק האם ניתן אישור לשליחה
        {
            sendSMS();
        }
        else {
            Toast.makeText(this,"permission denied",Toast.LENGTH_LONG).show(); // מודיע שלא ניתן אישור
        }
    }

    private void sendSMS() {
        String phone = etPhone.getText().toString();
        String message = etMessage.getText().toString();

        if (!phone.isEmpty() && !message.isEmpty()) // בודק האם השדות ריקים
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone,null,message,null,null); // שליחת ה-sms
            Toast.makeText(this,"SMS sent successfully",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"please enter the details",Toast.LENGTH_LONG).show();

        }
    }
}