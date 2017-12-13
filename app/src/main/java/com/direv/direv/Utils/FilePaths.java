package com.direv.direv.Utils;

import android.os.Environment;

/**
 * Created by khinthein on 12/5/17.
 */

public class FilePaths {
    //"storage/emulated/0"
  //  public String ROOT_DIR = Environment.getExternalStorageDirectory().getPath();
    public String ROOT_DIR = Environment.getExternalStorageDirectory().getPath();
    public String PICTURES = ROOT_DIR + "/Pictures";
    public String CAMERA = ROOT_DIR + "/DCIM/Camera";
 //   public String CAMERA = "storage/emulated/DCIM/Camera";

    public String FIREBASE_IMAGE_STORAGE = "photos/users/";


}
