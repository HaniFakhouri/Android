package com.example.hani.job.ActivityLauncher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ActivityLauncherPolicy {

    private Context mContext;

    public ActivityLauncherPolicy(Context context) {
        mContext = context;
    }

    public void launchActivityByPackageName(String packageName) {
        Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(packageName);
        startActivity(intent);
    }

    public void launchActivityByAction(String action) {
        Intent intent = new Intent(action);
        startActivity(intent);
    }

    public void launchActivityByClass(Class clazz) {
        launchActivityByClass(mContext, clazz);
    }

    public void launchActivityByClass(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        startActivity(intent);
    }

    public void launchActivityByPackageNameResult(String pacakgeName, int resultCode, Activity activity) {
        Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(pacakgeName);
        activity.startActivityForResult(intent, resultCode);
    }

    public void launchActivityByClassForResult(Class clazz, int resultCode, Activity activity) {
        Intent intent = new Intent(mContext, clazz);
        intent.putExtra("requestCode", resultCode);
        activity.startActivityForResult(intent, resultCode);
    }

    private void startActivity(Intent intent) {
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            mContext.startActivity(intent);
        }
    }

}
