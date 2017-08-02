package com.kkk8888.bdosimulator;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MyDbHelper extends SQLiteOpenHelper {

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static final String DATABASE_NAME = "database/data.sqlite";
    private static final String PACKAGE_DIR = "data/data/com.kkk8888.bdosimulator/databases";

    public MyDbHelper(Context context) {
        super(context, PACKAGE_DIR + "/" + DATABASE_NAME, null, 1);
        initialize(context);
        Toast.makeText(context, "메롱", Toast.LENGTH_SHORT).show();

    }

    public static void initialize(Context ctx) {

        File fiel = Environment.getDataDirectory();
        Toast.makeText(ctx, fiel.getAbsolutePath(), Toast.LENGTH_SHORT).show();


        File folder = new File(fiel.getAbsolutePath()+PACKAGE_DIR);
        folder.mkdirs();

        File outfile = new File(PACKAGE_DIR + "/" + DATABASE_NAME);
        try {
            OutputStream os = new FileOutputStream(outfile);
            Toast.makeText(ctx, "ㅇㅇ", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(ctx, "ㄴㄴ", Toast.LENGTH_SHORT).show();
        }
        
//
//        if (outfile.length() <= 0) {
//            AssetManager assetManager = ctx.getResources().getAssets();
//            try {
//                ctx.openFileOutput(DATABASE_NAME,)
//                InputStream is = assetManager.open(DATABASE_NAME, AssetManager.ACCESS_BUFFER);
//                long filesize = is.available();
//                byte[] tempdata = new byte[(int) filesize];
//                is.read(tempdata);
//                is.close();
//                outfile.createNewFile();
//                FileOutputStream fo = new FileOutputStream(outfile);
//                fo.write(tempdata);
//                fo.close();
//                Toast.makeText(ctx, "파일복사완료", Toast.LENGTH_SHORT).show();
//            } catch (IOException e) {
//                e.printStackTrace();
//                Toast.makeText(ctx, "에러", Toast.LENGTH_SHORT).show();
//
//            }
//        }


    }

}
