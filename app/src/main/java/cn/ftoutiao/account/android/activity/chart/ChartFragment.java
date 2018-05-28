package cn.ftoutiao.account.android.activity.chart;

import cn.ftoutiao.account.android.base.BaseFragment;

/**
 * Author: weichyang
 * Date:   2018/2/8
 * Description: 图
 * 1.存储一个变量为什么不可用.基类中
 * <p>
 * tips：
 * 外部进行刷新，本地进行切换刷新
 * <p>
 * 1. 查询所有属于A的该账本的数据aid，按照月进行分组，（年，uid，aid group by month)
 * 2.查询该年所有账本总（支出/收入）
 * 3.查询（year,uid,month）总支出，count(*) 相同类别个数
 * 4. 2/3 占总的比例
 * <p>
 * 上一年的12月到这个月，默认显示当月
 * <p>
 * 5.当月完成单数，最大金额
 */
public class ChartFragment extends BaseFragment {



}
