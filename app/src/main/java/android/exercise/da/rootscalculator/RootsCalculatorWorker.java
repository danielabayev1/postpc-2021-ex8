package android.exercise.da.rootscalculator;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;


public class RootsCalculatorWorker extends Worker {
    public RootsCalculatorWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // load from sp the right id for the worker calculation
        SharedPreferences sp = getApplicationContext().getSharedPreferences("roots_db", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String id = getInputData().getString("id");
        String calculationId = sp.getString(id, null);

        if (calculationId != null) {

            Calculation calc = gson.fromJson(calculationId, Calculation.class);
            long start = calc.getLastCounter();
            long number = calc.getNumber();
            if (start != -1 && number != -1) {
                long until = (long) Math.sqrt(number);
                long root1 = -1;
                long root2 = -1;
                for (; start < until; start++) {
                    if ((long) (number % start) == 0) {
                        root1 = start;
                        root2 = (long) (number / start);
                        return Result.success(new Data.Builder().putLong("root1", root1).putLong("root2", root2).build());
                    }
                    if (start % 1_000_000 == 0) {
                        SharedPreferences.Editor editor = sp.edit();
                        calc.setLastCounter(start);
                        editor.putString(id, gson.toJson(calc));
                        editor.apply();
                    }
                }
                return Result.success(new Data.Builder().putLong("root1", root1).putLong("root2", root2).build());
            }
        }
        return Result.failure();
    }

}
