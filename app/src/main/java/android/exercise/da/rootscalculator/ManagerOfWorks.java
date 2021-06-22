package android.exercise.da.rootscalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;

import java.util.List;
import java.util.UUID;

public class ManagerOfWorks {
    WorkManager workManager;
    SharedPreferences sp;
    CalculationsHolder calculationsHolder;
    Gson gson;

    public ManagerOfWorks(Context context, SharedPreferences sp) {
        this.workManager = WorkManager.getInstance(context);
        this.sp = sp;
        this.gson = new Gson();
        calculationsHolder = RootsCalculatorApplication.getInstance().getCalculationsHolder();
        activateListener();
    }

    private void activateListener() {
//        LiveData<WorkInfo> working = workManager.getWorkInfoByIdLiveData(UUID.fromString("working"));
        workManager.getWorkInfosByTagLiveData("working").observeForever(new Observer<List<WorkInfo>>() {
            @Override
            public void onChanged(List<WorkInfo> workInfos) {
                for (WorkInfo workInfo : workInfos) {
//                    System.out.println("---- work info: "+workInfo);
                    if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                        String id = workInfo.getOutputData().getString("id");
                        long root1 = workInfo.getOutputData().getLong("root1", -1);
                        long root2 = workInfo.getOutputData().getLong("root2", -1);
                        String calcString = sp.getString(id, null);
                        if (calcString != null) {
                            Calculation calculation = gson.fromJson(calcString, Calculation.class);
                            if (!calculation.getStatus().equals("done")) {
                                calculationsHolder.markCalcDone(id, root1, root2);
                            }
                        }
                    }
                }
            }
        });
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

    public void cancelCalc(String id) {
        System.out.println("----id from cancelCalc: "+UUID.fromString(id));
        workManager.cancelWorkById(UUID.fromString(id));
        System.out.println("----from cancel managerofWorks");
    }

}
