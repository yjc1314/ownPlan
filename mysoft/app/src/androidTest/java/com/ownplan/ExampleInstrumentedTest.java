package com.ownplan;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.ownplan.com.ownplan.utils.Diary;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.ownplan", appContext.getPackageName());
    }

    @Test
     Diary Getdiary() {

        Diary s = new Diary();
        s.setId(1);
        s.setContext("我是内容");
        Date date = new Date();
        s.setTime(date);
        s.setTitle("我是标题");

        return s ;
    }
}
