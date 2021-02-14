package com.wontanara.dictionnairedelanguedessignes.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipManager {
    private static final int BUFFER_SIZE = 6 * 1024;

    public static void unzip(String zipFile, String location) throws IOException {
        File f = new File(location);
        byte[] buffer = new byte[BUFFER_SIZE];
        if (!f.isDirectory()) {
            f.mkdirs();
        }

        ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFile));
        ZipEntry ze;
        while ((ze = zin.getNextEntry()) != null) {
            String path = location + File.separator + ze.getName();

            if (ze.isDirectory()) {
                File unzipFile = new File(path);
                if (!unzipFile.isDirectory()) {
                    unzipFile.mkdirs();
                }
            } else {

                FileOutputStream fout = new FileOutputStream(path, false);
                for (int c = zin.read(buffer); c != -1; c = zin.read(buffer)) {
                    fout.write(buffer, 0, c);
                }
                zin.closeEntry();
            }
        }
        zin.close();
    }
}
