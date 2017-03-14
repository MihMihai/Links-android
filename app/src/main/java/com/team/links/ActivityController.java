package com.team.links;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Mihai on 13.03.2017.
 */

public class ActivityController {

    public static void showToastMessage(Activity context, String description) {
        Toast.makeText(context,description,Toast.LENGTH_LONG).show();
    }
}
