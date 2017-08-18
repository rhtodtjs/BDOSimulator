package com.kkk8888.bdosimulator;

import java.util.ArrayList;

/**
 * Created by alfo06-18 on 2017-08-18.
 */

public class EnchantItemRatio {

    final static double[] stackRatio = new double[]{

            0, //0
            0,
            0,
            0,
            0,
            0,
            0,//6
            2.50, //7
            2.00, //8
            1.50, //9
            1.25, //10
            0.75, //11
            0.63, // 12
            0.50, //13
            0.50, //14
            1.50, //15
            0.75, //16
            0.50, //17
            0.25, //18
            0.15 //19
    };

    final static double[] bodystackRatio = new double[]{

            0, //0
            0,
            0,
            0,
            0,
            2.5,//5
            2.0,//6
            1.7, //7
            1.5, //8
            1.3, //9
            1.0, //10
            0.75, //11
            0.63, // 12
            0.50, //13
            0.50, //14
            1.50, //15
            0.75, //16
            0.50, //17
            0.25, //18
            0.15 //19
    };

    final static double[] accestackRatio = new double[]{

            1.50, //15
            0.75, //16
            0.50, //17
            0.25, //18
            0.15 //19


    };


    //무기
    ArrayList<ItemRatio> whiteItemMaxRate = new ArrayList<>();
    ArrayList<ItemRatio> greenItemMaxRate = new ArrayList<>();
    ArrayList<ItemRatio> blueItemMaxRate = new ArrayList<>();
    ArrayList<ItemRatio> yellowItemMaxRate = new ArrayList<>();

    //방어구
    ArrayList<ItemRatio> bodyWhiteItemMaxRate = new ArrayList<>();
    ArrayList<ItemRatio> bodyGreenItemMaxRate = new ArrayList<>();
    ArrayList<ItemRatio> bodyBlueItemMaxRate = new ArrayList<>();
    ArrayList<ItemRatio> bodyYellowItemMaxRate = new ArrayList<>();

    //방어구
    ArrayList<ItemRatio> AcceWhiteItemMaxRate = new ArrayList<>();
    ArrayList<ItemRatio> AcceGreenItemMaxRate = new ArrayList<>();
    ArrayList<ItemRatio> AcceBlueItemMaxRate = new ArrayList<>();
    ArrayList<ItemRatio> AcceYellowItemMaxRate = new ArrayList<>();


    public EnchantItemRatio() {

        //일반템
        whiteItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //0
        whiteItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //1
        whiteItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //2
        whiteItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //3
        whiteItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //4
        whiteItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //5
        whiteItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //6

        whiteItemMaxRate.add(new ItemRatio(20.0, 52.5, 13)); //7
        whiteItemMaxRate.add(new ItemRatio(17.5, 45.5, 14));// 8
        whiteItemMaxRate.add(new ItemRatio(15.0, 37.5, 15)); //9
        whiteItemMaxRate.add(new ItemRatio(12.5, 32.5, 16)); //10
        whiteItemMaxRate.add(new ItemRatio(10.0, 23.5, 18)); //11
        whiteItemMaxRate.add(new ItemRatio(7.5, 20.0, 20)); //12
        whiteItemMaxRate.add(new ItemRatio(5.0, 17.5, 25)); //13
        whiteItemMaxRate.add(new ItemRatio(2.5, 15.0, 25)); //14
        whiteItemMaxRate.add(new ItemRatio(15.0, 52.5, 25));//15
        whiteItemMaxRate.add(new ItemRatio(7.5, 33.75, 35));//I
        whiteItemMaxRate.add(new ItemRatio(5.0, 27.0, 44));//II
        whiteItemMaxRate.add(new ItemRatio(2.0, 25.0, 90));//III
        whiteItemMaxRate.add(new ItemRatio(1.5, 20.1, 124));//IV
        whiteItemMaxRate.add(new ItemRatio(0, 0, 124));

        //녹템
        greenItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //0
        greenItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //1
        greenItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //2
        greenItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //3
        greenItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //4
        greenItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //5
        greenItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //6

        greenItemMaxRate.add(new ItemRatio(17.6, 50.1, 13)); //7
        greenItemMaxRate.add(new ItemRatio(15.4, 43.4, 14));// 8
        greenItemMaxRate.add(new ItemRatio(13.2, 35.7, 15)); //9
        greenItemMaxRate.add(new ItemRatio(11.0, 31.0, 16)); //10
        greenItemMaxRate.add(new ItemRatio(8.8, 22.30, 18)); //11
        greenItemMaxRate.add(new ItemRatio(6.6, 19.2, 20)); //12
        greenItemMaxRate.add(new ItemRatio(4.4, 16.9, 25)); //13
        greenItemMaxRate.add(new ItemRatio(2.2, 14.7, 25)); //14
        greenItemMaxRate.add(new ItemRatio(13.2, 50.7, 25));//15
        greenItemMaxRate.add(new ItemRatio(6.6, 32.9, 35));//I
        greenItemMaxRate.add(new ItemRatio(4.4, 26.4, 44));//II
        greenItemMaxRate.add(new ItemRatio(1.8, 24.3, 90));//III
        greenItemMaxRate.add(new ItemRatio(1.3, 19.9, 124));//IV
        greenItemMaxRate.add(new ItemRatio(0, 0, 124));

        //파템
        blueItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //0
        blueItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //1
        blueItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //2
        blueItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //3
        blueItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //4
        blueItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //5
        blueItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //6

        blueItemMaxRate.add(new ItemRatio(15.4, 47.9, 13)); //7
        blueItemMaxRate.add(new ItemRatio(13.5, 41.5, 14));// 8
        blueItemMaxRate.add(new ItemRatio(11.6, 34.1, 15)); //9
        blueItemMaxRate.add(new ItemRatio(9.6, 29.6, 16)); //10
        blueItemMaxRate.add(new ItemRatio(7.7, 21.2, 18)); //11
        blueItemMaxRate.add(new ItemRatio(5.8, 18.4, 20)); //12
        blueItemMaxRate.add(new ItemRatio(3.9, 16.4, 25)); //13
        blueItemMaxRate.add(new ItemRatio(1.9, 14.4, 25)); //14
        blueItemMaxRate.add(new ItemRatio(11.6, 49.1, 25));//15
        blueItemMaxRate.add(new ItemRatio(5.8, 32.0, 35));//I
        blueItemMaxRate.add(new ItemRatio(3.9, 25.9, 44));//II
        blueItemMaxRate.add(new ItemRatio(1.5, 24.0, 90));//III
        blueItemMaxRate.add(new ItemRatio(1.2, 19.8, 124));//IV
        blueItemMaxRate.add(new ItemRatio(0, 0, 124));

        //노란템 (보스템)
        yellowItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //0
        yellowItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //1
        yellowItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //2
        yellowItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //3
        yellowItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //4
        yellowItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //5
        yellowItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //6

        yellowItemMaxRate.add(new ItemRatio(13.2, 45.7, 13)); //7
        yellowItemMaxRate.add(new ItemRatio(11.55, 39.6, 14));// 8
        yellowItemMaxRate.add(new ItemRatio(9.9, 32.4, 15)); //9
        yellowItemMaxRate.add(new ItemRatio(8.25, 28.3, 16)); //10
        yellowItemMaxRate.add(new ItemRatio(6.6, 20.1, 18)); //11
        yellowItemMaxRate.add(new ItemRatio(4.95, 17.6, 20)); //12
        yellowItemMaxRate.add(new ItemRatio(3.3, 15.8, 25)); //13
        yellowItemMaxRate.add(new ItemRatio(1.65, 14.2, 25)); //14
        yellowItemMaxRate.add(new ItemRatio(9.9, 47.4, 25));//15
        yellowItemMaxRate.add(new ItemRatio(5.0, 31.2, 35));//I
        yellowItemMaxRate.add(new ItemRatio(3.3, 25.3, 44));//II
        yellowItemMaxRate.add(new ItemRatio(1.3, 23.8, 90));//III
        yellowItemMaxRate.add(new ItemRatio(1.0, 19.6, 124));//IV
        yellowItemMaxRate.add(new ItemRatio(0, 0, 124));


        ///방어구


        //일반템
        bodyWhiteItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //0
        bodyWhiteItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //1
        bodyWhiteItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //2
        bodyWhiteItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //3
        bodyWhiteItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //4

        bodyWhiteItemMaxRate.add(new ItemRatio(20.0, 52.5, 13)); //5
        bodyWhiteItemMaxRate.add(new ItemRatio(17.5, 45.5, 14)); //6
        bodyWhiteItemMaxRate.add(new ItemRatio(16.3, 40.8, 14)); //7
        bodyWhiteItemMaxRate.add(new ItemRatio(15.0, 37.5, 15));// 8
        bodyWhiteItemMaxRate.add(new ItemRatio(12.5, 32.5, 16)); //9
        bodyWhiteItemMaxRate.add(new ItemRatio(11.3, 28.3, 17)); //10
        bodyWhiteItemMaxRate.add(new ItemRatio(10.0, 23.5, 18)); //11
        bodyWhiteItemMaxRate.add(new ItemRatio(7.5, 20.0, 20)); //12
        bodyWhiteItemMaxRate.add(new ItemRatio(5.0, 17.5, 25)); //13
        bodyWhiteItemMaxRate.add(new ItemRatio(2.5, 15.0, 25)); //14
        bodyWhiteItemMaxRate.add(new ItemRatio(15.0, 52.5, 25));//15
        bodyWhiteItemMaxRate.add(new ItemRatio(7.5, 33.75, 35));//I
        bodyWhiteItemMaxRate.add(new ItemRatio(5.0, 27.0, 44));//II
        bodyWhiteItemMaxRate.add(new ItemRatio(2.0, 25.0, 90));//III
        bodyWhiteItemMaxRate.add(new ItemRatio(1.5, 20.1, 124));//IV

        //녹템
        bodyGreenItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //0
        bodyGreenItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //1
        bodyGreenItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //2
        bodyGreenItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //3
        bodyGreenItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //4

        bodyGreenItemMaxRate.add(new ItemRatio(17.6, 50.1, 13)); //5
        bodyGreenItemMaxRate.add(new ItemRatio(15.4, 43.4, 14)); //6
        bodyGreenItemMaxRate.add(new ItemRatio(14.3, 38.8, 14)); //7
        bodyGreenItemMaxRate.add(new ItemRatio(13.2, 35.7, 15));// 8
        bodyGreenItemMaxRate.add(new ItemRatio(11.0, 31.0, 16)); //9
        bodyGreenItemMaxRate.add(new ItemRatio(9.9, 26.9, 17)); //10
        bodyGreenItemMaxRate.add(new ItemRatio(8.8, 22.30, 18)); //11
        bodyGreenItemMaxRate.add(new ItemRatio(6.6, 19.2, 20)); //12
        bodyGreenItemMaxRate.add(new ItemRatio(4.4, 16.9, 25)); //13
        bodyGreenItemMaxRate.add(new ItemRatio(2.2, 14.7, 25)); //14
        bodyGreenItemMaxRate.add(new ItemRatio(13.2, 50.7, 25));//15
        bodyGreenItemMaxRate.add(new ItemRatio(6.6, 32.9, 35));//I
        bodyGreenItemMaxRate.add(new ItemRatio(4.4, 26.4, 44));//II
        bodyGreenItemMaxRate.add(new ItemRatio(1.8, 24.3, 90));//III
        bodyGreenItemMaxRate.add(new ItemRatio(1.3, 19.9, 124));//IV
        bodyGreenItemMaxRate.add(new ItemRatio(0, 0, 124));

        //파템
        bodyBlueItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //0
        bodyBlueItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //1
        bodyBlueItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //2
        bodyBlueItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //3
        bodyBlueItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //4

        bodyBlueItemMaxRate.add(new ItemRatio(15.4, 47.9, 13)); //5
        bodyBlueItemMaxRate.add(new ItemRatio(13.5, 41.5, 14)); //6
        bodyBlueItemMaxRate.add(new ItemRatio(12.5, 37.0, 14)); //7
        bodyBlueItemMaxRate.add(new ItemRatio(11.1, 34.1, 15));// 8
        bodyBlueItemMaxRate.add(new ItemRatio(9.6, 29.6, 16)); //9
        bodyBlueItemMaxRate.add(new ItemRatio(8.7, 31.0, 16)); //10
        bodyBlueItemMaxRate.add(new ItemRatio(7.7, 21.2, 18)); //11
        bodyBlueItemMaxRate.add(new ItemRatio(5.8, 18.4, 20)); //12
        bodyBlueItemMaxRate.add(new ItemRatio(3.9, 16.4, 25)); //13
        bodyBlueItemMaxRate.add(new ItemRatio(1.9, 14.4, 25)); //14
        bodyBlueItemMaxRate.add(new ItemRatio(11.6, 49.1, 25));//15
        bodyBlueItemMaxRate.add(new ItemRatio(5.8, 32.0, 35));//I
        bodyBlueItemMaxRate.add(new ItemRatio(3.9, 25.9, 44));//II
        bodyBlueItemMaxRate.add(new ItemRatio(1.5, 24.0, 90));//III
        bodyBlueItemMaxRate.add(new ItemRatio(1.2, 19.8, 124));//IV
        bodyBlueItemMaxRate.add(new ItemRatio(0, 0, 124));

        //노란템 (보스템)
        bodyYellowItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //0
        bodyYellowItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //1
        bodyYellowItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //2
        bodyYellowItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //3
        bodyYellowItemMaxRate.add(new ItemRatio(100.0, 100.0, 0)); //4

        bodyYellowItemMaxRate.add(new ItemRatio(13.2, 45.7, 13)); //5
        bodyYellowItemMaxRate.add(new ItemRatio(11.6, 39.6, 14)); //6
        bodyYellowItemMaxRate.add(new ItemRatio(10.7, 35.2, 14)); //7
        bodyYellowItemMaxRate.add(new ItemRatio(9.9, 32.4, 15));// 8
        bodyYellowItemMaxRate.add(new ItemRatio(8.3, 28.3, 16)); //9
        bodyYellowItemMaxRate.add(new ItemRatio(7.4, 24.4, 16)); //10
        bodyYellowItemMaxRate.add(new ItemRatio(6.6, 20.1, 18)); //11
        bodyYellowItemMaxRate.add(new ItemRatio(4.95, 17.6, 20)); //12
        bodyYellowItemMaxRate.add(new ItemRatio(3.3, 15.8, 25)); //13
        bodyYellowItemMaxRate.add(new ItemRatio(1.65, 14.2, 25)); //14
        bodyYellowItemMaxRate.add(new ItemRatio(9.9, 47.4, 25));//15
        bodyYellowItemMaxRate.add(new ItemRatio(5.0, 31.2, 35));//I
        bodyYellowItemMaxRate.add(new ItemRatio(3.3, 25.3, 44));//II
        bodyYellowItemMaxRate.add(new ItemRatio(1.3, 23.8, 90));//III
        bodyYellowItemMaxRate.add(new ItemRatio(1.0, 19.6, 124));//IV
        bodyYellowItemMaxRate.add(new ItemRatio(0, 0, 124));

        //흰템악세

        AcceWhiteItemMaxRate.add(new ItemRatio(15.0, 52.5, 25));//15
        AcceWhiteItemMaxRate.add(new ItemRatio(7.5, 33.75, 35));//I
        AcceWhiteItemMaxRate.add(new ItemRatio(5.0, 27.0, 44));//II
        AcceWhiteItemMaxRate.add(new ItemRatio(2.0, 25.0, 90));//III
        AcceWhiteItemMaxRate.add(new ItemRatio(1.5, 20.1, 124));//IV

        //녹템 악세
        AcceGreenItemMaxRate.add(new ItemRatio(13.2, 50.7, 25));//15
        AcceGreenItemMaxRate.add(new ItemRatio(6.6, 32.9, 35));//I
        AcceGreenItemMaxRate.add(new ItemRatio(4.4, 26.4, 44));//II
        AcceGreenItemMaxRate.add(new ItemRatio(1.8, 24.3, 90));//III
        AcceGreenItemMaxRate.add(new ItemRatio(1.3, 19.9, 124));//IV

        //파템 악세
        AcceBlueItemMaxRate.add(new ItemRatio(11.6, 49.1, 25));//15
        AcceBlueItemMaxRate.add(new ItemRatio(5.8, 32.0, 35));//I
        AcceBlueItemMaxRate.add(new ItemRatio(3.9, 25.9, 44));//II
        AcceBlueItemMaxRate.add(new ItemRatio(1.5, 24.0, 90));//III
        AcceBlueItemMaxRate.add(new ItemRatio(1.2, 19.8, 124));//IV

        AcceYellowItemMaxRate.add(new ItemRatio(9.9, 47.4, 25));//15
        AcceYellowItemMaxRate.add(new ItemRatio(5.0, 31.2, 35));//I
        AcceYellowItemMaxRate.add(new ItemRatio(3.3, 25.3, 44));//II
        AcceYellowItemMaxRate.add(new ItemRatio(1.3, 23.8, 90));//III
        AcceYellowItemMaxRate.add(new ItemRatio(1.0, 19.6, 124));//IV

    }

    class ItemRatio {
        private double baseRate;
        private double maxRate;
        private int maxStack;

        public ItemRatio(double baseRate, double maxRate, int maxStack) {
            this.baseRate = baseRate;
            this.maxRate = maxRate;
            this.maxStack = maxStack;
        }

        public double getBaseRate() {
            return baseRate;
        }

        public void setBaseRate(double baseRate) {
            this.baseRate = baseRate;
        }

        public double getMaxRate() {
            return maxRate;
        }

        public void setMaxRate(double maxRate) {
            this.maxRate = maxRate;
        }

        public int getMaxStack() {
            return maxStack;
        }

        public void setMaxStack(int maxStack) {
            this.maxStack = maxStack;
        }
    }


}
