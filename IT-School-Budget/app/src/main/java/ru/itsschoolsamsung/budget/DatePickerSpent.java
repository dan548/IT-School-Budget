package ru.itsschoolsamsung.budget;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class DatePickerSpent extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    @Override
    public void onStart() {
        super.onStart();
        Button nButton =  ((AlertDialog) getDialog())
                .getButton(DialogInterface.BUTTON_POSITIVE);
        nButton.setText(getResources().getString(R.string.OK));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DATE);

        Dialog picker = new DatePickerDialog(getActivity(), this, year, month, day);
        picker.setTitle(getResources().getString(R.string.choose_date));
        return picker;
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int day) {
        TextView tv = (TextView) getActivity().findViewById(R.id.spent_date_yo);
        month += 1;
        if (month < 10) {
            tv.setText(day + ".0" + month + "." + year);
        } else {
            tv.setText(day + "." + month + "." + year);
        }
    }
}
