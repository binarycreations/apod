package net.binarycreations.apod.daily;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.binarycreations.apod.R;

/**
 * Shows a single daily astronomical picture with an explanation.
 *
 * @author graham.
 */
public class DailyPictureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_picture);

    }
}
