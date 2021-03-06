package com.direv.direv.Utils;

import android.Manifest;

/**
 * Created by khinthein on 11/30/17.
 */

public class Permissions {
    public static final String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    public static final String[] CAMERA_PERMISSION = {
            Manifest.permission.CAMERA
    };
    public static final String[] WRITE_STORAGE_PERMISSION = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static final String[] READ_STORAGE_PERMISSION = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
}
