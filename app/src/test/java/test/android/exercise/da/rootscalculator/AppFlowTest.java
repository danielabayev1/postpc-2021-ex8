package test.android.exercise.da.rootscalculator;

import android.app.Application;
import android.exercise.da.rootscalculator.Calculation;
import android.exercise.da.rootscalculator.CalculationsHolder;
import android.exercise.da.rootscalculator.MainActivity;
import android.exercise.da.rootscalculator.R;
import android.widget.EditText;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ibm.icu.text.ArabicShaping;

import org.checkerframework.checker.units.qual.C;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static android.os.Looper.getMainLooper;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
//
@Config(sdk = {28}, application = Application.class)
public class AppFlowTest {
    private ActivityController<MainActivity> activityController;
    private MainActivity activityUnderTest;
    private CalculationsHolder mockCalculatorHolder;

    @Before
    public void setup() {
        mockCalculatorHolder = Mockito.mock(CalculationsHolder.class);
        activityController = Robolectric.buildActivity(MainActivity.class);
        activityUnderTest = activityController.get();
        activityUnderTest.calculationsHolder = mockCalculatorHolder;
    }

    @Test
    public void when_insertValidNumberAndClickedButton_then_addCalculationIsExecuted() {
        //setup

        MutableLiveData<ArrayList<Calculation>> mockLiveData = new MutableLiveData<>();
        Mockito.when(mockCalculatorHolder.getLiveData()).thenReturn(mockLiveData);
        activityController.create().start().resume();

        FloatingActionButton b = activityUnderTest.findViewById(R.id.buttonCreateNewCalculation);
        EditText editText = activityUnderTest.findViewById(R.id.editTextInsertTask);

        //test
        editText.setText("89967623946997");
        b.performClick();

        //verify
        Mockito.verify(mockCalculatorHolder).addNewCalculation(Mockito.eq(89967623946997L));
    }

    @Test
    public void when_insertInvalidInput_then_ButtonShouldBeDisabled() {
        //setup
        MutableLiveData<ArrayList<Calculation>> mockLiveData = new MutableLiveData<>();
        Mockito.when(mockCalculatorHolder.getLiveData()).thenReturn(mockLiveData);
        activityController.create().start().resume();

        FloatingActionButton b = activityUnderTest.findViewById(R.id.buttonCreateNewCalculation);
        EditText editText = activityUnderTest.findViewById(R.id.editTextInsertTask);

        //test
        editText.setText("123a");

        //verify
        assertFalse(b.isEnabled());
    }

    @Test
    public void when_insertValidInput_then_AdapterShouldHave1Item() {
        //setup
        Calculation calculation = new Calculation(89967623946997L);
        ArrayList<Calculation> arr = new ArrayList<>();
        arr.add(calculation);
        MutableLiveData<ArrayList<Calculation>> mockLiveData = new MutableLiveData<>(arr);
        Mockito.when(mockCalculatorHolder.getLiveData()).thenReturn(mockLiveData);
        activityController.create().start().resume();

        FloatingActionButton b = activityUnderTest.findViewById(R.id.buttonCreateNewCalculation);
        EditText editText = activityUnderTest.findViewById(R.id.editTextInsertTask);
        RecyclerView rv = activityUnderTest.findViewById(R.id.recyclerRootsCalculationList);


        //verify
        assertEquals(1,rv.getAdapter().getItemCount());
    }
}