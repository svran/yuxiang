package air.svran.hook.miui.aod;

import android.app.Activity;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private CheckedTextView hideIcon;
    private SharedPreferences sharedPreferences;
    private boolean needHideIcon = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideIcon = findViewById(R.id.hideIcon);
        hideIcon.setChecked(needHideIcon);
        sharedPreferences = getApplication().getSharedPreferences(HookEntry.PACKAGE_NAME_SELF + "_preferences", MODE_MULTI_PROCESS);
        needHideIcon = sharedPreferences.getBoolean("XP_HIDE_ICON", false);
        hideIcon.setChecked(needHideIcon);
        hideIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideIcon.toggle();
                needHideIcon = hideIcon.isChecked();
                sharedPreferences.edit().putBoolean("XP_HIDE_ICON", needHideIcon).commit();
                showLauncherIcon(needHideIcon);
            }
        });
        ((TextView) findViewById(R.id.info)).setText(AppUtils.isModuleActive() ? "已激活" : "未激活");
    }

    public void showLauncherIcon(boolean needHideIcon) {
        PackageManager packageManager = this.getPackageManager();
        int show = needHideIcon ? PackageManager.COMPONENT_ENABLED_STATE_DISABLED : PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        packageManager.setComponentEnabledSetting(getAliasComponentName(), show, PackageManager.DONT_KILL_APP);
    }

    private ComponentName getAliasComponentName() {
        return new ComponentName(this, HookEntry.PACKAGE_NAME_SELF + ".MainActivityAlias");
    }

}