package com.github.handioq.weatherapp.navdrawer;

import android.content.Context;
import android.content.Intent;

import com.github.handioq.weatherapp.activities.SettingsActivity;

public class SettingsCommand implements ICommand {

    private Context context;

    public SettingsCommand(Context context) {
        this.context = context;
    }

    public SettingsCommand() { }

    @Override
    public void execute() {
        Intent settingsIntent = new Intent(context, SettingsActivity.class);
        context.startActivity(settingsIntent);
    }
}
