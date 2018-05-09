package com.souschef.sork.sous_chef;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Robin on 2018-05-09.
 */

public class StartTutorial {
    public final static int RECIPE_CHOOSER = 0;
    public final static int COOKING = 1;
    public static int currentTutorial = 0;
    public static boolean[] shownTutorial = new boolean[2];

    public static void showTutorial(int activity, Activity activityObject) {
        if(!shownTutorial[activity]) {
            shownTutorial[activity] = true;
            currentTutorial = activity;
            Intent intent = new Intent(activityObject.getBaseContext(), TutorialActivity.class);
            activityObject.startActivity(intent);
        }
    }
}
