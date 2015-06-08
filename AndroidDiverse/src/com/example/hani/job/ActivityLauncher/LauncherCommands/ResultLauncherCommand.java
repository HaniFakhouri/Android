package com.example.hani.job.ActivityLauncher.LauncherCommands;

import android.app.Activity;

import com.example.hani.job.ActivityLauncher.ActivityLauncherPolicy;
import com.example.hani.job.ActivityLauncher.Command;

/**
 * Created by hani on 2015-06-08.
 */
public class ResultLauncherCommand implements Command {

    private ActivityLauncherPolicy mLauncher;
    private Class mClass;
    private int mResultCode;
    private Activity mActivity;

    public ResultLauncherCommand(ActivityLauncherPolicy launcher, Class clazz, int resultCode,
                                 Activity activity) {
        mLauncher = launcher;
        mClass = clazz;
        mResultCode = resultCode;
        mActivity = activity;
    }

    @Override
    public void launch() {
        mLauncher.launchActivityByClassForResult(mClass, mResultCode, mActivity);
    }

}
