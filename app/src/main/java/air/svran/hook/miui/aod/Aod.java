package air.svran.hook.miui.aod;

import android.widget.TextView;

import java.util.Calendar;
import java.util.TimeZone;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * 功能介绍:  <br/>
 * 调用方式: / <br/>
 * <p/>
 * 作   者: Svran - ranliulian@gmail.com <br/>
 * 创建电脑: Svran-MY  <br/>
 * 创建时间: 2020/11/19 15:40 <br/>
 * 最后编辑: 2020/11/19 - Svran
 *
 * @author Svran
 */
public class Aod {
    public Aod(XC_LoadPackage.LoadPackageParam lpparam) {
        XposedBridge.log("Svran: Hook Aod");
        hookSunTime(lpparam.classLoader);
        hookSmartCoverClockTime(lpparam.classLoader);
        hookSignatureTime(lpparam.classLoader);
        hookHorizontalClockTime(lpparam.classLoader);
        // 主要使用 com.miui.aod.widget.IAodClock updateTime(boolean)
    }

//    private int sec = 0;

    public void updateTime(TextView textView, boolean post) {
//        if (!post) {
//            sec = 0;
//        } else {
//            sec += 1;
//        }
        if (textView != null) {
            Calendar c = Calendar.getInstance(TimeZone.getDefault());
//            String hour = c.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + c.get(Calendar.HOUR_OF_DAY) : "" + c.get(Calendar.HOUR_OF_DAY);
            String minute = c.get(Calendar.MINUTE) < 10 ? "0" + c.get(Calendar.MINUTE) : "" + c.get(Calendar.MINUTE);
            String second = c.get(Calendar.SECOND) < 10 ? "0" + c.get(Calendar.SECOND) : "" + c.get(Calendar.SECOND);
            String time = c.get(Calendar.HOUR_OF_DAY) + ":" + minute + ":" + second;
            textView.setText(time);
            textView.postDelayed(() -> {
//                if (sec >= 15) {
//                    LogUtils.d("Svran: 超过15秒,不再刷新");
//                    textView.setTag(null);
//                    return;
//                }
                if (textView.getTag() != null && !(boolean) textView.getTag()) {
//                    LogUtils.d("Svran: break");
                    return;
                }
                updateTime(textView, true);
            }, 1000);
//            LogUtils.d("Svran: 设置时间: " + time);
        } else {
            XposedBridge.log("Svran: Aod null textView");
        }
    }

    /*
    @Override // com.miui.aod.widget.IAodClock
    public void updateTime(boolean z) {
        String str;
        TimeZone timeZone = this.mTimeZone;
        if (timeZone == null) {
            timeZone = TimeZone.getDefault();
        }
        this.mCal = new Calendar(timeZone);
        new SimpleDateFormat(Utils.getHourMinformat(this.mContext)).setTimeZone(timeZone);
        int i = this.mCal.get(18);
        if (!z && i > 12) {
            i -= 12;
        }
        if (!z && i == 0) {
            i = 12;
        }
        TextView textView = this.mClockHorizontal;
        boolean z2 = true;
        if (textView != null) {
            textView.setText(String.format("%d:%02d", Integer.valueOf(i), Integer.valueOf(this.mCal.get(20))));
        }
        this.mDateView.setText(this.mCal.format(this.mContext, AODSettings.getDateFormat(this.mContext, z, false)));
        if (this.mDateLunar != null && this.mStyleInfo.isLunarSwitchOn(this.mContext)) {
            TextView textView2 = this.mDateLunar;
            Calendar calendar = this.mCal;
            Context context = this.mContext;
            textView2.setText(calendar.format(context, AODSettings.getDateFormat(context, z, true)));
        }
        StyleInfo styleInfo = this.mStyleInfo;
        if (styleInfo != null && styleInfo.isChangeColorAlongTime()) {
            LinearLayout linearLayout = this.mGradientLayout;
            if (linearLayout instanceof GradientLinearLayout) {
                Drawable gradientOverlayDrawable = ((GradientLinearLayout) linearLayout).getGradientOverlayDrawable();
                setClockMask(0, AodDrawables.get24GradientDrawableRes());
                if (gradientOverlayDrawable != ((GradientLinearLayout) this.mGradientLayout).getGradientOverlayDrawable()) {
                    this.mGradientLayout.invalidate();
                }
            }
        }
        if (this.mIsDual) {
            TimeZone timeZone2 = this.mTimeZoneDual;
            if (timeZone2 == null) {
                timeZone2 = TimeZone.getDefault();
            }
            this.mCity.setText(AODSettings.getCityNameByTimeZone(this.mContext, TimeZone.getDefault(), false));
            this.mCity2.setText(AODSettings.getCityNameByTimeZone(this.mContext, this.mTimeZoneDual, false));
            this.mCalDual = new Calendar(timeZone2);
            if (this.mClockHorizontalDual != null) {
                int i2 = this.mCalDual.get(18);
                if (!z && i2 > 12) {
                    i2 -= 12;
                }
                if (!z && i2 == 0) {
                    i2 = 12;
                }
                this.mClockHorizontalDual.setText(String.format("%d:%02d", Integer.valueOf(i2), Integer.valueOf(this.mCalDual.get(20))));
            }
            if (this.mLayoutGravity != 0) {
                z2 = false;
            }
            if (z2) {
                str = AODSettings.getDateFormat(this.mContext, z, false);
                this.mDateView.setText(this.mCal.format(this.mContext, str));
            } else {
                str = this.mContext.getResources().getString(z ? R.string.aod_dual_clock_date : R.string.aod_dual_clock_date_12);
                if (AODSettings.isSameDate(this.mCal, this.mCalDual)) {
                    str = this.mContext.getResources().getString(z ? R.string.aod_dual_togother : R.string.aod_dual_togother_12);
                }
            }
            this.mDateViewDual.setText(this.mCalDual.format(this.mContext, str));
        }
    }// */

    private TextView textView4;

    private void hookHorizontalClockTime(ClassLoader classLoader) {
        String tip = "时间4";
        String clsName = "com.miui.aod.widget.HorizontalClock";
        String methodName = "updateTime";
        boolean canHook = SvranHookUtils.findMethod(clsName, methodName, classLoader, tip, boolean.class);
        if (canHook) {
            XposedBridge.log("Svran: -------------已开启(" + tip + ")-----------------");
            XposedHelpers.findAndHookMethod(clsName, classLoader, methodName, boolean.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    XposedBridge.log("Svran: 时间4");
                    try {
                        TextView tv = (TextView) XposedHelpers.findFieldIfExists(Class.forName(clsName, false, classLoader), "mClockHorizontal").get(param.thisObject);
                        if (tv != textView4) {
                            textView4 = tv;
                            updateTime(textView4, false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            XposedBridge.log("Svran: -------------已结束(" + tip + ")✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔\n.");
        } else {
            XposedBridge.log("Svran: Error:××××××××××××功能异常(" + tip + ")××××××××××××\n.");
        }
    }

    /*

    @Override // com.miui.aod.widget.IAodClock
    public void updateTime(boolean z) {
        SignatureAodView signatureAodView = this.mSignatureAodView;
        if (signatureAodView != null) {
            signatureAodView.set24GradientColor(AodDrawables.get24GradientDrawableRes());
            TimeZone timeZone = this.mTimeZone;
            if (timeZone == null) {
                timeZone = TimeZone.getDefault();
            }
            Calendar calendar = new Calendar(timeZone);
            Context context = this.mSignatureAodView.getContext();
            new SimpleDateFormat(Utils.getHourMinformat(context)).setTimeZone(timeZone);
            TextView textView = this.mTimeView;
            Resources resources = context.getResources();
            int i = R.string.aod_dual_togother;
            textView.setText(calendar.format(context, resources.getString(z ? R.string.aod_dual_togother : R.string.aod_signature_12)));
            this.mDateView.setText(calendar.format(context, context.getResources().getString(R.string.aod_lock_screen_date_signature)));
            if (this.mIsDual) {
                TextView textView2 = this.mCity;
                if (textView2 != null) {
                    textView2.setText(AODSettings.getCityNameByTimeZone(context, TimeZone.getDefault(), false));
                }
                TextView textView3 = this.mCity2;
                if (textView3 != null) {
                    textView3.setText(AODSettings.getCityNameByTimeZone(context, this.mTimeZoneDual, false));
                }
                if (this.mDateViewDual != null) {
                    String string = context.getResources().getString(z ? R.string.aod_dual_clock_date : R.string.aod_dual_clock_date_12);
                    Calendar calendar2 = new Calendar(this.mTimeZoneDual);
                    if (AODSettings.isSameDate(calendar, calendar2)) {
                        Resources resources2 = context.getResources();
                        if (!z) {
                            i = R.string.aod_dual_togother_12;
                        }
                        string = resources2.getString(i);
                    }
                    this.mDateViewDual.setText(calendar2.format(context, string));
                }
            }
        }
    } // */

    private TextView textView3;

    private void hookSignatureTime(ClassLoader classLoader) {
        String tip = "时间3";
        String clsName = "com.miui.aod.widget.SignatureClock";
        String methodName = "updateTime";
        boolean canHook = SvranHookUtils.findMethod(clsName, methodName, classLoader, tip, boolean.class);
        if (canHook) {
            XposedBridge.log("Svran: -------------已开启(" + tip + ")-----------------");
            XposedHelpers.findAndHookMethod(clsName, classLoader, methodName, boolean.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    XposedBridge.log("Svran: 时间3");
                    try {
                        TextView tv = (TextView) XposedHelpers.findFieldIfExists(Class.forName(clsName, false, classLoader), "mTimeView").get(param.thisObject);
                        if (tv != textView3) {
                            textView3 = tv;
                            updateTime(textView3, false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            XposedBridge.log("Svran: -------------已结束(" + tip + ")✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔\n.");
        } else {
            XposedBridge.log("Svran: Error:××××××××××××功能异常(" + tip + ")××××××××××××\n.");
        }
    }

    /*
    @Override // com.miui.aod.widget.IAodClock
    public void updateTime(boolean z) {
        TimeZone timeZone = TimeZone.getDefault();
        this.mCal = new Calendar(timeZone);
        new SimpleDateFormat(Utils.getHourMinformat(getContext())).setTimeZone(timeZone);
        int i = this.mCal.get(18);
        if (!z && i > 12) {
            i -= 12;
        }
        if (!z && i == 0) {
            i = 12;
        }
        this.mTimeView.setText(String.format("%d:%02d", Integer.valueOf(i), Integer.valueOf(this.mCal.get(20))));
        this.mDateView.setText(this.mCal.format(getContext(), getContext().getResources().getString(R.string.aod_lock_screen_date_smartcover_b)).toUpperCase());
        StyleInfo styleInfo = this.mStyleInfo;
        if (styleInfo != null && styleInfo.isChangeColorAlongTime()) {
            Drawable gradientOverlayDrawable = getGradientOverlayDrawable();
            setClockMask(0, AodDrawables.get24GradientDrawableRes());
            if (gradientOverlayDrawable != getGradientOverlayDrawable()) {
                invalidate();
            }
        }
    }// */

    private TextView textView2;

    private void hookSmartCoverClockTime(ClassLoader classLoader) {
        String tip = "时间2";
        String clsName = "com.miui.aod.widget.SmartCoverClockBView";
        String methodName = "updateTime";
        boolean canHook = SvranHookUtils.findMethod(clsName, methodName, classLoader, tip, boolean.class);
        if (canHook) {
            XposedBridge.log("Svran: -------------已开启(" + tip + ")-----------------");
            XposedHelpers.findAndHookMethod(clsName, classLoader, methodName, boolean.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    XposedBridge.log("Svran: 时间2");
                    try {
                        TextView tv = (TextView) XposedHelpers.findFieldIfExists(Class.forName(clsName, false, classLoader), "mTimeView").get(param.thisObject);
                        if (tv != textView2) {
                            textView2 = tv;
                            updateTime(tv, false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            XposedBridge.log("Svran: -------------已结束(" + tip + ")✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔\n.");
        } else {
            XposedBridge.log("Svran: Error:××××××××××××功能异常(" + tip + ")××××××××××××\n.");
        }
    }

    /*
    @Override // com.miui.aod.widget.IAodClock
    public void updateTime(boolean z) {
        TimeZone timeZone = this.mTimeZone;
        if (timeZone == null) {
            timeZone = TimeZone.getDefault();
        }
        Calendar calendar = new Calendar(timeZone);
        int i = calendar.get(18);
        if (!z && i > 12) {
            i -= 12;
        }
        if (!z && i == 0) {
            i = 12;
        }
        TextView textView = this.mClockHorizontal;
        if (textView != null) {
            textView.setText(String.format("%02d:%02d", Integer.valueOf(i), Integer.valueOf(calendar.get(20))));
        }
        if (this.mDual && this.mDateViewDualContainer != null) {
            this.mCity2.setText(AODSettings.getCityNameByTimeZone(this.mContext, this.mTimeZoneDual, false));
            this.mDateViewDual.setText(new Calendar(this.mTimeZoneDual).format(this.mContext, z ? "HH:mm" : "hh:mm"));
        }
    }// */

    private TextView textView1;

    private void hookSunTime(ClassLoader classLoader) {
        String tip = "时间1";
        String clsName = "com.miui.aod.widget.SunClock";
        String methodName = "updateTime";
        boolean canHook = SvranHookUtils.findMethod(clsName, methodName, classLoader, tip, boolean.class);
        if (canHook) {
            XposedBridge.log("Svran: -------------已开启(" + tip + ")-----------------");
            XposedHelpers.findAndHookMethod(clsName, classLoader, methodName, boolean.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    XposedBridge.log("Svran: 时间1");
                    try {
                        TextView tv = (TextView) XposedHelpers.findFieldIfExists(Class.forName(clsName, false, classLoader), "mClockHorizontal").get(param.thisObject);
                        if (tv != textView1) {
                            textView1 = tv;
                            updateTime(textView1, false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            XposedBridge.log("Svran: -------------已结束(" + tip + ")✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔\n.");
        } else {
            XposedBridge.log("Svran: Error:××××××××××××功能异常(" + tip + ")××××××××××××\n.");
        }
    }

    private static void testHook(ClassLoader classLoader) {
    }
}
