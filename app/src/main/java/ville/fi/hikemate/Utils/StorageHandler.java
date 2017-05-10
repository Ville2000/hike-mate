package ville.fi.hikemate.Utils;

import android.content.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import ville.fi.hikemate.Resources.Hike;

/**
 * StorageHandler handles the external storage operations.
 *
 * @author      Ville Haapavaara
 * @version     10.5.2017
 * @since       1.8
 */
public class StorageHandler {

    /**
     * Mapper for the reading and writing.
     */
    ObjectMapper mapper = new ObjectMapper();

    /**
     * Returns a list of hikes.
     *
     * Tries to read a 'hikes.txt' named file to a string. The mapper then
     * converts this string to a linked list object that's passed to the
     * caller.
     *
     * @param host  host of the request
     * @return      a list of hikes
     */
    public LinkedList<Hike> readStorage(Context host) {
        StringBuilder sb = new StringBuilder();

        try {
            FileInputStream fis = host.openFileInput("hikes.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LinkedList<Hike> hikes = new LinkedList<>();

        try {
            hikes = mapper.readValue(sb.toString(), new TypeReference<LinkedList<Hike>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return hikes;
    }

    /**
     * Writes a list of hikes to a file.
     *
     * Converts a list of hikes to a string and then writes them to a
     * 'hikes.txt' file.
     *
     * @param host  host of the request
     * @param hikes hikes to be saved
     */
    public void writeStorage(Context host, LinkedList<Hike> hikes) {
        String json = "";
        ObjectMapper mapper = new ObjectMapper();

        try {
            json += mapper.writeValueAsString(hikes);
            FileOutputStream outputStream = host.openFileOutput("hikes.txt", Context.MODE_PRIVATE);
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
