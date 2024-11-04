package com.example.project_kpi_27_09_24.utils;

import org.springframework.util.StringUtils;

import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class AppUtils {

    public static String getDateTime() {
        SimpleDateFormat dtf = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss");
        Date date = new Date();
        return dtf.format(date);
    }


    //get time and date
    public static String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return sdf.format(date.getTime());
    }
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return sdf.format(date.getTime());
    }

    public static String getFileExt(String fileName) {
        if (StringUtils.hasText(fileName)) {

            //  return fileName.substring(fileName.lastIndexOf("."+1));
            return fileName.split("\\.")[1];
        }
        return null;
    }

    public static Validator getValidator() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        return validator;
    }

   /* public static JsonObject dataResponse(String data) {

        JsonObject jsonElement = (JsonObject) JsonParser.parseString(data);
        JsonObject jsonObject = jsonElement.getAsJsonObject("object");
        return jsonObject;
    }*/


    public  static  String  getEnv(String key)   throws IOException {

        Properties   properties=new Properties();
        InputStream   inputStream=
                 AppUtils.class.getClassLoader().getResourceAsStream("application.properties");
        properties.load(inputStream);
        String   value=(String)  properties.get(key);
        if(!value.isEmpty()){
            return  value;
        }
            return null;

    }
}

