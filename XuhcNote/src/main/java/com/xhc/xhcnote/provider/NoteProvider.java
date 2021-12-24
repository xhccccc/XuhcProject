package com.xhc.xhcnote.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.xhc.xhcnote.tool.NoteSQLite;
import com.xhc.xhcnote.tool.UtilDB;

public class NoteProvider extends ContentProvider {

    public static final int UserInfo = 0;
    public static final String AUTHORITY = "com.xhc.xhcnote.provider";
    private static UriMatcher sUriMatcher;

    private NoteSQLite mNoteSQLite;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY,"UserInfo",UserInfo);
    }

    public NoteProvider() {
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        mNoteSQLite = new NoteSQLite(getContext(), UtilDB.DATABASE_NAME,null,UtilDB.DATABASE_VERION);
        return false;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        switch (sUriMatcher.match(uri)){
            case UserInfo:
                return "vnd.android.cursor.dir/vnd.com.xhc.xhcnote.provider.UserInfo";
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }



    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteDatabase db = mNoteSQLite.getReadableDatabase();
        Cursor cursor = null;
        switch (sUriMatcher.match(uri)){
            case UserInfo:
                cursor = db.query("UserInfo",projection,selection,selectionArgs,null,null,sortOrder);
            default:
                break;

        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
