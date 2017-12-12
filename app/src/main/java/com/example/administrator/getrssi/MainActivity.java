package com.example.administrator.getrssi;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Collect collect;
    private WifiManager wm;
    EditText editText1;
    EditText editText2;
    StringBuilder r1bulid,d1build,r2bulid,d2bulid,r3build,d3build,r4build,d4build;
    private SQLiteDatabase dbWrite, dbRead;
    private SimpleCursorAdapter adapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new mThread().start();
        Button add = (Button)findViewById(R.id.button);
        collect = new Collect(this,"my.db",null,1);

        dbRead = collect.getReadableDatabase();
        dbWrite = collect.getWritableDatabase();

        add.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                collect.getWritableDatabase();
                SQLiteDatabase db = collect.getWritableDatabase();
                ContentValues values = new ContentValues();
                //第一条
                values.put("ID","1");
                values.put("RSSI",r1bulid.toString());
                values.put("Distance",d1build.toString());
                db.insert("DR",null,values);
                values.clear();
              /*  //第二条
                values.put("ID","2");
                values.put("RSSI",r2bulid.toString());
                values.put("Distance",d2bulid.toString());
                db.insert("DR",null,values);
                values.clear();
                //第三条
                values.put("ID","3");
                values.put("RSSI",r3build.toString());
                values.put("Distance",d3build.toString());
                db.insert("DR",null,values);
                values.clear();
                //第四条
                values.put("ID","4");
                values.put("RSSI",r4build.toString());
                values.put("Distance",d4build.toString());
                db.insert("DR",null,values);
                values.clear();*/
            }
        });
    }


    private class mThread extends Thread{
        @Override
        public void run(){
            while(true){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        obtainListInfo("qwert","399","w","X");
                    }
                });
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private int obtainListInfo(String ssid,String sid,String Sid,String Ssid){

        editText1 = (EditText) findViewById(R.id.et1);
        editText2 = (EditText) findViewById(R.id.et2);
        //显示扫描到的所有wifi信息
        wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wi = wm.getConnectionInfo();

        int strength = wi.getRssi();
        int speed = wi.getLinkSpeed();
        String designation = wi.getSSID();

        String addr = wi.getBSSID();
        String unit = WifiInfo.LINK_SPEED_UNITS;

        if (wm.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
            StringBuilder listinfo = new StringBuilder();
            //搜索到的wifi列表信息
            List<ScanResult> scanResults = wm.getScanResults();

            for (ScanResult sr:scanResults){

                if(sr.SSID.equals(ssid)) {
                    listinfo.append("wifi网络ID：");
                    listinfo.append(sr.SSID);
                    listinfo.append("\nwifi MAC地址：");
                    listinfo.append(sr.BSSID);
                    listinfo.append("\nwifi信号强度：");
                    listinfo.append(sr.level);
                    int RSsi = Math.abs(sr.level);
                    double mlevel = (RSsi-35)/(10*2.1);
                    listinfo.append(("\n距离"));
                    listinfo.append(mlevel+"\n\n");
                    r1bulid.append(sr.level);
                    d1build.append(mlevel);
                }
                if(sr.SSID.equals(Sid)) {
                    listinfo.append("wifi网络ID：");
                    listinfo.append(sr.SSID);
                    listinfo.append("\nwifi MAC地址：");
                    listinfo.append(sr.BSSID);
                    listinfo.append("\nwifi信号强度：");
                    listinfo.append(sr.level);
                    int RSsi = Math.abs(sr.level);
                    double mlevel = (RSsi-35)/(10*2.1);
                    listinfo.append(("\n距离"));
                    listinfo.append(mlevel+"\n\n");
                    r2bulid.append(sr.level);
                    d2bulid.append(mlevel);
                }
                if(sr.SSID.equals(sid)) {
                    listinfo.append("wifi网络ID：");
                    listinfo.append(sr.SSID);
                    listinfo.append("\nwifi MAC地址：");
                    listinfo.append(sr.BSSID);
                    listinfo.append("\nwifi信号强度：");
                    listinfo.append(sr.level);
                    int RSsi = Math.abs(sr.level);
                    double mlevel = (RSsi-35)/(10*2.1);
                    listinfo.append(("\n距离"));
                    listinfo.append(mlevel+"\n\n");
                    r3build.append(sr.level);
                    d3build.append(mlevel);
                }
                if(sr.SSID.equals(Ssid)) {
                    listinfo.append("wifi网络ID：");
                    listinfo.append(sr.SSID);
                    listinfo.append("\nwifi MAC地址：");
                    listinfo.append(sr.BSSID);
                    listinfo.append("\nwifi信号强度：");
                    listinfo.append(sr.level);
                    int RSsi = Math.abs(sr.level);
                    double mlevel = (RSsi-35)/(10*2.1);
                    listinfo.append(("\n距离"));
                    listinfo.append(mlevel+"\n\n");
                    d4build.append(mlevel);
                    r4build.append(sr.level);
                }
            }

            editText2.setText(listinfo.toString());
            String curr_connected_wifi=null;
            curr_connected_wifi="Currently connecting WiFi \'"+designation+"\' \nRssi: "+strength+
                    "\nMac addr: "+addr+"\nspeed: "+speed+" "+unit;
            editText1.setText(curr_connected_wifi.toString());
        }
        return -1;
    }
}
