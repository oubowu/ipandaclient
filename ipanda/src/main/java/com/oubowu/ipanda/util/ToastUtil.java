package com.oubowu.ipanda.util;

import android.app.Application;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.oubowu.ipanda.R;

/**
 * 作者：特种兵__AK47
 * 链接：https://www.jianshu.com/p/b6f207ce367b
 * 來源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
public class ToastUtil {

    //    private static Context sContext = BasicApp.getInstance();// App生命周期中唯一Context，BaseApplication继承Application
    //    private static LayoutInflater sInflater = LayoutInflater.from(sContext);// 布局加载
    //    private static View sToastView = sInflater.inflate(R.layout.layout_top_toast, null);
    //    private static TextView sMsgView = (TextView) sToastView.findViewById(R.id.tv_msg_text);

    private static Application sContext = null;
    private static View sToastView = null;
    private static TextView sMsgView = null;

    private static final int TYPE_CODE_SUCCESS = 0x01;
    private static final int TYPE_CODE_ERROR = 0x02;
    private static int COLOR_SUCCESS = 0;
    private static int COLOR_ERROR = 0;
    private static final int DEFAULT_TIME_DELAY = 50;// 单位：毫秒

    private static Toast sToast;// 系统提示类
    private static Handler sHandler;

    public static void init(Application context) {
        sContext = context;// App生命周期中唯一Context，BaseApplication继承Application
        sToastView = LayoutInflater.from(sContext).inflate(R.layout.layout_top_toast, null);
        sMsgView = sToastView.findViewById(R.id.tv_msg_text);
        COLOR_SUCCESS = sContext.getResources().getColor(R.color.msg_status_success);
        COLOR_ERROR = sContext.getResources().getColor(R.color.msg_status_warn);
    }

    public static void showSuccessMsg(int msgResId) {
        try {
            showSuccessMsg(sContext.getString(msgResId));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void showErrorMsg(int msgResId) {
        try {
            showErrorMsg(sContext.getString(msgResId));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void showSuccessMsg(String msg) {
        showMsg(TYPE_CODE_SUCCESS, msg);
    }

    public static void showErrorMsg(String msg) {
        showMsg(TYPE_CODE_ERROR, msg);
    }

    private static void showMsg(final int typeCode, final String msg) {
        if (sContext == null//
                || ForegroundCallbacks.get().isBackground()// 如果APP回到后台，则不显示
                || msg == null) {
            Log.e("ToastUtil", "80行-showMsg(): " + "如果APP回到后台，则不显示");
            return;
        }

        if (sToast == null) {// 防止重复提示：不为Null，即全局使用同一个Toast实例
            sToast = new Toast(sContext);
        }

        if (sHandler == null) {
            sHandler = new Handler(Looper.getMainLooper());
        }

        sHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int msgViewBagColor = 0;
                switch (typeCode) {
                    case TYPE_CODE_SUCCESS:
                        msgViewBagColor = COLOR_SUCCESS;
                        break;
                    case TYPE_CODE_ERROR:
                        msgViewBagColor = COLOR_ERROR;
                        break;
                    default:
                        msgViewBagColor = COLOR_SUCCESS;
                        break;
                }
                sMsgView.setBackgroundColor(msgViewBagColor);
                sMsgView.setText(msg);
                sToast.setView(sToastView);
                sToast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);// 顶部居中
                sToast.setDuration(Toast.LENGTH_SHORT);
                sToast.show();
            }
        }, DEFAULT_TIME_DELAY);
    }

    // 暂不对外提供：主要针对需要在某个时候，取消提示
    public static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }

}
