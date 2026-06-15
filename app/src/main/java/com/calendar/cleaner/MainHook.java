package com.calendar.cleaner;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage {

    private static final String TAG = "CalendarCleaner";
    private static final String TARGET_PKG = "com.coloros.calendar";
    private static final String LUNAR_CLASS =
        "com.coloros.calendar.foundation.lunarcalendarlib.a";

    private static final String[] LUNAR_METHODS =
        {"p", "F", "w", "k", "l", "B", "x", "y", "f", "i", "j", "g", "n"};

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!TARGET_PKG.equals(lpparam.packageName)) return;

        XposedBridge.log(TAG + ": loading");

        try {
            Class<?> lunarClass = XposedHelpers.findClass(LUNAR_CLASS, lpparam.classLoader);

            for (String method : LUNAR_METHODS) {
                XposedBridge.hookAllMethods(lunarClass, method, new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) {
                        return "";
                    }
                });
            }

            XposedBridge.log(TAG + ": OK — " + LUNAR_METHODS.length + " methods hooked");
        } catch (Throwable e) {
            XposedBridge.log(TAG + ": FAIL: " + e.getMessage());
        }
    }
}
