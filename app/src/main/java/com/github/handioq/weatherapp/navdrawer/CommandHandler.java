package com.github.handioq.weatherapp.navdrawer;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Class store the map with commands to execute.
 */
public class CommandHandler {

    private Map<String, ICommand> commands = new HashMap<String, ICommand>();
    private Context context;

    public CommandHandler(Context context)
    {
        this.context = context;
        commands.put("Settings", new SettingsCommand(context));
    }

    /**
     * Handle the request and execute it.
     * @param action
     */
    public void handleRequest(String action) {
        if (commands.containsKey(action))
        {
            try {
                ICommand command = commands.get(action);
                command.execute();
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        }
    }

}
