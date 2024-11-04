package com.example.project_kpi_27_09_24.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface AppConstants {

   Path  mainPath= Paths.get("storage");
   Path tmPath = Paths.get(mainPath + "/tm");


   String DEFAULT_PAGE_NUMBER = "0";
   String DEFAULT_PAGE_SIZE = "20";
   int MAX_PAGE_SIZE = 30;
   List<String> openUrl= new ArrayList<>(
           Arrays.asList("/api/auth/login","/api/auth/refreshtoken"));
}
