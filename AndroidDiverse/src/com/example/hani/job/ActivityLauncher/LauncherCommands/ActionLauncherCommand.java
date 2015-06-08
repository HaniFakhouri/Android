package com.example.hani.job.ActivityLauncher.LauncherCommands;

import com.example.hani.job.ActivityLauncher.ActivityLauncherPolicy;
import com.example.hani.job.ActivityLauncher.Command;

/**
 * Created by hani on 2015-06-08.
 */
public class ActionLauncherCommand implements Command {

    private ActivityLauncherPolicy mLauncher;
    private String mAction;

    public ActionLauncherCommand(ActivityLauncherPolicy launcher, String action) {
        mLauncher = launcher;
        mAction = action;
    }

    @Override
    public void launch() {
        mLauncher.launchActivityByAction(mAction);
    }
}
