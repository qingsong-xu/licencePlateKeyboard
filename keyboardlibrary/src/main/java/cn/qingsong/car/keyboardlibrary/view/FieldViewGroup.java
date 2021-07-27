package cn.qingsong.car.keyboardlibrary.view;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

import cn.qingsong.car.keyboardlibrary.R;

/**
 * @author 陈小锅 (yoojiachen@gmail.com)
 */
abstract class FieldViewGroup {

    private static final String TAG = "InputView.TextViewGroup";

    private final TextView[] mFieldViews = new TextView[8];

    public FieldViewGroup() {
        final int[] resIds = new int[]{
                R.id.et_car_license_inputbox1,
                R.id.et_car_license_inputbox2,
                R.id.et_car_license_inputbox3,
                R.id.et_car_license_inputbox4,
                R.id.et_car_license_inputbox5,
                R.id.et_car_license_inputbox6,
                R.id.et_car_license_inputbox7,
                R.id.et_car_license_inputbox8
        };
        for (int i = 0; i < resIds.length; i++) {
            mFieldViews[i] = findViewById(resIds[i]);
            mFieldViews[i].setTag("[RAW.idx:" + i + "]");
        }
        // 默认时，显示8位
        changeTo8Fields();
    }

    protected abstract TextView findViewById(int id);

    public void setTextToFields(String text) {
        // cleanup
        for (TextView f : mFieldViews) {
            f.setText(null);
        }

        final char[] chars = text.toCharArray();
        if (chars.length >= 8) {
            changeTo8Fields();
        } else {
            changeTo7Fields();
        }
        // 显示到对应键位
        final TextView[] fields = getAvailableFields();
        for (int i = 0; i < fields.length; i++) {
            final String txt;
            if (i < chars.length) {
                txt = String.valueOf(chars[i]);
            } else {
                txt = null;
            }
            fields[i].setText(txt);
        }
    }

    public TextView[] getAvailableFields() {
        final List<TextView> output = new ArrayList<>(8);
        final int lastIndex = mFieldViews.length - 1;
        TextView fieldView;
        for (int i = 0; i < mFieldViews.length; i++) {
            fieldView = mFieldViews[i];
            if (i != lastIndex || fieldView.getVisibility() == View.VISIBLE) {
                output.add(fieldView);
            }
        }
        return output.toArray(new TextView[output.size()]);
    }

    public TextView getFieldAt(int index) {
        return mFieldViews[index];
    }

    public boolean changeTo7Fields() {
        if (mFieldViews[7].getVisibility() != View.VISIBLE) {
            return false;
        }
        mFieldViews[7].setText(null);
        return true;
    }

    public boolean changeTo8Fields() {
        if (mFieldViews[7].getVisibility() == View.VISIBLE) {
            return false;
        }
        mFieldViews[7].setText(null);
        return true;
    }

    //设置间距
    public void setHorizontalPadding(float padding) {
        int halfPadding = (int) (padding + 0.5f);
        for (int i = 0; i < mFieldViews.length; i++) {
            TextView textView = mFieldViews[i];
            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) textView.getLayoutParams();
            //第一个省份
            if (i == 0) {
                lp.leftMargin = 0;
                lp.topMargin = 0;
                lp.rightMargin = halfPadding;
                lp.bottomMargin = 0;
            } else if (i == 7) {
                lp.leftMargin = halfPadding;
                lp.topMargin = 0;
                lp.rightMargin = 0;
                lp.bottomMargin = 0;
            } else {
                lp.leftMargin = halfPadding;
                lp.topMargin = 0;
                lp.rightMargin = halfPadding;
                lp.bottomMargin = 0;
            }
            textView.setLayoutParams(lp);
        }
    }

    public TextView getLastField() {
        if (mFieldViews[7].getVisibility() == View.VISIBLE) {
            return mFieldViews[7];
        } else {
            return mFieldViews[6];
        }
    }

    public TextView getFirstSelectedFieldOrNull() {
        for (TextView field : getAvailableFields()) {
            if (field.isSelected()) {
                return field;
            }
        }
        return null;
    }

    public TextView getLastFilledFieldOrNull() {
        final TextView[] fields = getAvailableFields();
        for (int i = fields.length - 1; i >= 0; i--) {
            if (!TextUtils.isEmpty(fields[i].getText())) {
                return fields[i];
            }
        }
        return null;
    }

    public TextView getFirstEmptyField() {
        final TextView[] fields = getAvailableFields();
        TextView out = fields[0];
        for (TextView field : fields) {
            out = field;
            final CharSequence keyTxt = field.getText();
            if (TextUtils.isEmpty(keyTxt)) {
                break;
            }
        }
        Log.d(TAG, "[-- CheckEmpty --]: Btn.idx: " + out.getTag() + ", Btn.text: " + out.getText() + ", Btn.addr: " + out);
        return out;
    }

    public int getNextIndexOfField(TextView target) {
        final TextView[] fields = getAvailableFields();
        for (int i = 0; i < fields.length; i++) {
            if (target == fields[i]) {
                return Math.min(fields.length - 1, i + 1);
            }
        }
        return 0;
    }

    public boolean isAllFieldsFilled() {
        for (TextView field : getAvailableFields()) {
            if (TextUtils.isEmpty(field.getText())) {
                return false;
            }
        }
        return true;
    }

    public String getText() {
        final StringBuilder sb = new StringBuilder();
        for (TextView field : getAvailableFields()) {
            sb.append(field.getText());
        }
        return sb.toString();
    }

    /**
     * 设置文字大小，单位sp
     *
     * @param size
     */
    public void setupAllFieldsTextSize(float size) {
        for (TextView field : mFieldViews) {
            field.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }

    public void setupAllFieldsOnClickListener(View.OnClickListener listener) {
        for (TextView field : mFieldViews) {
            field.setOnClickListener(listener);
        }
    }

    /**
     * 返回长度
     *
     * @return
     */
    public int getCount() {
        if (mFieldViews != null) {
            return mFieldViews.length;
        }
        return 0;
    }
}
