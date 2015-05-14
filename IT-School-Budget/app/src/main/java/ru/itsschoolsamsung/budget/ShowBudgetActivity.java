package ru.itsschoolsamsung.budget;


import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class ShowBudgetActivity extends Activity{


    private static SQLiteDatabase sqLiteDatabase;
    private static DatabaseBudget databaseBudget;
    private static ArrayList<Integer> arrayBudget;
    private static ArrayList<Integer> arrayBudget2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_budget);

        databaseBudget = new DatabaseBudget(this);
        sqLiteDatabase = databaseBudget.getReadableDatabase();

        final TextView textViewShow = (TextView)findViewById(R.id.cur_bud);

        arrayBudget = new ArrayList<>();
        String selectQuery = "SELECT spent_Sum FROM Spent;";
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        while (cursor.moveToNext()){
            int field = cursor.getInt(cursor.getColumnIndex("spent_Sum"));
            arrayBudget.add(field);
        }
        cursor.close();

        int[] spentArray = convertIntegers(arrayBudget);
        long spent_sum = 0;
        for(int i=0; i<spentArray.length; i++) {
            spent_sum+=spentArray[i];
        }

        arrayBudget2 = new ArrayList<>();
        String selectQuery2 = "SELECT budget_Sum FROM Budget;";
        Cursor cursor2 = sqLiteDatabase.rawQuery(selectQuery2, null);
        while (cursor2.moveToNext()){
            int field = cursor.getInt(cursor.getColumnIndex("budget_Sum"));
            arrayBudget2.add(field);
        }
        cursor2.close();

        int[] paymentArray = convertIntegers(arrayBudget2);
        long payments_sum = 0;
        for(int i=0; i<paymentArray.length; i++) {
            payments_sum+=paymentArray[i];
        }


        textViewShow.setText(Long.toString(spent_sum - payments_sum));
    }

    public static int[] convertIntegers(ArrayList<Integer> integers){
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i=0; i<ret.length; i++){
            ret[i] = iterator.next();
        }
        return ret;
    }
}
