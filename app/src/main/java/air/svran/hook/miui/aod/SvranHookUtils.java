package air.svran.hook.miui.aod;

import java.lang.reflect.Method;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * 功能介绍: XpHook工具类 <br/>
 * 调用方式: / <br/>
 * <p/>
 * 创建作者: Svran - ranliulian@gmail.com<br/>
 * 创建电脑: Svran-MY  <br/>
 * 创建时间: 2021/4/14 10:12 <br/>
 * 最后编辑: 2021/4/14 10:12 - Svran<br/>
 *
 * @author Svran
 */
public class SvranHookUtils {

    public static boolean findMethod(String className, String methodName, ClassLoader classLoader, String tip, Object... parameterTypes) {
        for (int i = 0; i < parameterTypes.length; i++) {
            if (parameterTypes[i] == null) {
                XposedBridge.log("Svran: Error:××××××××××××功能异常(" + tip + " 第" + i + "个参数 null )××××××××××××\n.");
                return false;
            }
        }
        Class<?> cls = XposedHelpers.findClassIfExists(className, classLoader);
        if (cls == null) {
            XposedBridge.log("Svran: Error:××××××××××××功能异常(" + tip + " Class -> " + className + " <- 不存在! )××××××××××××\n.");
            return false;
        }
        Method method = XposedHelpers.findMethodExactIfExists(cls, methodName, parameterTypes);
        if (method == null) {
            XposedBridge.log("Svran: Error:××××××××××××功能异常(" + tip + " Method -> " + methodName + " <- 不存在! )××××××××××××\n.");
            return false;
        }
        XposedBridge.log("Svran: >>>>>>>>>>>>>>>>>(" + tip + " 找到该方法!)<<<<<<<<<<<<<<<<<<");
        return method != null;
    }

    private SvranHookUtils() {
    }
}
