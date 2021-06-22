package android.exercise.da.rootscalculator;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.WorkInfo;

import java.util.ArrayList;
import java.util.UUID;

public class CalculationAdapter extends RecyclerView.Adapter<CalculationHolder> {
    ArrayList<Calculation> calculations = new ArrayList<>();
    LiveData<WorkInfo> liveData;
    OnItemClickListener onClickDeleteButton = null;
    OnItemClickListener onClickCancelButton = null;
    ManagerOfWorks managerOfWorks;


    @NonNull
    @Override
    public CalculationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_work_item, parent, false);
        return new CalculationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalculationHolder holder, int position) {
        Calculation calc = this.calculations.get(position);
        System.out.println("----"+calc.getCalcId());
        if(managerOfWorks!=null) {
            liveData = managerOfWorks.getWorkManager().getWorkInfoByIdLiveData(UUID.fromString(calc.getRequestId()));
            holder.textView.setText("a");
            liveData.observeForever(new Observer<WorkInfo>() {
                @Override
                public void onChanged(WorkInfo workInfo) {
                    Data progress = workInfo.getProgress();
                    int percent = progress.getInt("percent", -1);
                    if (percent != -1) {
//                    String msg = "calculating roots for "+calc.getNumber()+": "+percent+"%...";
                        String msg = percent + "%...";
                        holder.textView.setText(msg);
                    }
                }
            });
        }

        if (calc.getStatus().equals("in-progress")) {
            holder.deleteButton.setVisibility(View.GONE);
            holder.cancelCalc.setVisibility(View.VISIBLE);
        } else {
            holder.deleteButton.setVisibility(View.VISIBLE);
            holder.cancelCalc.setVisibility(View.GONE);
            holder.textView.setText(calculations.get(position).getDescription());
        }


        holder.deleteButton.setOnClickListener(v -> {
            if (onClickDeleteButton != null) {
                onClickDeleteButton.onButtonClick(calculations.get(position));
            }
        });

        holder.cancelCalc.setOnClickListener(v -> {
            if (onClickCancelButton != null) {
                onClickCancelButton.onButtonClick(calculations.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return calculations.size();
    }

    public void setCalculations(ArrayList<Calculation> calculations) {
        this.calculations.clear();
        this.calculations.addAll(calculations);
        notifyDataSetChanged();
    }
}
