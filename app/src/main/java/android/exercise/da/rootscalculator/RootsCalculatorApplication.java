package android.exercise.da.rootscalculator;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.WindowAnimationFrameStats;

public class RootsCalculatorApplication extends Application {
    private SharedPreferences sp;
    private static RootsCalculatorApplication instance;
    private CalculationsHolder calculationsHolder;
    private ManagerOfWorks managerOfWorks;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        this.sp = this.getSharedPreferences("roots_db", Context.MODE_PRIVATE);
        this.managerOfWorks = new ManagerOfWorks(this);
        this.calculationsHolder = new CalculationsHolder(this.sp, managerOfWorks);
    }

    public ManagerOfWorks getManagerOfWorks() {
        return managerOfWorks;
    }

    public CalculationsHolder getCalculationsHolder() {
        return this.calculationsHolder;
    }

    public static RootsCalculatorApplication getInstance() {

        return instance;
    }
}
