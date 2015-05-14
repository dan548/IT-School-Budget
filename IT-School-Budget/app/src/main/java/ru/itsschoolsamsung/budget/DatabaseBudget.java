package ru.itsschoolsamsung.budget;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseBudget extends SQLiteOpenHelper{


    private static final String DATABASE_NAME = "budget_base.db";
    private static final int DATABASE_VERSION = 1;

    @Override
    public void onCreate(SQLiteDatabase db) {


        String createBudgetQuery = "CREATE table Budget (_id integer primary key autoincrement, " +
                "budget_Surname varchar(32), budget_Name varchar(32), " +
                "budget_Last_name varchar(32), budget_Last_payment_day integer, " +
                "budget_Last_payment_month integer, " +
                "budget_Last_payment_year integer , " +
                "budget_Phone_one varchar(32), " +
                "budget_Phone_two varchar(32), " +
                "budget_Sum integer);";
        db.execSQL(createBudgetQuery);

        String createSpentQuery = "CREATE table Spent (_id integer primary key autoincrement, " +
                "spent_Date varchar(32), spent_Product varchar(32), " +
                "spent_Sum integer);";
        db.execSQL(createSpentQuery);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String dropQuery = "DROP TABLE IF EXISTS Budget;" +
                            "DROP TABLE IF EXISTS Spent;";

    }

    public DatabaseBudget(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
