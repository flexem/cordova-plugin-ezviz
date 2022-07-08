package ezviz.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import __PACKAGE_NAME__.R;

@SuppressLint("Registered")
public class RootActivity extends Activity {

    public final static String TAG = RootActivity.class.getSimpleName();

    protected static Context mContext;

    private Toast mToast = null;

    private boolean mIsTip = true;

    protected void showToast(int id) {
        if (!mIsTip) {
            return;
        }

        if (isFinishing()) {
            return;
        }
        String text = getString(id);
        if (text != null && !text.equals("")) {
            if (mToast == null) {
                mToast = Toast.makeText(this, text, Toast.LENGTH_LONG);
                mToast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                mToast.setText(text);
            }
            mToast.show();
        }
    }

    protected void showToast(int id, int errCode) {
        if (!mIsTip) {
            return;
        }

        if (isFinishing()) {
            return;
        }

        String text = getString(id);
        if (errCode != 0) {
            int errorId = getErrorId(errCode);
        if (errorId != 0) {
            text = getString(errorId);
        } else {
            text = text + " (" + errCode + ")";
        }
    }
        if (text != null && !text.equals("")) {
            if (mToast == null) {
                mToast = Toast.makeText(this, text, Toast.LENGTH_LONG);
                mToast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                mToast.setText(text);
            }
            mToast.show();
        }
    }

    protected void showToast(final CharSequence text) {
        LogUtil.d(TAG, text.toString());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!mIsTip) {
                    return;
                }

                if (isFinishing()) {
                    return;
                }
                if (text != null && !text.equals("")) {
                    if (mToast == null) {
                        mToast = Toast.makeText(mContext, text, Toast.LENGTH_LONG);
                        mToast.setGravity(Gravity.CENTER, 0, 0);
                    } else {
                        mToast.setText(text);
                    }
                    mToast.show();
                }
            }
        });
    }

    protected int getErrorId(int errorCode) {
        int errorId = this.getResources().getIdentifier("error_code_" + errorCode, "string", this.getPackageName());

        return errorId;
    }

    private static List<Activity> mActivityList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mActivityList.add(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityList.remove(this);
    }

    protected void toast(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private AlertDialog mLastDialog = null;
    protected void dialog(final String title, final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mLastDialog != null && mLastDialog.isShowing()){
                    mLastDialog.dismiss();
                }
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RootActivity.this);
                dialogBuilder.setTitle(title);
                ViewGroup msgLayoutVg = (ViewGroup) LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_dialog_tip, null);
                TextView msgTv = (TextView) msgLayoutVg.findViewById(R.id.tv_tip);
                if (msgTv != null){
                    msgTv.setText(msg);
                }
                dialogBuilder.setView(msgLayoutVg);
                dialogBuilder.setPositiveButton(getApplicationContext().getString(R.string.btn_ensure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialogBuilder.setCancelable(false);
                mLastDialog = dialogBuilder.show();
            }
        });
    }

}
