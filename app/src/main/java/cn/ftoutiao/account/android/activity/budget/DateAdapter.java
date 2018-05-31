package cn.ftoutiao.account.android.activity.budget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import cn.ftoutiao.account.android.R;

/**
 * @author XuHuiHao
 *         日期adapter
 *         Created on 2018/1/31 0031.
 */

public class DateAdapter extends MyBaseAdapter<DateAdapter.ViewHolder> {
    public List<BudgetBean.BodyBean> beanList ;
    public Context context;
    public int count;
    public DateAdapter(int count, List list, Context context) {
        super();
        this.count = count;
        this.beanList = list;
        this.context = context;
    }
    /**
     * 填充数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onHolder(ViewHolder holder, int position) {
        holder.tvItemMonth.setText(beanList.get(position).getMonth()+beanList.get(position).getYear());
        holder.tvItemMoney.setText(beanList.get(position).getMoney());
    }

    /**
     * 生成ViewHolder
     *
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder() {
        View view = LayoutInflater.from(context).inflate(R.layout.item_date, null);
        return new ViewHolder(view);
    }
    public class ViewHolder extends BaseViewHolder{
        public View view;
        public TextView tvItemMonth;
        public TextView tvItemMoney;
        public ViewHolder(View view){
            this.view=view;
            this.tvItemMonth =  view.findViewById(R.id.tv_item_month);
            this.tvItemMoney = view.findViewById(R.id.tv_item_money);
        }
        /**
         * 用来返回 view
         *
         * @return
         */
        @Override
        public View createView() {
            return view;
        }
    }
}
