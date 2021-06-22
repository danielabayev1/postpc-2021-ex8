package android.exercise.da.rootscalculator;

import java.util.UUID;

public class Calculation implements Comparable<Calculation> {
    long number;
    long root1, root2;
    long lastCounter;
    String status;
    String calcId;
    String requestId;

    public Calculation(long number) {
        this.number = number;
        this.status = "in-progress";
        this.root1 = -1;
        this.root2 = -1;
        this.calcId = UUID.randomUUID().toString();
        this.lastCounter = 2;
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

    public long getLastCounter() {
        return lastCounter;
    }

    public void setLastCounter(long lastCounter) {
        this.lastCounter = lastCounter;
    }

    public String getDescription() {
//        if (status.equals("in-progress")){
//            return "Roots for " + number + ": in-progress" ;
//        }
        if (root1 != -1 && root2 != -1) {
            return "Roots for " + number + ": " + root1 + "x" + root2;
        } else  {
            return "Roots for " + number + ": number is prime";
        }
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }

    @Override
    public int compareTo(Calculation o) {
        if (this.status.equals("in-progress") && o.status.equals("in-progress") ||
                this.status.equals("done") && o.status.equals("done")) {
            return Double.compare(this.number, o.number);
        } else if (this.status.equals("in-progress") && o.status.equals("done")) {
            return -1;
        } else {
            return 1;
        }
    }
}
