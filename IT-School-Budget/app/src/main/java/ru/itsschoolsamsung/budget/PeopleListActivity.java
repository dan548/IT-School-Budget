package ru.itsschoolsamsung.budget;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

public class PeopleListActivity extends Activity {

    private Intent intBud;
    private Intent intTrans;
    private Intent intChoose;
    private boolean flag = false;
    private  static ArrayList<String> arrayBudget;
    private  ArrayAdapter<String> stringArrayAdapter;
    private  ListView listBudget;
    private static long budgetID;
    private static DatabaseBudget databaseBudget;
    private static SQLiteDatabase sqLiteDatabase;
    private String surname = null;
    private String name = null;
    private String last_name = null;
    private int day = 0;
    private int month = 0;
    private int year = 0;
    private int sum = 0;
    private String phone_one;
    private String phone_two;


    @Override
    protected void onResume() {
        super.onResume();
        refreshContact();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_list);

        databaseBudget = new DatabaseBudget(this);
        sqLiteDatabase = databaseBudget.getWritableDatabase();

        intBud = new Intent(PeopleListActivity.this, AddEditActivity.class);
        intTrans = new Intent(PeopleListActivity.this, AddPaymentActivity.class);
        intChoose = new Intent(PeopleListActivity.this, ChooseNumberActivity.class);

        listBudget = (ListView)findViewById(R.id.list_people);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.attachToListView(listBudget);

        refreshContact();

        listBudget.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getAdapter().getItem(position).toString();
                budgetID = getIDContact(text);
                showPopupMenu(view);
            }
        });

        View.OnClickListener btnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.fab:
                        flag = true;
                        intBud.putExtra("key1",flag);
                        startActivity(intBud);
                        break;
                }
            }
        };
        fab.setOnClickListener(btnClick);


    }




    public static void addArrayContact(String surname, String name, String last_name, int day, int month, int year, int sum, String phone_one, String phone_two){
        //вставка записи в таблицу
        String insertQuery = "INSERT INTO Budget (budget_Surname, budget_Name, budget_Last_name, budget_Last_payment_day, budget_Last_payment_month, " +
                "budget_Last_payment_year, budget_Phone_one, budget_Phone_two, budget_Sum) VALUES " +
                "('"+surname+"', '"+name+"', '"+last_name+"', '"+day+"', '"+month+"', '"+year+"', '"+phone_one+"', '"+phone_two+"', '"+sum+"');";
        sqLiteDatabase.execSQL(insertQuery);
    }

    public static void addPayment(int day, int month, int year, int sum){


        String query = "SELECT budget_Sum FROM Budget WHERE _id LIKE '"+budgetID+"';";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        int money = cursor.getInt(cursor.getColumnIndex("budget_Sum"));
        cursor.close();


        sum += money;

        String queryUpdate = "UPDATE Budget SET budget_Last_payment_day = '"+day+"', " +
                "budget_Last_payment_month = '"+month+"', " +
                "budget_Last_payment_year = '"+year+"', " +
                "budget_Sum = '"+sum+"' WHERE _id = "+budgetID+";";
        sqLiteDatabase.execSQL(queryUpdate);
    }

    //удаление контакта
    private void delContact(){
        String queryDelete = "DELETE FROM Budget WHERE _id = "+budgetID+";";
        sqLiteDatabase.execSQL(queryDelete);
        refreshContact();
    }

    public static void editContact (String s_name, String n_name, String l_name, String p1, String p2){
        String queryUpdate = "UPDATE Budget SET budget_Surname = '"+s_name+"', " +
                "budget_Name = '"+n_name+"', " +
                "budget_Last_name = '"+l_name+"', " +
                "budget_Phone_one = '"+p1+"', " +
                "budget_Phone_two = '"+p2+"' " +
                "WHERE _id = "+budgetID+";";
        sqLiteDatabase.execSQL(queryUpdate);
    }
    private void showPopupMenu(View v){
        PopupMenu popupMenu = new PopupMenu(this,v);
        popupMenu.inflate(R.menu.popupmenu_add_pay);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    //редактирование записи
                    case R.id.menu1:
                        setNames();
                        flag = false;
                        intBud.putExtra("key1", flag);
                        intBud.putExtra("key2", surname);
                        intBud.putExtra("key3", name);
                        intBud.putExtra("key4", last_name);
                        intBud.putExtra("key5", day);
                        intBud.putExtra("key6", sum);
                        intBud.putExtra("key7", month);
                        intBud.putExtra("key8", year);
                        intBud.putExtra("key9", phone_one);
                        intBud.putExtra("key10", phone_two);
                        startActivity(intBud);
                        return  true;
                    //добавление записи
                    case R.id.menu2:
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PeopleListActivity.this);
                        alertDialog.setTitle(R.string.confirm);
                        alertDialog.setMessage(R.string.confirm_question);
                        alertDialog.setIcon(R.drawable.kitin);
                        alertDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                delContact();
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
                    case R.id.menu3:
                        intTrans.putExtra("key_id", budgetID);
                        startActivity(intTrans);
                        return true;
                    case R.id.menu4:
                        setNumbers();
                        if (!phone_two.equals("+7")) {
                        intChoose.putExtra("phone1", phone_one);
                        intChoose.putExtra("phone2", phone_two);
                        startActivity(intChoose);
                        }
                        else {
                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone_one)));
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                refreshContact();
            }
        });
        popupMenu.show();
    }

    private void refreshContact(){
        //обновление набора данных
        arrayBudget = new ArrayList<>();
        String selectQuery = "SELECT budget_Surname, budget_Name, budget_Last_name FROM Budget ORDER BY budget_Surname;";
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        while (cursor.moveToNext()){
            String field = cursor.getString(cursor.getColumnIndex("budget_Surname"))+" "
                    +cursor.getString(cursor.getColumnIndex("budget_Name"))+" "
                    +cursor.getString(cursor.getColumnIndex("budget_Last_name"));
            arrayBudget.add(field);
        }
        stringArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayBudget);
        listBudget.setAdapter(stringArrayAdapter);
        cursor.close();
    }

    private long getIDContact(String text){
        String query = "SELECT _id FROM Budget WHERE budget_Surname || ' ' || budget_Name || ' ' || budget_Last_name LIKE '"+text+"';";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        cursor.moveToFirst();
        long id = cursor.getLong(cursor.getColumnIndex("_id"));
        cursor.close();
        return id;
    }

    //назначение фамилии + имени + отчества
    private void setNames(){
        String query = "SELECT budget_Surname, budget_Name, budget_Last_name, budget_Last_payment_day, " +
                "budget_Last_payment_month, budget_Last_payment_year, budget_Phone_one, budget_Phone_two, budget_Sum FROM Budget WHERE _id = "+budgetID+";";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        cursor.moveToFirst();
        surname = cursor.getString(cursor.getColumnIndex("budget_Surname"));
        name = cursor.getString(cursor.getColumnIndex("budget_Name"));
        last_name = cursor.getString(cursor.getColumnIndex("budget_Last_name"));
        day = cursor.getInt(cursor.getColumnIndex("budget_Last_payment_day"));
        month = cursor.getInt(cursor.getColumnIndex("budget_Last_payment_month"));
        year = cursor.getInt(cursor.getColumnIndex("budget_Last_payment_year"));
        sum = cursor.getInt(cursor.getColumnIndex("budget_Sum"));
        phone_one = cursor.getString(cursor.getColumnIndex("budget_Phone_one"));
        phone_two = cursor.getString(cursor.getColumnIndex("budget_Phone_two"));
        cursor.close();
    }

    private void setNumbers(){
        String query = "SELECT budget_Phone_one, budget_Phone_two FROM Budget WHERE _id = "+budgetID+";";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        cursor.moveToFirst();
        phone_one = cursor.getString(cursor.getColumnIndex("budget_Phone_one"));
        phone_two = cursor.getString(cursor.getColumnIndex("budget_Phone_two"));
        cursor.close();
    }
}
