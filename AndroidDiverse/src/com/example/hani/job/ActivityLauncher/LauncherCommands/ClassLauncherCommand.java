package com.example.hani.job.ActivityLauncher.LauncherCommands;

import com.example.hani.job.ActivityLauncher.ActivityLauncherPolicy;
import com.example.hani.job.ActivityLauncher.Command;

/**
 * Created by hani on 2015-06-08.
 */
public class ClassLauncherCommand implements Command {

    private ActivityLauncherPolicy mLauncher;
    private Class mClass;

    public ClassLauncherCommand(ActivityLauncherPolicy launcher, Class clazz) {
        mLauncher = launcher;
        mClass = clazz;
    }

    @Override
    public void launch() {
        mLauncher.launchActivityByClass(mClass);
    }

}
