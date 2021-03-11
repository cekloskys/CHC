package com.example.chc;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    // initialize constants for DB version and name
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "chc.db";

    // initialize constants for student table
    private static final String TABLE_STUDENT = "student";
    private static final String COLUMN_STUDENT_ID = "_id";
    private static final String COLUMN_STUDENT_NAME = "name";
    private static final String COLUMN_STUDENT_YEAR = "year";
    private static final String COLUMN_STUDENT_MAJOR = "major";

    /**
     * Initializes a DBHandler.  Creates version of the database.
     * @param context reference to an activity that initializes the
     *                DBHandler
     * @param cursorFactory null
     */
    public DBHandler (Context context, SQLiteDatabase.CursorFactory cursorFactory) {

        // call superclass constructor
        super(context, DATABASE_NAME, cursorFactory, DATABASE_VERSION);
    }

    /**
     * Creates database tables.
     * @param sqLiteDatabase reference to chc database
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // define create statement for student table
        String query = "CREATE TABLE " + TABLE_STUDENT + "(" +
                COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_STUDENT_NAME + " TEXT, " +
                COLUMN_STUDENT_YEAR + " TEXT, " +
                COLUMN_STUDENT_MAJOR + " TEXT" +
                ");";

        // execute create statement
        sqLiteDatabase.execSQL(query);
    }

    /**
     * Creates a new version of the database.
     * @param sqLiteDatabase reference to shopper database
     * @param oldVersion old version of database
     * @param newVersion new version of database
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        // define drop statement
        String query = "DROP TABLE IF EXISTS " + TABLE_STUDENT;

        // execute drop statement that drops student table
        sqLiteDatabase.execSQL(query);

        onCreate(sqLiteDatabase);
    }

    /**
     * This method gets called when the add button in the Action Bar of the
     * MainActivity gets clicked.  It inserts a new row in the
     * student table.
     * @param name student name
     * @param year student year
     * @param major student major
     */
    public void addStudent(String name, String year, String major) {

        // get reference to chc database
        SQLiteDatabase db = getWritableDatabase();

        // initialize a ContentValues object
        ContentValues values = new ContentValues();

        // put data into ContentValues object
        values.put(COLUMN_STUDENT_NAME, name);
        values.put(COLUMN_STUDENT_YEAR, year);
        values.put(COLUMN_STUDENT_MAJOR, major);

        // insert data in ContentValues object into student table
        db.insert(TABLE_STUDENT, null, values);

        // close database reference
        db.close();
    }

    /**
     * This method gets called when a menu-item in the overflow menu in the
     * MainActivity is selected.
     * @param major selected major (CIS, CIT, or CSM)
     * @return count of the number of students who have the selected major.
     */
    public int getCount (String major) {

        // get reference to chc database
        SQLiteDatabase db = getWritableDatabase();

        // define select statement
        String query = "SELECT * FROM " + TABLE_STUDENT +
                " WHERE " + COLUMN_STUDENT_MAJOR + " = " + "'" + major + "'";

        // execute select statement and return count of rows in Cursor
        return db.rawQuery(query, null).getCount();
    }
}
