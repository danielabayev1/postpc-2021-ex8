package android.exercise.da.rootscalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.UUID;

public class ManagerOfWorks {
    WorkManager workManager;
//    SharedPreferences sp;
//    CalculationsHolder calculationsHolder;

    public ManagerOfWorks(Context context) {
        this.workManager = WorkManager.getInstance(context);
//        initializeFromHolder();
    }

    public WorkManager getWorkManager() {
        return workManager;
    }

//    private void initializeFromHolder() {
//        for (Calculation calc : this.calculationsHolder.getCurrentItems()) {
//            // todo : done automatically
//            if (calc.getStatus().equals("in-progress")) {
//                OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(RootsCalculatorWorker.class)
//                        .addTag("working").setInputData(new Data.Builder()
//                                .putString("id", calc.getCalcId()).build()).build();
//                workManager.enqueueUniqueWork(calc.getCalcId(), ExistingWorkPolicy.KEEP, request);
//            }
//        }
//    }

    public UUID runNewCalc(Calculation calc) {
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(RootsCalculatorWorker.class)
                .addTag("working").setInputData(new Data.Builder()
                        .putString("id", calc.getCalcId()).build()).build();
        workManager.enqueueUniqueWork(calc.getCalcId(), ExistingWorkPolicy.KEEP, request);
        return request.getId();
    }

//    public void cancelCalc(Calculation calc) {
//        workManager.cancelUniqueWork(calc.getCalcId());
//    }

}
