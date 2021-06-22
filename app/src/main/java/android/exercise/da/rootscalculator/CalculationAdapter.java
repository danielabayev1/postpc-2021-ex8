package android.exercise.da.rootscalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalculationAdapter extends RecyclerView.Adapter<CalculationHolder> {
    ArrayList<Calculation> calculations = new ArrayList<>();
//    LiveData<WorkInfo> liveData = null;
    OnItemClickListener onClickDeleteButton = null;
    OnItemClickListener onClickCancelButton = null;
//    Observer<WorkInfo> observer = null;
//    ManagerOfWorks managerOfWorks = null;
    int i=0;


    @NonNull
    @Override
    public CalculationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_work_item, parent, false);
        return new CalculationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalculationHolder holder, int position) {
//        System.out.println("**** created: "+i+" pos:"+position);
        Calculation calc = this.calculations.get(position);
//        String s = calc.getNumber() + " pos " + position;
//        holder.textView.setText(s);
//        if (managerOfWorks != null) {
//            if (liveData != null) {
//                if (observer != null) {
//                    System.out.println("++++ del pos: "+position+" obs:" + observer +" ld:"+liveData);
//                    liveData.removeObserver(observer);
//                    observer = null;
//                    liveData=null;
//                }
//            }
//        }

        if (calc.getStatus().equals("in-progress")) {
            holder.deleteButton.setVisibility(View.GONE);
            holder.cancelCalc.setVisibility(View.VISIBLE);
//            liveData = managerOfWorks.getWorkManager().getWorkInfoByIdLiveData(UUID.fromString(calc.getRequestId()));
////            System.out.println("++++calcId: " + calc.getCalcId() + " pos:" + position);
//            observer = new Observer<WorkInfo>() {
//                @Override
//                public void onChanged(WorkInfo workInfo) {
////                    System.out.println("----calcId: "+calc.getCalcId()+" pos:"+position);
//                    Data progress = workInfo.getProgress();
//                    int percent = progress.getInt("percent", -1);
//                    if (percent != -1) {
////                    String msg = "calculating roots for "+calc.getNumber()+": "+percent+"%...";
//                        String msg = percent + "%...";
//                        holder.textView.setText(msg);
//                    }
//                }
//            };
//            System.out.println("++++ create pos: "+position+" obs:" + observer +" ld:"+liveData);
//            liveData.observeForever(observer);
        } else {
            holder.deleteButton.setVisibility(View.VISIBLE);
            holder.cancelCalc.setVisibility(View.GONE);
//            liveData=null;
//            observer=null;
        }
        holder.textView.setText(calculations.get(position).getFinalDescription());


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
