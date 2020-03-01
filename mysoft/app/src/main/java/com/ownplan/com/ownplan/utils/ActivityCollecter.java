package com.ownplan.com.ownplan.utils;

import android.app.Activity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ActivityCollecter {
    public static List<Activity> activities = new ArrayList<>();

    public static void Add(Activity activity) {
        activities.add(activity);


    }

    public static void Remove(Activity activity) {
        activities.remove(activity);

    }

    public static void Finishall() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {

                activity.finish();

            }

        }

    }


}
