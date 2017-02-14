package com.hskj.damnicomniplusvic.sturxdemo.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hskj.damnicomniplusvic.sturxdemo.bean.ImageTextBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by DAMNICOMNIPLUSVIC on 2017/2/10.
 * (c) 2017 DAMNICOMNIPLUSVIC Inc,All Rights Reserved.
 */

public class Database {
    private static String DATA_FILE_NAME="data.db";
    private static Database INSTANCE;
    static File dataFile =new File(AppRoot.getINSTANCE().getFilesDir(),DATA_FILE_NAME);
    Gson gson=new Gson();
    private Database(){}
    public static Database getINSTANCE()
    {
        if(INSTANCE==null)
        {
            Log.i(TAG, "getINSTANCE: "+AppRoot.getINSTANCE().getFilesDir().getPath());
            INSTANCE=new Database();
            Log.i(TAG, "getINSTANCE: "+dataFile.getPath());
        }
        return INSTANCE;
    }

    public List<ImageTextBean> readItems()
    {
        // Hard code adding some delay, to distinguish reading from memory and reading disk clearly
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Reader reader = new FileReader(dataFile);
            return gson.fromJson(reader, new TypeToken<List<ImageTextBean>>(){}.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void writeItems(List<ImageTextBean> list)
    {
        String json = gson.toJson(list);
        try {
            if (!dataFile.exists()) {
                try {
                    dataFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Writer writer = new FileWriter(dataFile);
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void delete()
    {
        dataFile.delete();
    }
}
