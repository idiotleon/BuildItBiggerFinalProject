package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;
import android.util.Pair;

import java.util.concurrent.ExecutionException;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class EndpointsAsyncTaskTest extends ApplicationTestCase<Application> {
    public EndpointsAsyncTaskTest() {
        super(Application.class);
    }

    public void testVerifySuccessfulRetrieval(){
        String result = null;
        try {
            result = new MainActivity.EndpointAsyncTask().execute(new Pair<Context, String>(getContext(), "Leon")).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertEquals("Hi, Leon", result);
    }
}