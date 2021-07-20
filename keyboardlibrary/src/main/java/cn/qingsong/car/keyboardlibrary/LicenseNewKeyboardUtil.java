//package cn.qingsong.car.keyboardlibrary;
//
//import android.app.Activity;
//import android.content.Context;
//import android.inputmethodservice.Keyboard;
//import android.inputmethodservice.KeyboardView;
//import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.TextView;
//
//public class LicenseNewKeyboardUtil {
//    private Context ctx;
//    private KeyboardView keyboardView;
//    private Keyboard k1;// 省份简称键盘
//    private Keyboard k2;// 数字字母键盘
//
//    private IHandleCurSign handleCurSign;
//
//    private String provinceShort[];
//    private String letterAndDigit[];
//
//    private TextView edits[];
//    private int currentEditText = 0;// 默认当前光标在第一个EditText
//
//    public LicenseNewKeyboardUtil(Context ctx, TextView edits[]) {
//        this.ctx = ctx;
//        this.edits = edits;
//        k1 = new Keyboard(ctx, R.xml.province_short_keyboard);
//        k2 = new Keyboard(ctx, R.xml.lettersanddigit_keyboard);
//
//        keyboardView = (KeyboardView) ((Activity) ctx).findViewById(R.id.keyboard_view);
//        keyboardView.setKeyboard(k1);
//        keyboardView.setEnabled(true);
//        // 设置为true时,当按下一个按键时会有一个popup来显示<key>元素设置的android:popupCharacters=""
//        keyboardView.setPreviewEnabled(false);
//        // 设置键盘按键监听器
//        keyboardView.setOnKeyboardActionListener(listener);
//        provinceShort = new String[]{
//                "京", "津", "渝", "沪", "冀", "晋", "辽", "吉", "黑", "苏",
//                "浙", "皖", "闽", "赣", "鲁", "豫", "鄂", "湘", "粤", "琼",
//                "川", "贵", "云", "陕", "甘", "青", "蒙", "桂", "宁", "新",
//                "台", "藏", "使", "领", "警", "港", "澳", "挂"};
//
//        letterAndDigit = new String[]{
//                "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
//                "Q", "W", "E", "R", "T", "Y", "U", "学", "警", "P",
//                "A", "S", "D", "F", "G", "H", "J", "K", "L",
//                "Z", "X", "C", "V", "B", "N", "M"};
//        handleSign(0);
//        for (int i = 0; i < edits.length; i++) {
//            final int pos = i;
//            edits[i].setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    switch (pos) {
//                        case 0:// 省份
//                            handleCurSign.currentText(0);
//                            currentEditText = 0;
//                            keyboardView.setKeyboard(k1);
//                            handleSign(currentEditText);
//                            break;
//
//                        case 1:
//                            handleCurSign.currentText(1);
//                            currentEditText = 1;
//                            keyboardView.setKeyboard(k2);
//                            handleSign(currentEditText);
//                            break;
//
//                        case 2:
//                            handleCurSign.currentText(2);
//                            currentEditText = 2;
//                            keyboardView.setKeyboard(k2);
//                            handleSign(currentEditText);
//                            break;
//
//                        case 3:
//                            handleCurSign.currentText(3);
//                            currentEditText = 3;
//                            keyboardView.setKeyboard(k2);
//                            handleSign(currentEditText);
//                            break;
//
//                        case 4:
//                            handleCurSign.currentText(4);
//                            currentEditText = 4;
//                            keyboardView.setKeyboard(k2);
//                            handleSign(currentEditText);
//                            break;
//
//                        case 5:
//                            handleCurSign.currentText(5);
//                            currentEditText = 5;
//                            keyboardView.setKeyboard(k2);
//                            handleSign(currentEditText);
//                            break;
//
//                        case 6:
//                            handleCurSign.currentText(6);
//                            currentEditText = 7;
//                            keyboardView.setKeyboard(k2);
//                            handleSign(currentEditText);
//                            break;
//
//                        case 7:
//                            handleCurSign.currentText(7);
//                            currentEditText = 7;
//                            keyboardView.setKeyboard(k2);
//                            handleSign(currentEditText);
//                            break;
//                    }
//
//                }
//            });
//        }
//    }
//
//    private OnKeyboardActionListener listener = new OnKeyboardActionListener() {
//        @Override
//        public void swipeUp() {
//        }
//
//        @Override
//        public void swipeRight() {
//        }
//
//        @Override
//        public void swipeLeft() {
//        }
//
//        @Override
//        public void swipeDown() {
//        }
//
//        @Override
//        public void onText(CharSequence text) {
//        }
//
//        @Override
//        public void onRelease(int primaryCode) {
//
//        }
//
//        @Override
//        public void onPress(int primaryCode) {
//        }
//
//        @Override
//        public void onKey(int primaryCode, int[] keyCodes) {
//            if (primaryCode == 112) { // xml中定义的删除键值为112
//                edits[currentEditText].setText("");// 将当前EditText置为""并currentEditText-1
//                currentEditText--;
//                if (currentEditText < 1) {
//                    // 切换为省份简称键盘
//                    keyboardView.setKeyboard(k1);
//                }
//                if (currentEditText < 0) {
//                    currentEditText = 0;
//                }
//                if (handleCurSign != null) {
//                    handleCurSign.currentText(currentEditText);
//                }
//                handleSign(currentEditText);
//            } else if (primaryCode == 66) { // xml中定义的完成键值为66
//
//            } else { // 其它字符按键
//                if (currentEditText == 0) {
//                    // 如果currentEditText==0代表当前为省份键盘,
//                    // 按下一个按键后,设置相应的EditText的值
//                    // 然后切换为字母数字键盘
//                    // currentEditText+1
//                    edits[0].setText(provinceShort[primaryCode]);
//                    currentEditText = 1;
//                    keyboardView.setKeyboard(k2);
//                    handleSign(currentEditText);
//                } else {
//                    // 第二位必须大写字母
//                    if (currentEditText == 1 && !letterAndDigit[primaryCode].matches("[A-Z]{1}")) {
//                        return;
//                    }
//                    if ("O".equals(letterAndDigit[primaryCode]) || "I".equals(letterAndDigit[primaryCode])) {
//                        return;
//                    }
//                    edits[currentEditText].setText(letterAndDigit[primaryCode]);
//                    currentEditText++;
//                    if (currentEditText > 7) {
//                        currentEditText = 7;
//                        if (handleCurSign != null) {
//                            handleCurSign.currentText(currentEditText);
//                        }
//                    }
//                    handleSign(currentEditText);
//                }
//            }
//        }
//    };
//
//    private void handleSign(int curSign) {
//        for (int i = 0; i <= 7; i++) {
//            if (i == curSign) {
//                edits[i].setSelected(true);
//            } else {
//                edits[i].setSelected(false);
//            }
//        }
//    }
//
//    public String getInputLisenceNumber() {
//        String lisenceNumber = "";
//        for (int i = 0; i < edits.length; i++) {
//            lisenceNumber = lisenceNumber + edits[i].getText().toString().trim();
//        }
//
//        return lisenceNumber;
//    }
//
//    /**
//     * 显示键盘
//     */
//    public void showKeyboard() {
//        int visibility = keyboardView.getVisibility();
//        if (visibility == View.GONE || visibility == View.INVISIBLE) {
//            keyboardView.setVisibility(View.VISIBLE);
//        }
//    }
//
//    /**
//     * 隐藏键盘
//     */
//    public void hideKeyboard() {
//        int visibility = keyboardView.getVisibility();
//        if (visibility == View.VISIBLE) {
//            keyboardView.setVisibility(View.INVISIBLE);
//        }
//    }
//
//    public void setHandleCurSign(IHandleCurSign handleCurSign) {
//        this.handleCurSign = handleCurSign;
//    }
//
//    public interface IHandleCurSign {
//        void currentText(int cur);
//    }
//}