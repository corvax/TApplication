package com.example.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vitaly on 29/11/13.
 */
public final class NotesDataContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public NotesDataContract(){
    }

    /* Columns: Inner class that defines the table contents */
    public static abstract class NotesEntry implements BaseColumns {
        public static final String TABLE_NAME = "note";
        public static final String COLUMN_NAME_NOTE_ID = "note_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_TEXT = "text";
        public static final String COLUMN_NAME_LAST_MODIFIED = "last_modified";
    }

    /* Types and other constants */
    private static final String TYPE_INTEGER = "INTEGER";
    private static final String TYPE_TEXT = "TEXT";
    private static final String TYPE_DATETIME = "DATETIME";
    // column separator
    private static final String COLUMN_SEP = ",";
    // SQL DML Quries
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + NotesEntry.TABLE_NAME + "("+
                    NotesEntry._ID +                 TYPE_INTEGER + " PRIMARY KEY" + COLUMN_SEP +
                    NotesEntry.COLUMN_NAME_NOTE_ID + TYPE_TEXT +                     COLUMN_SEP +
                    NotesEntry.COLUMN_NAME_TITLE +   TYPE_TEXT +                     COLUMN_SEP +
                    NotesEntry.COLUMN_NAME_TEXT +    TYPE_TEXT +                     COLUMN_SEP +
                    NotesEntry.COLUMN_NAME_LAST_MODIFIED + TYPE_TEXT + ")";
    private static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + NotesEntry.TABLE_NAME;

    /* Database interface class */
    public class NotesDbHelper extends SQLiteOpenHelper {
        // Database version info. If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Notes.db";

        // create an instance of the class
        public NotesDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // create the database objects
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_TABLE);
        }

        // we need to implement this method
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // this is a test temp data, just drop the table and create a new one
            //db.execSQL(SQL_DROP_TABLE);
            //onCreate(db);
        }

        // we are not required to implement this, but good practice to do so
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
            // do not downgrade
        }
    }

}
