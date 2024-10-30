package com.averin.SOAP.utilities.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    private DatePickerDialog.OnDateSetListener dateSetListener;
    DatePickerDialog datePickerDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        dateSetListener = (DatePickerDialog.OnDateSetListener) getTargetFragment();
        datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
        return datePickerDialog;
    }
}
