package ua.lviv.navpil.jpanelcalc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OneDimensionalCalculator {


    public static int[] calculate(List<OneDimensionalSize> sizes, int totalWidth){
        List<OneDimensionalSizeHelper> all = new ArrayList<>();
        List<OneDimensionalSizeHelper> temp = new ArrayList<>();

        for (OneDimensionalSize size : sizes) {
            OneDimensionalSizeHelper helper = new OneDimensionalSizeHelper(size);
            all.add(helper);
            if(!helper.isFixed()) {
                temp.add(helper);
            }
        }

        int sumTotal;
        int widthToGive = totalWidth;
        int prev;
        do{
            prev = widthToGive;
            double ratioSum = 0;
            for (OneDimensionalSizeHelper helper : temp) {
                ratioSum += helper.getRatio();
            }
            for (OneDimensionalSizeHelper helper : temp) {
                helper.addTempSize(helper.getRatio() * widthToGive / ratioSum);
            }
            sumTotal = 0;
            for (OneDimensionalSizeHelper helper : all) {
                sumTotal += helper.getRealisticSize();
            }
            widthToGive = totalWidth - sumTotal;

            if(widthToGive > 0){
                for(Iterator<OneDimensionalSizeHelper> iterator = temp.iterator();iterator.hasNext();){
                    OneDimensionalSizeHelper helper = iterator.next();
                    if(helper.hitUpperBound()){
                        iterator.remove();
                    }
                }
            } else {
                for(Iterator<OneDimensionalSizeHelper> iterator = temp.iterator();iterator.hasNext();){
                    OneDimensionalSizeHelper helper = iterator.next();
                    if(helper.hitLowerBound()){
                        iterator.remove();
                    }
                }
            }

        } while(widthToGive != 0 && prev != widthToGive && temp.size() > 0);

        int[] returnValue = new int[sizes.size()];
        int index = 0;
        for (OneDimensionalSizeHelper helper : all) {
            returnValue[index] = helper.getRealisticSize();
            index++;
        }
        return returnValue;
    }
}
