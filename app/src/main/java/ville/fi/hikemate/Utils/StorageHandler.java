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
import java.util.List;

import ville.fi.hikemate.Resources.Hike;
import ville.fi.hikemate.Resources.HikeList;

/**
 * Created by Ville on 10.4.2017.
 */

public class StorageHandler {

    ObjectMapper mapper = new ObjectMapper();

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
