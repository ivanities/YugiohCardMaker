package newclasses.timeUtils;

public class StopWatch {
    private long t0 = 0;

    public void start() {
        t0 = System.currentTimeMillis();
    }

    public double getElapsedTime() {
        long t1 = System.currentTimeMillis();
        return (t1 - t0)/1000.0;
    }

    public void printElapsedTime(String key) {
        System.out.println(key + ": ElapsedTime = " + getElapsedTime());
    }
}
