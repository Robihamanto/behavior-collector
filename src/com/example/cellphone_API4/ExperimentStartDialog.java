package com.example.cellphone_API4;

import com.example.cellphone_API4.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

public class ExperimentStartDialog extends DialogFragment {

	private HorizontialListView myList;

	private int position;

	public ExperimentStartDialog(HorizontialListView horizontialListView, int position) {
		myList = horizontialListView;
		this.position = position;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(inflater.inflate(R.layout.dialog_tempstop, null))
				.setMessage("\n\n\n\n\n\n\n\n����}�l\n\n\n\n\n\n\n\n")
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
							myList.setSelection(position);
						dialog.cancel();
					}
				});
		// Create the AlertDialog object and return it
		return builder.create();
	}
}