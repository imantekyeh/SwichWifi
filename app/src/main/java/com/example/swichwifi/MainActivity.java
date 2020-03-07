package com.example.swichwifi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    private WifiManager wifiManager;
    private Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aSwitch = (Switch) findViewById(R.id.wifi);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    wifiManager.setWifiEnabled(true);
                    aSwitch.setText("is on wifi");
                } else {
                    wifiManager.setWifiEnabled(false);
                    aSwitch.setText("is off wifi");
                }}});
        if (wifiManager.setWifiEnabled(true)) {
            aSwitch.setChecked(true);
            aSwitch.setText("is on wifi");

        } else {
            wifiManager.setWifiEnabled(false);{
                aSwitch.setChecked(false);
                aSwitch.setText("is off wifi");
            }}}
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int WifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
            switch (WifiStateExtra) {
                case WifiManager.WIFI_STATE_ENABLED:
                    aSwitch.setChecked(true);
                    aSwitch.setText("is on wifi");
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    aSwitch.setChecked(false);
                    aSwitch.setText("is off wifi");
                    break;
            }}};
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(broadcastReceiver, intentFilter);}
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }}