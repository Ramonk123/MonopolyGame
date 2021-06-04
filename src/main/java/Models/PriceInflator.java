package Models;

public class PriceInflator {
    private int inflation;
    private double ratio;

    public PriceInflator(int inflation, double ratio) {
        this.inflation = inflation;
        this.ratio = ratio;
    }

    public int inflateByTicks(int ticks) {
        return (int) ((double) inflation * (ratio * (double) ticks));
    }
}
