package android.exercise.da.rootscalculator;

import java.util.UUID;

public class Calculation implements Comparable<Calculation> {
    long number;
    long root1, root2;
    String status;
    String calcId;

    public Calculation(long number) {
        this.number = number;
        this.status = "in-progress";
        this.root1 = -1;
        this.root2 = -1;
        this.calcId = UUID.randomUUID().toString();
    }

    public void setRoots(long root1, long root2) {
        this.root1 = root1;
        this.root2 = root2;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public long getNumber() {
        return this.number;
    }

    public String getCalcId() {
        return calcId;
    }

    @Override
    public int compareTo(Calculation o) {
        if (this.status.equals("in-progress") && o.status.equals("in-progress") ||
                this.status.equals("done") && o.status.equals("done")) {
            return Double.compare(this.number, o.number);
        } else if (this.status.equals("in-progress") && o.status.equals("done")) {
            return 1;
        } else {
            return -1;
        }
    }
}
