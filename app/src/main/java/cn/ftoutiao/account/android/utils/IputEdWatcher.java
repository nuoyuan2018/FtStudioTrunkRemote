package cn.ftoutiao.account.android.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;



public class IputEdWatcher implements TextWatcher {
    private EditText editText;
    private Button button;
    private int beforelen = 0;
    private int afterlen = 0;
    private int hasbank;
    private String leftMoney = "";
    private int conAfter;
    private boolean isRecharge;
    private ImageView img_clear;
    private TextChangeCallBack textChangeCallBack;
    private String reportContent = "";
    private int type = -1; // 0.实名绑卡充值-充值页 1.充值独立页

    public IputEdWatcher(ImageView img_clear, EditText editText, Button btn, int hasBankCard) {
        this.editText = editText;
        this.button = btn;
        this.hasbank = hasBankCard;
        this.img_clear = img_clear;
        isRecharge = false;
    }

    public void setHasCard(int hasBankCard) {
        this.hasbank = hasBankCard;
    }

    public void setLeftMoney(String leftMoney) {
        this.leftMoney = leftMoney;
    }

    public void setIsRecharge() {
        isRecharge = true;
    }

    @Override
    public void afterTextChanged(Editable s) {
        String contents = editText.getText().toString().trim();


        if (isRecharge) {
            if (contents.length() == 1 && contents.equals(".")) {
                editText.setText("");
                return;
            }
        }
        afterlen = contents.length();
        if (StringUtil.isNotEmpty(contents)) {
            img_clear.setVisibility(View.VISIBLE);
        } else {
            img_clear.setVisibility(View.INVISIBLE);
        }
        /*
         * 2. 0开始的时候可以输入“.”，但是限制小数最多两位 3. startWith!="." 4. 小数输入满两位，光标置到最后 5.
		 * 有“.”时，小数最多两位，整数最多8位
		 */

        if (afterlen > beforelen) {
            if (contents.startsWith(".")) {
                editText.setText("");
                button.setEnabled(true);
                return;
            }

            // 1. 没有 “.”，整数最多8位
            if (contents.contains(".")) {
                String[] cons = contents.split("\\.");
                if (cons.length > 1 && cons[1].length() > 2) {
                    contents = cons[0] + "." + cons[1].substring(0, 2);
                    editText.setText(contents);
                    editText.setSelection(contents.length());
                    return;
                }
//				if (cons.length > 1) {
//					if (cons[1].length() > conAfter) {
//						cons[1] = cons[1].substring(0, 2);
//						contents = cons[0] + "." + cons[1];
//						editText.setText(contents);
//						conAfter = cons[1].length();
//						editText.setSelection(contents.length());
//						return;
//					}
//				}

                if (cons[0].length() > 8) {
                    cons[0] = cons[0].substring(0, 8);
                }

                if (afterlen > 10 && cons.length > 1) {
                    contents = cons[0] + "." + cons[1];
                    editText.setText(contents);
                    editText.setSelection(contents.length());
                    return;
                }

            } else {

                if (contents.startsWith("0")) {
                    if (contents.length() > 1) {//0开始输入的时候输入 点则走if的小数规则，若非输入 点，则走正常整数,并清除0
                        contents = contents.substring(0, contents.length() - 1);
                        editText.setText(contents);
//                        button.setEnabled(true);
//                        editText.setSelection(1);
//                        return;
                    }
                }

                if (afterlen > 8) {
                    contents = contents.substring(0, 8);
                    editText.setText(contents);
                    editText.setSelection(contents.length());
                    return;
                }
            }

        }

        if (contents.length() > 0 && hasbank != 0 && !contents.startsWith("0")) {
            button.setEnabled(true);
        } else {
            button.setEnabled(true);
        }
        // 2016/7/11 修改 新逻辑
        if (!isRecharge) {
            if (contents.startsWith("0")) {
                button.setEnabled(true);
            }
        }
        //进行兼容处理
        // this is recharge deal btn
//        if (isRecharge && hasbank != 0) {
        if (isRecharge && (hasbank != 0 || (contents.length() > 0 && !contents.startsWith("0")))) {
            // TODO: 2016/7/11  修改
            if (contents.length() < 1 || contents.equals("0")) {
                button.setEnabled(true);
            } else {
                button.setEnabled(true);
            }
        }
        if (!reportContent.isEmpty() && s.length() == 0) {
            reportContent = "";
        }

        if (contents.length() > 0) button.setEnabled(true);
        else button.setEnabled(false);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

        beforelen = s.length();
        if (reportContent.isEmpty()) {
            reportContent = "b";
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (textChangeCallBack != null) {
            textChangeCallBack.onTextChanged(s);
        }
    }

    private String handleFormatMoney(String contents) {
        // 处理输入金额返回format后的金额
        long v = Long.valueOf(contents);
        contents = StringUtil.formatMoney(v);
        return contents;
    }


    public TextChangeCallBack getTextChangeCallBack() {
        return textChangeCallBack;
    }

    public void setTextChangeCallBack(TextChangeCallBack textChangeCallBack) {
        this.textChangeCallBack = textChangeCallBack;
    }

    public interface TextChangeCallBack {
        void onTextChanged(CharSequence s);

    }

    /**
     * 设置页面
     *
     * @param type
     * @return
     */
    public void setPageType(int type) {
        this.type = type;
    }


}
