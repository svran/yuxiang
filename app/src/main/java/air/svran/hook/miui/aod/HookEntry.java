package air.svran.hook.miui.aod;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookEntry implements IXposedHookLoadPackage, IXposedHookInitPackageResources {
    public static final String PACKAGE_NAME_SELF = BuildConfig.APPLICATION_ID;
    public static final String PACKAGE_NAME_AOD = "com.miui.aod";

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        switch (lpparam.packageName) {
            case PACKAGE_NAME_SELF:
                hookSelf(lpparam);
                break;
            case PACKAGE_NAME_AOD:
                new Aod(lpparam);
                break;
            default:
        }
    }

    private void hookSelf(XC_LoadPackage.LoadPackageParam lpparam) {
        XposedBridge.log("Svran: hook自己");
        Class<?> classAppUtils = XposedHelpers.findClassIfExists(PACKAGE_NAME_SELF + ".AppUtils", lpparam.classLoader);
        if (classAppUtils != null) {
            XposedHelpers.findAndHookMethod(classAppUtils, "isModuleActive", XC_MethodReplacement.returnConstant(true));
        }
        XposedBridge.log("Svran: Hook完毕");
    }

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {
    }
}