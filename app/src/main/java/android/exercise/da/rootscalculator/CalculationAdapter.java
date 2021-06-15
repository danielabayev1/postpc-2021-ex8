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
    OnItemClickListener onClickDeleteButton = null;
    OnItemClickListener onClickCancelButton = null;


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

        if (calc.getStatus().equals("in-progress")) {
            holder.deleteButton.setVisibility(View.GONE);
            holder.cancelCalc.setVisibility(View.VISIBLE);
        } else {
            holder.deleteButton.setVisibility(View.VISIBLE);
            holder.cancelCalc.setVisibility(View.GONE);
        }

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickDeleteButton != null) {
                    onClickDeleteButton.onButtonClick(calculations.get(position));
                }
            }
        });

        holder.cancelCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickCancelButton != null) {
                    onClickCancelButton.onButtonClick(calculations.get(position));
                }
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
