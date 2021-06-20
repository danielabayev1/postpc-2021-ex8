package android.exercise.da.rootscalculator;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class CalculationsHolder {
    private ArrayList<Calculation> calculationList = new ArrayList<>();
    private final SharedPreferences sp;
    private Gson gson;
    private MutableLiveData<ArrayList<Calculation>> calculationsLiveDataMutable = new MutableLiveData<>();
    private ManagerOfWorks managerOfWorks;

    CalculationsHolder(SharedPreferences sp, ManagerOfWorks managerOfWorks) {
        this.sp = sp;
        this.gson = new Gson();
        initializeFromSp();
        this.managerOfWorks = managerOfWorks;
        this.calculationsLiveDataMutable.setValue(new ArrayList<>(calculationList));

    }

    private void initializeFromSp() {
        Set<String> keys = this.sp.getAll().keySet();
        for (String key : keys) {
            String calcString = sp.getString(key, null);
            Calculation calc = gson.fromJson(calcString, Calculation.class);
            calculationList.add(calc);
        }
        Collections.sort(this.calculationList);
    }

    public ArrayList<Calculation> getCurrentItems() {
        return new ArrayList<>(this.calculationList);
    }

    public void addNewCalculation(long number) {
        Calculation calc = new Calculation(number);
        this.calculationList.add(calc);
        Collections.sort(this.calculationList);
        updateSpContent(calc);
        this.managerOfWorks.runNewCalc(calc);
        calculationsLiveDataMutable.setValue(new ArrayList<>(this.calculationList));

    }

    public LiveData<ArrayList<Calculation>> getLiveData() {
        return this.calculationsLiveDataMutable;
    }

    public void markCalcDone(Calculation calc) {
        for (int i = 0; i < this.calculationList.size(); i++) {
            if (this.calculationList.get(i).getCalcId().equals(calc.getCalcId())) {
                this.calculationList.get(i).setStatus("done");
                break;
            }
        }
        Collections.sort(this.calculationList);
        calculationsLiveDataMutable.setValue(new ArrayList<>(this.calculationList));
        updateSpContent(calc);
    }


    public void deleteCalc(Calculation calc) {
        this.calculationList.remove(calc);
        calculationsLiveDataMutable.setValue(new ArrayList<>(calculationList));
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(calc.getCalcId());
        editor.apply();
    }

    private void updateSpContent(Calculation calc) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(calc.getCalcId(), gson.toJson(calc));
        editor.apply();
    }

}
