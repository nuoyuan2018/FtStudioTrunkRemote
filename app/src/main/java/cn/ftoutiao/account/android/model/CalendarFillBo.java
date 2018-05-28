//package cn.ftoutiao.account.android.model;
//
///**
// * Created by weichyang on 2018/4/8.
// * 配合日历展示当天收入、支出总数
// */
//
//public class CalendarFillBo implements Cloneable {
//    public int tagDay; //添加标记
//    public String inputTotal = "0";//当天收入总数
//    public String outTotal = "0"; //当天支出总数
//
//    @Override
//    public CalendarFillBo clone() {
//        CalendarFillBo bo = null;
//        try {
//            bo = (CalendarFillBo) super.clone();
//        } catch (CloneNotSupportedException e) {
//            e.printStackTrace();
//        }
//        return bo;
//    }
//
//    @Override
//    public String toString() {
//        return "CalendarFillBo{" +
//                "tagDay=" + tagDay +
//                ", inputTotal='" + inputTotal + '\'' +
//                ", outTotal='" + outTotal + '\'' +
//                '}';
//    }
//}
