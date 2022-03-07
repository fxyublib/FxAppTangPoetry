package com.fxyublib.android.FxAppTangPoetry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DataBaseHelper";
    //The Android's default system path of your application database.
    private static String DB_PATH = "";
    private String DB_NAME = "xxtsdb.db";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    /**
     * 获取app缓存路径
     * @param context
     * @return
     */
    public String getCachePath( Context context ){
        String cachePath ;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            //外部存储可用
            cachePath = context.getExternalCacheDir().getPath() ;
        }else {
            //外部存储不可用
            cachePath = context.getCacheDir().getPath() ;
        }
        return cachePath ;
    }

    //判断文件是否存在
    public boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public DataBaseHelper(Context context, String dbname) {
        super(context, dbname, null, 1);
        this.myContext = context;
        this.DB_NAME = dbname;

        ArrayList dirs = new ArrayList();

        dirs.add(myContext.getExternalCacheDir().getPath()); // /storage/emulated/0/Android/data/com.fxyublib.android.FxTangPoetry/cache
        dirs.add(myContext.getExternalFilesDir("test").getPath()); // /storage/emulated/0/Android/data/com.fxyublib.android.FxTangPoetry/files/test

        dirs.add(myContext.getCacheDir().getPath()); // /data/user/0/com.fxyublib.android.FxTangPoetry/cache
        dirs.add(myContext.getApplicationInfo().dataDir ); // /data/user/0/com.fxyublib.android.FxTangPoetry

        dirs.add(myContext.getDatabasePath("test").getPath()); // /data/user/0/com.fxyublib.android.FxTangPoetry/databases/test
        dirs.add(myContext.getFilesDir().getPath()); // /data/user/0/com.fxyublib.android.FxTangPoetry/files
        dirs.add(myContext.getPackageResourcePath()); // /data/app/com.fxyublib.android.FxTangPoetry-1/base.apk
        dirs.add(myContext.getPackageCodePath()); // /data/app/com.fxyublib.android.FxTangPoetry-1/base.apk
        dirs.add(myContext.getDir("test", Context.MODE_PRIVATE).getPath()); // /data/user/0/com.fxyublib.android.FxTangPoetry/app_test

        dirs.add(Environment.getDataDirectory()); // /data
        dirs.add(Environment.getDownloadCacheDirectory()); // /cache
        dirs.add(Environment.getExternalStorageDirectory()); // /storage/emulated/0
        dirs.add(Environment.getExternalStoragePublicDirectory("test")); // /storage/emulated/0/test
        dirs.add(Environment.getRootDirectory()); // /system

        for (int i = 0; i < dirs.size(); i++) {
            Log.i(TAG, "dir: " + dirs.get(i) );
        }

        DB_PATH = dirs.get(0).toString() + "/";
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            //do nothing - database already exist
            Log.i(TAG, "createDataBase: exist" );
        } else {
            Log.i(TAG, "createDataBase: not exist" );
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            copyDataBase();
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        String myPath = DB_PATH + DB_NAME;
        Log.i(TAG, "checkDataBase: " + myPath );

        if(!fileIsExists(myPath))
            return false;

        try {
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            //database does't exist yet.
            Log.i(TAG, "checkDataBase: error=" + e.getMessage());
        }

        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {
        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;
        Log.i(TAG, "copyDataBase: " + outFileName );

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public SQLiteDatabase openDataBase(String myPath) throws SQLException {
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        return myDataBase;
    }

    public SQLiteDatabase getDataBase() {
        try {
            createDataBase();

            String myPath = DB_PATH + DB_NAME;
            openDataBase(myPath);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return myDataBase;
    }

    public SQLiteDatabase getDataBasePtr() {
        myDataBase = this.getReadableDatabase();
        myDataBase = this.getDataBase();
        return myDataBase;
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();

        Log.i(TAG, "close: OK ");
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate: OK ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onUpgrade: OK ");
    }

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

}
