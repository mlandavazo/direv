package com.direv.direv.Utils;

import com.nostra13.universalimageloader.core.assist.FailReason;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by khinthein on 12/5/17.
 */

public class FileSearch {
    /**
     * Search a directory and return a list of all directories contained inside
     * @param directory
     * @return
     */
    public static ArrayList<String> getDirectoryPaths(String directory){
        ArrayList <String> pathArray = new ArrayList<>();
        File file = new File(directory);
        File[] listfiles = file.listFiles();
        for(int i = 0; i < listfiles.length; i++){
            if(listfiles[i].isDirectory()){
                pathArray.add(listfiles[i].getAbsolutePath());
            }
        }
        return pathArray;
    }
    public static ArrayList<String> getFilePaths(String directory){
        /**
         * Search directory and return a list of all files contained inside
         * @param directory
         * @return
         */

        System.out.println("**********************************************");
        System.out.println(directory);

        ArrayList<String> pathArray = new ArrayList<>();
        File file = new File(directory);
        File[] listfiles = file.listFiles();


        try {
            for(int i = 0; i < listfiles.length; i++){
                if(listfiles[i].isFile()){
                    pathArray.add(listfiles[i].getAbsolutePath());
                }
            }
        } catch (RuntimeException e) {
            System.out.println("******************** Caught Error **************************");
        }

        return pathArray;
    }
}
