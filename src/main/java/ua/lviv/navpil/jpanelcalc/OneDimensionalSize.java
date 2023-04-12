package ua.lviv.navpil.jpanelcalc;

public class OneDimensionalSize {

    private double ratio = 0;
    private int max = -1;
    private int min = -1;
    private int fixed = -1;

    public OneDimensionalSize(){

    }
    public OneDimensionalSize(double ratio, int min, int max){
        setRatio(ratio);
        setMin(min);
        setMax(max);
    }
    public OneDimensionalSize(double ratio){
        setRatio(ratio);
    }
    public OneDimensionalSize(int fixed){
        setFixed(fixed);
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
        clearFixed();
        if(min > max){
            clearMin();
        }
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
        clearFixed();
        if(hasMax() && max < min){
            clearMax();
        }
    }

    public int getFixed() {
        return fixed;
    }

    public void setFixed(int fixed) {
        this.fixed = fixed;
        clearMax();
        clearMin();
    }

    private void clearMin() {
        this.min = -1;
    }

    public void clearMax() {
        this.max = -1;
    }

    public void clearFixed(){
        fixed = -1;
    }

    public boolean isFixed(){
        return fixed >= 0;
    }
    public boolean hasMax(){
        return max >= 0;
    }
    public boolean hasMin(){
        return min >= 0;
    }


}
