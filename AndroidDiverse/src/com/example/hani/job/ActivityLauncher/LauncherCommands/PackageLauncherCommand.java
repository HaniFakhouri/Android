package com.example.hani.job.ActivityLauncher.LauncherCommands;

import com.example.hani.job.ActivityLauncher.ActivityLauncherPolicy;
import com.example.hani.job.ActivityLauncher.Command;

/**
 * Created by hani on 2015-06-08.
 */
public class PackageLauncherCommand implements Command {

    private ActivityLauncherPolicy mLauncher;
    private String mPackageName;

    public PackageLauncherCommand(ActivityLauncherPolicy launcher, String packageName) {
        mLauncher = launcher;
        mPackageName = packageName;
    }

    @Override
    public void launch() {
        mLauncher.launchActivityByPackageName(mPackageName);
    }
}
