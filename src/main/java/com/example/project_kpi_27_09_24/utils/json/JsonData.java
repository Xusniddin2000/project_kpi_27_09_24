package com.example.project_kpi_27_09_24.utils.json;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonData {

    public static String getDataStream(URL url) throws IOException {
        InputStream input = url.openStream();
        InputStreamReader inpSReader = new InputStreamReader(input);
        BufferedReader bufReader = new BufferedReader(inpSReader);
        StringBuilder json = new StringBuilder();
        int c;
        while ((c = bufReader.read()) != -1) {
            json.append((char) c);
        }
        return json.toString();
    }

    public static String getJSONObject(String url) throws IOException {
        if (!StringUtils.hasText(url)) {
            return null;
        }
        URL newUrl=new URL(url);
        try (InputStream inputStream = newUrl.openStream()) {
            return IOUtils.toString(inputStream, String.valueOf(Charset.forName("UTF-8")));
        } catch (IOException e) {
            // Log the exception or handle it as needed
            throw new IOException("Error fetching JSON from URL: " + url, e);
        }
    }

    public static String getByteFileFromURL(String s1) throws IOException {
        InputStream inputStream = new URL(s1).openStream();
        ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
        FileOutputStream fileOutputStream = new FileOutputStream("rasm");
        FileChannel fileChannel = fileOutputStream.getChannel();
        fileOutputStream.getChannel()
                .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        return "Bu file ko'chirildi..!!>>";
    }

    public static List<AddressModel> getListJson(String path, AddressModel model) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
           throw  new FileNotFoundException();
                     }
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new FileReader(file));
        AddressModel[] models = gson.fromJson(jsonReader, AddressModel[].class);
        List<AddressModel> modelList = new ArrayList<>(Arrays.asList(models));
        return modelList;
    }

    public static AddressModel getModelJson(String path) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new FileReader(file));
        return gson.fromJson(jsonReader, AddressModel.class);
    }

}
