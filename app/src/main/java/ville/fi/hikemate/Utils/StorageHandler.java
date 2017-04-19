package ville.fi.hikemate.Utils;

import android.content.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import ville.fi.hikemate.Resources.Hike;
import ville.fi.hikemate.Resources.HikeList;

/**
 * Created by Ville on 10.4.2017.
 */

public class StorageHandler {

    ObjectMapper mapper = new ObjectMapper();

    public HikeList readStorage(Context host) {
        StringBuilder sb = new StringBuilder();

        try {
            FileInputStream fis = host.openFileInput("hikes.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            System.out.println("Read success.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HikeList hikes = null;

        try {
            hikes = mapper.readValue(sb.toString(), HikeList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return hikes;
    }

    public void writeStorage(Context host, HikeList hikes) {
        String json = "";
        ObjectMapper mapper = new ObjectMapper();

        try {
            json += mapper.writeValueAsString(hikes);
            FileOutputStream outputStream = host.openFileOutput("hikes.txt", Context.MODE_PRIVATE);
            outputStream.write(json.getBytes());
            outputStream.close();
            System.out.println("Write success.");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
