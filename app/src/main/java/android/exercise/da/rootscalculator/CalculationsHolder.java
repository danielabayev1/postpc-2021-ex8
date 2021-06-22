package android.exercise.da.rootscalculator;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class CalculationsHolder {
    private final ArrayList<Calculation> calculationList = new ArrayList<>();
    private final SharedPreferences sp;
    private final Gson gson;
    private final MutableLiveData<ArrayList<Calculation>> calculationsLiveDataMutable = new MutableLiveData<>();



    CalculationsHolder(SharedPreferences sp) {
        this.sp = sp;
        this.gson = new Gson();
        initializeFromSp();
        System.out.println("----" + calculationList.size());
        this.calculationsLiveDataMutable.setValue(new ArrayList<>(calculationList));

    }

    private void initializeFromSp() {
        Set<String> keys = this.sp.getAll().keySet();
        for (String key : keys) {
            String calcString = sp.getString(key, null);
            if (calcString != null) {
                Calculation calc = gson.fromJson(calcString, Calculation.class);
                calculationList.add(calc);
            }
        }
        Collections.sort(this.calculationList);
    }

    public ArrayList<Calculation> getCurrentItems() {
        return new ArrayList<>(this.calculationList);
    }

    public void addNewCalculation(long number) {
        Calculation calc = new Calculation(number);
        UUID requestId = RootsCalculatorApplication.getInstance().getManagerOfWorks().runNewCalc(calc);
        calc.setRequestId(requestId.toString());
        this.calculationList.add(calc);
        Collections.sort(this.calculationList);
        updateSpContent(calc);
        calculationsLiveDataMutable.setValue(new ArrayList<>(this.calculationList));
    }

    public LiveData<ArrayList<Calculation>> getLiveData() {
        return this.calculationsLiveDataMutable;
    }

    public void markCalcDone(String id, long root1, long root2) {
        Calculation calc = null;
        for (int i = 0; i < this.calculationList.size(); i++) {
            if (this.calculationList.get(i).getCalcId().equals(id)) {
                calc = this.calculationList.get(i);
                calc.setRoots(root1, root2);
                calc.setStatus("done");
                break;
            }
        }
        Collections.sort(this.calculationList);
        calculationsLiveDataMutable.setValue(new ArrayList<>(this.calculationList));
        System.out.println("----from markDone");
        updateSpContent(calc);
    }


    public void deleteCalc(Calculation calc) {
        for (int i = 0; i < this.calculationList.size(); i++) {
            if (this.calculationList.get(i).getCalcId().equals(calc.getCalcId())) {
                this.calculationList.remove(i);
                System.out.println("----from delete");
                break;
            }
        }
        System.out.println("----" + calculationList.size());
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(calc.getCalcId());
        editor.apply();
        calculationsLiveDataMutable.setValue(new ArrayList<>(calculationList));

    }

    public void cancelCalc(Calculation calc) {
        RootsCalculatorApplication.getInstance().getManagerOfWorks().cancelCalc(calc.getRequestId());
        System.out.println("----from cancel id: "+calc.getCalcId());
        deleteCalc(calc);
    }

    private void updateSpContent(Calculation calc) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(calc.getCalcId(), gson.toJson(calc));
        editor.apply();
    }

}
