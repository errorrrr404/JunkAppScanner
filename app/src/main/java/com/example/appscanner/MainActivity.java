package com.example.appscanner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Variables declarations
    Button btnLaunch;
    TextView tvTotalApps;
    ListView lvAppList;
    TextView tvOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Integrating variables with UI
        btnLaunch = findViewById(R.id.btnLaunch);
        tvTotalApps = findViewById(R.id.tvTotalApps);
        lvAppList = findViewById(R.id.lvAppList);
        tvOwner = findViewById(R.id.tvOwner);

        btnLaunch.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                List<PackageInfo> packageInfoList = getPackageManager().getInstalledPackages(0);
                String[] stringsArray = new String[packageInfoList.size()];
                for (int i = 0; i < packageInfoList.size(); i++) {
                    PackageInfo packageInfo = packageInfoList.get(i);
                    if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM)==0) {
                        String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                        Log.e("App Name : " + Integer.toString(i), appName);
                        stringsArray[i] = appName;
                    }
                }

                //calling function appCount, passing stringsArray as parameter
                int mainCount = appCount(stringsArray);

                //Setting number of additional downloaded apps on the text view
                tvTotalApps.setText("Total number of apps : " + mainCount);

                String[] foundApps = foundApps(stringsArray, mainCount);

                //explicit conversion of string[] to arrayList<String>
                ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(foundApps));

                //Setting found apps to the list view on first screen (Main Activity)
                lvAppList.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList));

                //Array containing harmful apps
                String[] harmfulArrayList = {"PikAShow", "ShareIt", "PubG", "GetInsta", "CamScanner", "UCBrowser", "DURecorder", "VPN"};

                //Array list to fetch the harmful apps found
                ArrayList<String> foundArrayList = new ArrayList<String>();

                //Comparing the applist with that of the harmful apps array
                for (int x = 0; x < harmfulArrayList.length; x++) {
                    int finalX = x;
                    Boolean finalResult = arrayList.stream().anyMatch(s -> s.equalsIgnoreCase(harmfulArrayList[finalX]));
                    System.out.println(finalResult);
                    if (finalResult) {
                        foundArrayList.add(harmfulArrayList[x]);
                    }
                }

                Intent intent = new Intent(MainActivity.this, Activity2.class);
                intent.putStringArrayListExtra("foundHarmfulApps", foundArrayList);

                startActivity(intent);
            }

            //Function to calculate the additional downloaded applications on device
            public int appCount(String[] strings){
                int count = 0;

                for (int i =0; i< strings.length; i++){
                    if(strings[i]!=null){
                        count++;
                    }
                }
                return count;
            }

            //Function to fetch only the additional apps in array (ignoring null values)
            public String[] foundApps(String[] strings, int finalCount){

                String[] foundApps = new String[finalCount];

                for (int i=0; i<finalCount; i++){
                    for (int j=0; j<strings.length; j++){
                        if (strings[j] != null){
                            foundApps[i] = strings[j];
                            i++;
                        }
                    }
                }
                return foundApps;
            }
        });
    }
}