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
    MainActivity activity;
    OnItemClickListener onClickDeleteButton = null;
    OnItemClickListener onClickCancelButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

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
        editText.setText("89967623946997");
//        editText.setText("91837839918353");
//        editText.setText("9181531581341931811");
//        editText.setText("6165678739293946997");

        //onRestore
        if (savedInstanceState != null) {
            this.calculationsHolder = RootsCalculatorApplication.getInstance().getCalculationsHolder();
            adapter.setCalculations(this.calculationsHolder.getCurrentItems());
            this.editText.setText(savedInstanceState.getString("text"));
        }

        // setting adapters buttons
        onClickDeleteButton = calculation -> calculationsHolder.deleteCalc(calculation);
        onClickCancelButton = calculation -> calculationsHolder.cancelCalc(calculation);
        adapter.onClickCancelButton = onClickCancelButton;
        adapter.onClickDeleteButton = onClickDeleteButton;
//        adapter.managerOfWorks = RootsCalculatorApplication.getInstance().getManagerOfWorks();

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
                    if (userInputLong > 0) {
                        button.setEnabled(true);
                    }

                } catch (NumberFormatException ignored) {
                    button.setEnabled(false);
                }
            }
        });

        button.setOnClickListener(v -> {
            try {
                long userInputLong = Long.parseLong(editText.getText().toString());
                if (userInputLong >= 0) {
//                    System.out.println("----clicked from Main1");
                    calculationsHolder.addNewCalculation(userInputLong);
                }
            } catch (NumberFormatException ignored) {
            }
        });

        this.calculationsHolder.getLiveData().observe(this, new Observer<ArrayList<Calculation>>() {
            @Override
            public void onChanged(ArrayList<Calculation> calculations) {
//                System.out.println("----changed");
                adapter.setCalculations(calculations);
            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("text", this.editText.getText().toString());
    }


}

/*todo i need to figure out how to observe in-progress tasks from previous app launch(it comes automatically)
* */