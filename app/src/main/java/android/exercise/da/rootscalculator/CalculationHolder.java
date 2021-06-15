package android.exercise.da.rootscalculator;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CalculationHolder extends RecyclerView.ViewHolder {
    TextView textView;
    Button deleteButton;
    FloatingActionButton cancelCalc;

    public CalculationHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.descriptionText);
        deleteButton = itemView.findViewById(R.id.buttonDeleteItem);
        cancelCalc = itemView.findViewById(R.id.buttonCancelCalculation);
    }
}
