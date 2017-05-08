package ville.fi.hikemate.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import ville.fi.hikemate.R;


/**
 * Created by Ville on 6.3.2017.
 */
public class Debug {
    private static int DEBUG_LEVEL;

    public static void print(String className, String tag, String message,  int level) {
        if (level <= DEBUG_LEVEL) {
            String logTag = className + "." + tag;
            Log.d(logTag, message);
        }
    }

    public static void print(Context host, String className, String tag, String message,  int level) {
        Log.d("print", "Printing");
        if (level <= DEBUG_LEVEL) {
            String logTag = className + "." + tag;
            Log.d(logTag, message);
            showToast(host, message);
        }
    }

    public static void loadDebug(Context host) {
        DEBUG_LEVEL = Integer.parseInt(host.getResources().getString(R.string.debugLevel));
        print("This", "Level", String.valueOf(DEBUG_LEVEL), 1);
    }

    private static void showToast(Context host, String message) {
        Toast toast = Toast.makeText(host, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void toastThis(Context host, String message) {
        Toast.makeText(host, message, Toast.LENGTH_SHORT).show();
    }
}

