package ru.itsschoolsamsung.budget;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

public class SpentListActivity extends Activity {
    private Intent intSpent;
    private boolean flag = false;
    private  static ArrayList<String> arrayBudget;
    private ArrayAdapter<String> stringArrayAdapter;
    private ListView listBudget;
    private static long budgetID;
    private static DatabaseBudget databaseBudget;
    private static SQLiteDatabase sqLiteDatabase;
    private String product = null;
    private String date = null;
    private int sum = 0;

    @Override
    protected void onResume() {
        super.onResume();
        refreshSpent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spent_list);

        databaseBudget = new DatabaseBudget(this);
        sqLiteDatabase = databaseBudget.getWritableDatabase();

        intSpent = new Intent(SpentListActivity.this, AddSpentActivity.class);

        listBudget = (ListView)findViewById(R.id.list_spent);
        final FloatingActionButton fab_spent = (FloatingActionButton) findViewById(R.id.fab_spent);


        fab_spent.attachToListView(listBudget);

        refreshSpent();

        listBudget.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getAdapter().getItem(position).toString();
                budgetID = getIDContact(text);
                setNames();
                Toast.makeText(SpentListActivity.this, getResources().getString(R.string.date_spent) + " "
                        + date + "\n" + getResources().getString(R.string.sum_spent) + " " + sum + " "
                        + getResources().getString(R.string.rubles), Toast.LENGTH_LONG).show();
                showPopupMenu(view);
            }
        });

        View.OnClickListener btnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.fab_spent:
                        flag = true;
                        intSpent.putExtra("key1",flag);
                        startActivity(intSpent);
                        break;
                }
            }
        };
        fab_spent.setOnClickListener(btnClick);


    }




    public static void addSpent(String date, String product, int sum){
        //вставка записи в таблицу
        String insertQuery = "INSERT INTO Spent (spent_Date, spent_Product, spent_Sum) VALUES " +
                "('"+date+"', '"+product+"', '"+sum+"');";
        sqLiteDatabase.execSQL(insertQuery);
    }


    //удаление контакта
    private void delSpent(){
        String queryDelete = "DELETE FROM Spent WHERE _id = "+budgetID+";";
        sqLiteDatabase.execSQL(queryDelete);
        refreshSpent();
    }

    public static void editSpent(String date, String product, int sum){
        String queryUpdate = "UPDATE Spent SET spent_Date = '"+date+"', " +
                "spent_Product = '"+product+"', " +
                "spent_Sum = '"+sum+"' " +
                "WHERE _id = "+budgetID+";";
        sqLiteDatabase.execSQL(queryUpdate);
    }
    private void showPopupMenu(View v){
        PopupMenu popupMenu = new PopupMenu(this,v);
        popupMenu.inflate(R.menu.popupmenu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    //редактирование записи
                    case R.id.menu_edit:
                        flag = false;
                        intSpent.putExtra("key1",flag);
                        intSpent.putExtra("key2",product);
                        intSpent.putExtra("key5",date);
                        intSpent.putExtra("key6",sum);
                        startActivity(intSpent);
                        return  true;
                    //удаление записи
                    case R.id.menu_delete:
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SpentListActivity.this);
                        alertDialog.setTitle(R.string.confirm);
                        alertDialog.setMessage(R.string.confirm_question_spent);
                        alertDialog.setIcon(R.drawable.kitin);
                        alertDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                delSpent();
                            }
                        });
                        alertDialog.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                        alertDialog.show();
                        return  true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                refreshSpent();
            }
        });
        popupMenu.show();
    }

    private void refreshSpent(){
        //обновление набора данных
        arrayBudget = new ArrayList<>();
        String selectQuery = "SELECT spent_Product FROM Spent ORDER BY spent_Date;";
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        while (cursor.moveToNext()){
            String field = cursor.getString(cursor.getColumnIndex("spent_Product"));
            arrayBudget.add(field);
        }
        stringArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayBudget);
        listBudget.setAdapter(stringArrayAdapter);
        cursor.close();
    }

    private long getIDContact(String text){
        String query = "SELECT _id FROM Spent WHERE spent_Product LIKE '"+text+"';";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        cursor.moveToFirst();
        long id = cursor.getLong(cursor.getColumnIndex("_id"));
        cursor.close();
        return id;
    }

    //назначение фамилии + имени + отчества
    private void setNames(){
        String query = "SELECT spent_Date, spent_Product, spent_Sum FROM Spent WHERE _id = "+budgetID+";";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        cursor.moveToFirst();
        date = cursor.getString(cursor.getColumnIndex("spent_Date"));
        product = cursor.getString(cursor.getColumnIndex("spent_Product"));
        sum = cursor.getInt(cursor.getColumnIndex("spent_Sum"));
        cursor.close();
    }
}
