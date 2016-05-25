package com.wallpaperapp.jamie.wallpaperdownloader;

import android.os.Build;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
/**
 * Created by jamie on 26/05/2016.
 */
public class MainMenuTest {
    private MainMenuFragment MainMenuFragment;
    private MainMenuActivity MainMenuActivity;


    // @Before => JUnit 4 annotation that specifies this method should run before each test is run
    // Useful to do setup for objects that are needed in the test
    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        MainMenuActivity = Robolectric.setupActivity(MainMenuActivity.class);
    }


    //Simple test for the string contained in the browse wallpaper button
    @Test
    public void validateTextViewContent() {
        Button btnBrowseWallpapers = (Button) MainMenuActivity.findViewById(R.id.btn_browse_wallpapers);
        assertNotNull("TextView could not be found", btnBrowseWallpapers);
        assertTrue("Button contains incorrect text",
                MainMenuActivity.getString(R.string.main_menu_browse_wallpapers).equals(btnBrowseWallpapers.getText().toString()));
    }
}
