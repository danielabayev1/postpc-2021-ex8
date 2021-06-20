package android.exercise.da.rootscalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    CalculationsHolder calculationsHolder;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (calculationsHolder == null) {
            calculationsHolder = RootsCalculatorApplication.getInstance().getCalculationsHolder();
        }

        CalculationAdapter adapter = new CalculationAdapter();

        //find views
        FloatingActionButton button = findViewById(R.id.buttonCreateNewCalculation);
        this.editText = findViewById(R.id.editTextInsertTask);
        RecyclerView rv = findViewById(R.id.recyclerRootsCalculationList);
        adapter.setCalculations(this.calculationsHolder.getCurrentItems());
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        //onRestore
        if (savedInstanceState != null) {
            this.calculationsHolder = RootsCalculatorApplication.getInstance().getCalculationsHolder();
            adapter.setCalculations(this.calculationsHolder.getCurrentItems());
            this.editText.setText(savedInstanceState.getString("text"));
        }

        this.calculationsHolder.getLiveData().observe(this, new Observer<ArrayList<Calculation>>() {
            @Override
            public void onChanged(ArrayList<Calculation> calculations) {
                adapter.setCalculations(calculations);
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                // text did change
                String newText = editText.getText().toString();
                try {
                    long userInputLong = Long.parseLong(newText);
                    if (userInputLong >= 0) {
                        button.setEnabled(true);
                    }

                } catch (NumberFormatException ignored) {
                    button.setEnabled(false);
                }
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    long userInputLong = Long.parseLong(editText.toString());
                    if (userInputLong >= 0) {
//                        calculationsHolder.addNewCalculation(userInputLong);
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("text", this.editText.getText().toString());
    }


}