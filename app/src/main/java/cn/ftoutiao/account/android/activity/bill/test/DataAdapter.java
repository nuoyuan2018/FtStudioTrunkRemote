package cn.ftoutiao.account.android.activity.bill.test;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acmenxd.recyclerview.delegate.ViewHolder;

import java.util.List;

import cn.ftoutiao.account.android.R;
import cn.ftoutiao.account.android.constants.ConstanPool;
import cn.ftoutiao.account.android.db.ListItemDB;
import cn.ftoutiao.account.android.model.db.ListItemBO;
import cn.ftoutiao.account.android.utils.FormatUtil;
import cn.ftoutiao.account.android.utils.StringUtil;
import cn.ftoutiao.account.android.utils.TimeFormat;
import cn.ftoutiao.account.android.widget.progressbar.NumberProgressBar;

/**
 * 类描述：适配器
 * 创建人：zz
 * 创建时间：2017/8/9 16:55
 */
public class DataAdapter extends BaseExpandableListAdapter {
    private List<GroupBean> mDatas;
    private Context context;
    private LayoutInflater inflater;


    public DataAdapter(List<GroupBean> datas, Context context) {
        this.mDatas = datas;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDatas.get(groupPosition).childs.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        View viewHolder = null;
        if (convertView != null) {
            viewHolder = convertView;
        } else {
            viewHolder = inflater.inflate(R.layout.child, null);
        }
        ListItemBO listItemBO = mDatas.get(groupPosition).childs.get(childPosition);

        if (listItemBO.aId == null && listItemBO.year == null) {
            viewHolder.findViewById(R.id.txt_holder).setVisibility(View.VISIBLE);
            viewHolder.findViewById(R.id.rel_content).setVisibility(View.GONE);
            return viewHolder;
        } else {
            viewHolder.findViewById(R.id.txt_holder).setVisibility(View.GONE);
            viewHolder.findViewById(R.id.rel_content).setVisibility(View.VISIBLE);
        }
        TextView textCname = viewHolder.findViewById(R.id.txt_cname);
        TextView textRemark = viewHolder.findViewById(R.id.txt_remark);
        TextView textAmount = viewHolder.findViewById(R.id.txt_amount);
        RelativeLayout floatTip = viewHolder.findViewById(R.id.real_float);
        TextView txt_date = viewHolder.findViewById(R.id.txt_date);
        TextView group_title = viewHolder.findViewById(R.id.group_title);
        TextView txt_right = viewHolder.findViewById(R.id.txt_right);
        View view_divide = viewHolder.findViewById(R.id.view_divide);

        //View view_divide_bottom = viewHolder.getView(R.id.view_divide_bottom);
        // view_divide_bottom.setVisibility(View.GONE);
        if (childPosition == 0) {
            floatTip.setVisibility(View.VISIBLE);
            view_divide.setVisibility(View.GONE);
        } else {
            if (listItemBO.day.equals(mDatas.get(groupPosition).childs.get(childPosition - 1).day)) {
                floatTip.setVisibility(View.GONE);
                view_divide.setVisibility(View.VISIBLE);
            } else {
                view_divide.setVisibility(View.GONE);
                floatTip.setVisibility(View.VISIBLE);
            }
        }
        if (floatTip.getVisibility() == View.VISIBLE) {
            txt_date.setText(TimeFormat.mill2Weekdate(listItemBO.aDate));
            float[] state = ListItemDB.getInstance().queryInput2OutputByDay(
                    listItemBO.day, listItemBO.month, listItemBO.year, listItemBO.aId);
            group_title.setText("支出：" + FormatUtil.getDecimalMoney2(String.valueOf(state[1]), 2));
            txt_right.setText("收入：" + FormatUtil.getDecimalMoney2(String.valueOf(state[0]), 2));
        }

        if (!StringUtil.isEmpty(listItemBO.remark)) {
            textRemark.setVisibility(View.VISIBLE);
            textRemark.setText(StringUtil.formatString(ConstanPool.TEXT_LENTH, listItemBO.remark));
        } else {
            textRemark.setVisibility(View.GONE);
            textRemark.setText("");
        }
        textCname.setText(listItemBO.cName);
        textAmount.setText((listItemBO.cType == 1 ? "+" : "-") + FormatUtil.getDecimalMoney2(String.valueOf(listItemBO.amount), 2));
        textAmount.setTextColor(context.getResources().getColor(listItemBO.cType == 1 ? R.color.color_33c58d : R.color.color_9e8bbb));

        return viewHolder;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition < 0)
            return 0;
        return mDatas.get(groupPosition).childs.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mDatas.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mDatas.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        View view = null;
        //普通分组
        if (convertView != null && (Integer) convertView.getTag() == 0) {
            view = convertView;
        } else {
            view = inflater.inflate(R.layout.group, null);
            view.setTag(0);
        }
        ViewHolder viewHolder = new ViewHolder(context, view);
        TextView month = viewHolder.getView(R.id.txt_month);
        TextView year = viewHolder.getView(R.id.txt_year);
        TextView txtInputTotal = viewHolder.getView(R.id.txt_input_total);
        TextView txtOutputTotal = viewHolder.getView(R.id.txt_output_total);
        TextView txtDifTotal = viewHolder.getView(R.id.txt_dif_total);
        ImageView imgArrow = viewHolder.getView(R.id.img_arrow);
        NumberProgressBar pro_output = viewHolder.getView(R.id.pro_output);

        NumberProgressBar pro_input = viewHolder.getView(R.id.pro_input);
        //View view_divide = viewHolder.getView(R.id.view_divide);
        GroupBean groupBean = mDatas.get(groupPosition);
        pro_input.setProgresOnlyDrawReached(true);
        pro_input.setProgresslenth(400);
        pro_output.setProgresOnlyDrawReached(true);
        pro_output.setProgresslenth(400);
        pro_output.setMax((int) groupBean.allOutTotal);
        pro_input.setMax((int) groupBean.allInputTotal);
        /**
         * 0.如果双方均为0，则展示参数字体
         * 00.如果一方为0，则展示末一方彩色。
         * 1.当前total  max  totalInput===> max
         * 2.当前占用比例，total,小于比例的总长度1.5倍数。== 1.5 倍数
         * 3.如果小于1.5倍数，设置字体变色
         */
        year.setText(groupBean.year);
        month.setText(Integer.valueOf(groupBean.month) >= 10 ? groupBean.month : "0" + groupBean.month);
//        String inputValue = "收入 ￥" + FormatUtil.afterdecimaldouble(String.valueOf(groupBean.inputTotal));
//        String outPutValue = "支出 ￥" + FormatUtil.afterdecimaldouble(String.valueOf(groupBean.outputTotal));
        pro_input.setProgress((int) groupBean.inputTotal);
        pro_output.setProgress((int) groupBean.outputTotal);
        String inputValue = "收入 ￥" + FormatUtil.afterdecimaldouble(String.valueOf(groupBean.inputTotal));
        String outPutValue = "支出 ￥" + FormatUtil.afterdecimaldouble(String.valueOf(groupBean.outputTotal));
        txtInputTotal.setText(inputValue);
        txtOutputTotal.setText(outPutValue);
        if (groupBean.inputTotal == 0 && groupBean.outputTotal == 0) {


        } else {
            if (groupBean.outputTotal > 0) {
                float baseProgress = groupBean.allOutTotal / 10 * 1.2f;
                float difMax = Math.max(baseProgress, groupBean.outputTotal);
                if (baseProgress == difMax) {
                    pro_output.setProgress((int) baseProgress);
                    txtOutputTotal.setText(
                            StringUtil.makeAprStr(
                                    outPutValue,
                                    3,
                                    outPutValue.length(),
                                    9,
                                    "#9e8bbb"));
                }
            }
            if (groupBean.inputTotal > 0) {
                float baseProgress = groupBean.allInputTotal / 10 * 1.2f;
                float difMax = Math.max(baseProgress, groupBean.inputTotal);
                if (baseProgress == difMax) {
                    pro_input.setProgress((int) baseProgress);
                    txtInputTotal.setText(StringUtil.makeAprStr(
                            inputValue,
                            3,
                            inputValue.length(),
                            9,
                            "#33c58d"));
                }
            }
        }
        txtInputTotal.setTextColor(context.getResources().getColor(groupBean.inputTotal == 0 ? R.color.color_33c58d : R.color.white));
        txtOutputTotal.setTextColor(context.getResources().getColor(groupBean.outputTotal == 0 ? R.color.color_9e8bbb : R.color.white));
        txtDifTotal.setText("￥" +
                FormatUtil.afterdecimaldouble(String.valueOf(groupBean.difInputOrOutPut)));
        txtDifTotal.setTextColor(context.getResources().getColor(groupBean.difInputOrOutPut >= 0 ? R.color.color_33c58d : R.color.color_9e8bbb));

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
