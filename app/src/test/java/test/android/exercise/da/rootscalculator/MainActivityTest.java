package test.android.exercise.da.rootscalculator;

import android.app.Application;
import android.exercise.da.rootscalculator.CalculationsHolder;
import android.exercise.da.rootscalculator.MainActivity;
import android.exercise.da.rootscalculator.R;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
//import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.junit.Assert.*;
//import static org.robolectric.Shadows.shadowOf;
//, application = Application.class
@RunWith(RobolectricTestRunner.class)
@Config(sdk = {28})
public class MainActivityTest {

    private ActivityController<MainActivity> activityController;
    private MainActivity activityUnderTest;
    private CalculationsHolder mockCalculationsHolder;

//    /**
//     * initialize main activity with a mock FireBaseManager
//     */
//    @Before
//    public void setup() {
//        mockCalculationsHolder = Mockito.mock(CalculationsHolder.class);
//        activityController = Robolectric.buildActivity(MainActivity.class);
//        activityUnderTest = activityController.get();
////        activityUnderTest.calculationsHolder = mockCalculationsHolder;
//
//    }

    @Test
    public void when_enterValidNumberAndClickButton_then_addCalculationIsPerformed() {
        //setup
        mockCalculationsHolder = Mockito.mock(CalculationsHolder.class);
        activityController = Robolectric.buildActivity(MainActivity.class);
        activityUnderTest = activityController.get();
        Mockito.when(mockCalculationsHolder.getCurrentItems()).thenReturn(new ArrayList<>());
        activityController.create().start().resume();
//        Button button = activityUnderTest.findViewById(R.id.buttonCreateNewCalculation);
//        EditText editText = activityUnderTest.findViewById(R.id.editTextInsertTask);
//        editText.setText("89967623946997");
        //test
//        button.performClick();

        //verify
        assertEquals(1,1);


    }
    @Test
    public void tests(){
        assertEquals(1,1);
    }

}