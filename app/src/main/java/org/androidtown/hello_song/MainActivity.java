package org.androidtown.hello_song;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etTelNr;
    TextView displayText;

    int MY_PERMISSONS_REQUEST_SEND_SMS = 1;

    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReceiver, smsDelivedReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sentPI = PendingIntent.getBroadcast(this, 0 , new Intent(SENT) , 0);
        deliveredPI = PendingIntent.getBroadcast(this, 0 , new Intent(DELIVERED),0);
        etTelNr = (EditText) findViewById(R.id.etTelNr);
        displayText = (TextView)findViewById(R.id.textView);
    }

    protected void onResume() {
        super.onResume();
        smsSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(MainActivity.this, "SMS sent!", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(MainActivity.this, "Generic failure", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(MainActivity.this, "No Serivce", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(MainActivity.this, "NUll PDU!", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(MainActivity.this, "Radio OFF", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        smsDelivedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(MainActivity.this, "SMS delivered", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(MainActivity.this, "SMS not delivered", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        registerReceiver(smsSentReceiver , new IntentFilter(SENT));
        registerReceiver(smsDelivedReceiver, new IntentFilter(DELIVERED));
    }


    public void onButton1Clicked(View v) {
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.naver.com"));
        startActivity(myIntent);
        Toast.makeText(getApplicationContext(),"Pressed Start Button!",Toast.LENGTH_LONG).show();
    } // Button1 눌렸을때 -> naver접속
    //todo bluetooth

    protected void onPause() {
        super.onPause();

        unregisterReceiver(smsDelivedReceiver);
        unregisterReceiver(smsSentReceiver);
    }


    public void onSendButtonClicked(View v) {

        String message = "TEST!";

        if(displayText.getText()=="none") {
            Toast.makeText(getApplicationContext(),"Telephone Number Error",Toast.LENGTH_LONG).show();
        }

        else {
            String telNr = displayText.getText().toString();

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSONS_REQUEST_SEND_SMS);
            } else {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(telNr, null, message, sentPI, deliveredPI);
            }
        }

    } // SendButton 눌렸을때 -> 문자 전송

    public void onMenuButtonClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }

    public void onUploadButtonClicked(View v) { //Upload 눌렀을때 -> 문자보낼 변호 등록
        String number = etTelNr.getText().toString();
        displayText.setText(number);
        Toast.makeText(getApplicationContext(),"Registered!",Toast.LENGTH_LONG).show();
    }





}
