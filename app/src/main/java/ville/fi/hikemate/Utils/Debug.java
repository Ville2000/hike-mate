package ville.fi.hikemate.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import ville.fi.hikemate.R;

/**
 * Debug is a helper class for debugging the app.
 *
 * Debug toasts debug messages to make the debugging of the app easier.
 *
 * @author      Ville Haapavaara
 * @version     10.5.2017
 * @since       1.8
 */
public class Debug {

    /**
     * Debug level.
     */
    private static int DEBUG_LEVEL;

    /**
     * Prints a debug message to a log.
     *
     * @param className     name of the class the message is from
     * @param tag           tag of the message
     * @param message       message of the debug message
     * @param level         level of the message
     */
    public static void print(String className, String tag, String message,
                             int level) {
        if (level <= DEBUG_LEVEL) {
            String logTag = className + "." + tag;
            Log.d(logTag, message);
        }
    }

    /**
     * Prints a more detailed debug message to a log.
     *
     * @param host          host of the class the message is from
     * @param className     name of the class the message is from
     * @param tag           tag of the message
     * @param message       message of the debug message
     * @param level         level of the message
     */
    public static void print(Context host, String className, String tag,
                             String message,  int level) {
        Log.d("print", "Printing");

        if (level <= DEBUG_LEVEL) {
            String logTag = className + "." + tag;
            Log.d(logTag, message);
            showToast(host, message);
        }
    }

    /**
     * Sets up the debug level.
     *
     * @param host  host of the request
     */
    public static void loadDebug(Context host) {
        DEBUG_LEVEL = Integer.parseInt(host.getResources()
                .getString(R.string.debugLevel));
        print("This", "Level", String.valueOf(DEBUG_LEVEL), 1);
    }

    /**
     * Shows a toast of the debug message.
     *
     * @param host      host of the request
     * @param message   message of the debug message
     */
    private static void showToast(Context host, String message) {
        Toast toast = Toast.makeText(host, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Toasts a string.
     *
     * @param host      host of the request
     * @param message   message of the toast
     */
    public static void toastThis(Context host, String message) {
        Toast.makeText(host, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Toasts a string longer.
     *
     * @param host      host of the request
     * @param message   message of the toast
     */
    public static void toastThisLonger(Context host, String message) {
        Toast.makeText(host, message, Toast.LENGTH_LONG).show();
    }
}
