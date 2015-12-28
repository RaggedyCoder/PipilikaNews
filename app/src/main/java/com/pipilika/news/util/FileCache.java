package com.pipilika.news.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FileCache {

    private File cacheDir;

    public FileCache(Context context) {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(Constants.IMAGE_CACHE_PATH, "cache");
        } else {
            cacheDir = context.getCacheDir();
        }

        if (!cacheDir.exists()) {
            makeDirectories();
        }
    }

    private boolean makeDirectories() {
        return cacheDir.mkdirs();
    }

    private boolean deleteFile(File file) {
        return file.delete();
    }

    public File getFile(String url) {
        return new File(cacheDir, String.valueOf(url.hashCode()));
    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            deleteFile(file);

        }
    }
}