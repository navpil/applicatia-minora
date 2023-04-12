package ua.lviv.navpil.jpanelcalc;

public class OneDimensionalSizeHelper extends OneDimensionalSize{

    private int tempSize;

    public OneDimensionalSizeHelper(OneDimensionalSize size){
        if(size.isFixed()){
            setFixed(size.getFixed());
        }
        if(size.hasMax()){
            setMax(size.getMax());
        }
        if(size.hasMin()){
            setMin(size.getMin());
        }
        setRatio(size.getRatio());
    }

    public boolean hitUpperBound(){
        return hasMax() && getTempSize() >= getMax();
    }

    public boolean hitLowerBound(){
        return hasMin() && getTempSize() <= getMin();
    }

    public int getRealisticSize(){
        if(isFixed()){
            return getFixed();
        }
        if(hitUpperBound()){
            return getMax();
        }
        if(hitLowerBound()){
            return getMin();
        }
        return Math.max(getTempSize(), 0);
    }

    public int getTempSize(){
        return tempSize;
    }

    public void addTempSize(double tempSize){
        this.tempSize += Math.round(tempSize);
    }

}
