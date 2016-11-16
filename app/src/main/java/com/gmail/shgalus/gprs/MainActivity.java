package com.gmail.shgalus.gprs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          try {
               setMobileDataEnabled(this, !isMobileDataEnabled(this));
          } catch (Exception e) {}
          finish();
     }

     /*
      * http://stackoverflow.com/questions/11555366/, 6 XI 2016
      */
     private void setMobileDataEnabled(Context context,
                                       boolean enabled)
          throws Exception {
          final ConnectivityManager cm =
               (ConnectivityManager)context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
          final Class cmClass = Class.forName(
               cm.getClass().getName());
          final Field f = cmClass.getDeclaredField("mService");
          f.setAccessible(true);
          final Object icm = f.get(cm);
          final Class icmClass = Class.forName(
               icm.getClass().getName());
          final Method method = icmClass.getDeclaredMethod(
               "setMobileDataEnabled", Boolean.TYPE);
          method.setAccessible(true);
          method.invoke(icm, enabled);
     }

     /*
      * https://developer.android.com/training/
      * monitoring-device-state/connectivity-monitoring.html, 6 XI
      * 2016
      */
     private boolean isMobileDataEnabled(Context context) {
          final ConnectivityManager cm =
               (ConnectivityManager)context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
          final NetworkInfo ni = cm.getNetworkInfo(
               ConnectivityManager.TYPE_MOBILE);
          final NetworkInfo.State s = ni.getState();
          return s != android.net.NetworkInfo.State.DISCONNECTED &&
               s != android.net.NetworkInfo.State.DISCONNECTING;
     }
}
