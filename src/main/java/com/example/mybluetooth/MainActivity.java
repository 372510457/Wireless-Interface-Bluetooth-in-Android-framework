package com.example.mybluetooth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ID_READ_WRITE_PERMISSION = 99;
    Button b1,b2,b3,b4;
    ListView lv;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice>pairedDevices;

    private void askBluetoothPermissions(){
        if (Build.VERSION.SDK_INT >=23){
            int bluetoothPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH);
            int bluetoothadminPermission = ActivityCompat.checkSelfPermission(this,Manifest.permission.BLUETOOTH_ADMIN);
            int advertisePermission = ActivityCompat.checkSelfPermission(this,Manifest.permission.BLUETOOTH_ADVERTISE);
            int connectPermission = ActivityCompat.checkSelfPermission(this,Manifest.permission.BLUETOOTH_CONNECT);
            int coarseLocationPermission = ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION);
            int fineLocationPermission = ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);

            if (bluetoothPermission != PackageManager.PERMISSION_GRANTED||
                    bluetoothadminPermission != PackageManager.PERMISSION_GRANTED||
                    advertisePermission != PackageManager.PERMISSION_GRANTED||
                    connectPermission != PackageManager.PERMISSION_GRANTED ||
                    coarseLocationPermission != PackageManager.PERMISSION_GRANTED ||
                    fineLocationPermission != PackageManager.PERMISSION_GRANTED
            ){
                this.requestPermissions(
                        new String[]{
                                Manifest.permission.BLUETOOTH,
                                Manifest.permission.BLUETOOTH_ADMIN,
                                Manifest.permission.BLUETOOTH_ADVERTISE,
                                Manifest.permission.BLUETOOTH_CONNECT,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                        },
                        REQUEST_ID_READ_WRITE_PERMISSION
                );
                return;
            }
        }
    }

    public void on(View v){
        BA = BluetoothAdapter.getDefaultAdapter();
        if (!BA.isEnabled()){
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn,0);
            Toast.makeText(getApplicationContext(),"Turn on", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplicationContext(),"Already on",Toast.LENGTH_LONG).show();
        }
    }

    public void off(View v){
        pairedDevices = BA.getBondedDevices();
        BA.disable();
        Toast.makeText(getApplicationContext(),"Truned off",Toast.LENGTH_LONG).show();

    }

    public void visible(View v){
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(getVisible,0);
    }

    public void list(View v){
        ArrayList list = new ArrayList();
        for(BluetoothDevice bt : pairedDevices){
            list.add(bt.getName()+ "," + bt.getAddress());
        }
        Toast.makeText(getApplicationContext(),"Showing Paired Devices", Toast.LENGTH_SHORT).show();

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        askBluetoothPermissions();
        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        lv = (ListView) findViewById(R.id.listView);
    }

}