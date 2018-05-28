package cn.ftoutiao.account.android.component.fragment

/**
 * Created by alan on 2017/9/29.
 */

interface BookFragmentListener {

    /**
     * 返回fragment对应的tag
     *
     * @return
     */
    val tagName: String

    /**
     * 返回fragment的container id
     *
     * @return
     */
    val containerId: Int

    /**
     * 返回fragment不可见时的状态，默认隐藏
     *
     * @return
     */
    val disappearFlag: Int

    /**
     * 显示
     */
    fun onShow()

    /**
     * 隐藏
     */
    fun onHide()

    companion object {

        /**
         * 当显示其他fragment的时候隐藏当前的fragment
         */
        val FLAG_HIDE = 0

        /**
         * 当显示其他fragment的时候移除当前的fragment
         */
        val FLAG_REMOVE = 1

        /**
         * 当显示其他fragment的时候,当前fragment保持不变
         */
        val FLAG_KEEP = 2
    }

}
