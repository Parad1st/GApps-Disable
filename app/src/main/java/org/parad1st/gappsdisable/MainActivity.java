package org.parad1st.gappsdisable;

import android.content.ComponentName;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

public class MainActivity extends ComponentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationInfo ai =null;
        try {
            ai = this.getPackageManager().getApplicationInfo("com.google.android.gms",0); // Имя пакета по которому будем смотреть, отключены Gapps или нет
        } catch (PackageManager.NameNotFoundException e) {
            this.finishAffinity();
        }
        boolean enabled = ai.enabled;
        try {
            //Ниже можешь написать любые команды, я использую su -c pm disable для отключения приложений
            //com.google.android.gms - гугл сервисы, com.google.android.googlequicksearchbox - гугл (поисковик), com.google.android.gm - почта, com.google.android.vending - плей маркет, com.google.android.youtube - ютуб, com.google.android.partnersetup - партнер сетап, com.google.android.gsf - сервис фреймворк, com.google.android.tts - распознание и синтезатор речи, com.google.android.ims - carrier сервисы
            String command = enabled ? "su -c pm disable com.google.android.gms; su -c pm disable com.google.android.googlequicksearchbox; su -c pm disable com.google.android.gm; su -c pm disable com.google.android.vending; su -c pm disable com.google.android.youtube; su -c pm disable com.google.android.partnersetup; su -c pm disable com.google.android.gsf; su -c pm disable com.google.android.tts, su -c pm disable com.google.android.ims" : "su -c pm enable com.google.android.gms; su -c pm enable com.google.android.googlequicksearchbox; su -c pm enable com.google.android.gm; su -c pm enable com.google.android.vending; su -c pm enable com.google.android.youtube; su -c pm enable com.google.android.partnersetup; su -c pm enable com.google.android.gsf; su -c pm enable com.google.android.tts; su -c pm enable com.google.android.ims";
            String toast = enabled ? "GApps Disabled" : "GApps Enabled";
            Runtime.getRuntime().exec(command);
            Toast.makeText(this,toast, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
        }
        if(enabled) {
            changeIconToFirst();
        } else {
            changeIconToSecond();
        }
        this.finishAffinity();
    }


    //спс спайку
    private void changeIconToSecond() {
        // первая иконка при отключении
        PackageManager packageManager = getPackageManager();
        packageManager.setComponentEnabledSetting(new ComponentName(MainActivity.this, "org.parad1st.gappsdisable.MainActivity"),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

        // первая иконка при включении
        packageManager.setComponentEnabledSetting(new ComponentName(MainActivity.this, "org.parad1st.gappsdisable.MainActivityHueten"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }
    private void changeIconToFirst() {
        // вторая иконка при отключении
        PackageManager packageManager = getPackageManager();
        packageManager.setComponentEnabledSetting(new ComponentName(MainActivity.this, "org.parad1st.gappsdisable.MainActivity"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        // вторая иконка при включении
        packageManager.setComponentEnabledSetting(new ComponentName(MainActivity.this, "org.parad1st.gappsdisable.MainActivityHueten"),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}
