package cn.ftoutiao.account.android.component.event;

/**
 * Created by alan on 2017/9/28.
 *
 * 事件类
 *
 */

public class RxBusEvent {


//    public class TestEvent {
//
//    }

    private RxBusEvent() {

    }

    /**
     * 获取具体类型的Event
     *
     * @param o
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> T getEvent(Object o) {
        return (T) o;
    }

    /**
     * 判断事件是否是所需类型
     *
     * @param event
     * @param clz
     * @return
     */
    public static boolean isEventInstanceOf(Object event, Class<?> clz) {
        return clz.isInstance(event);
    }

//    public void test(Object event) {
//        if (isEventInstanceOf(event, TestEvent.class)) {
//            TestEvent testEvent = getEvent(event);
//        }
//    }

}
