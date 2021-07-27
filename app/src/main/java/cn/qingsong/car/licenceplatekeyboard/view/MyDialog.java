package cn.qingsong.car.licenceplatekeyboard.view;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.qingsong.car.keyboardlibrary.OnInputChangedListener;
import cn.qingsong.car.keyboardlibrary.PopupKeyboard;
import cn.qingsong.car.keyboardlibrary.view.InputView;
import cn.qingsong.car.licenceplatekeyboard.R;

public class MyDialog extends DialogFragment {
    protected Unbinder unbinder;
    @BindView(R.id.inputView)
    InputView inputView;

    @BindView(R.id.inputView1)
    InputView inputView1;

    @BindView(R.id.close)
    ImageView ivClose;

    public MyDialog() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_my, null);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);

        //去掉dialog的标题，需要在setContentView()之前
//        this.getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = this.getDialog().getWindow();
        //去掉dialog默认的padding
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        //设置dialog的位置在底部
        lp.gravity = Gravity.CENTER;
        //设置dialog的动画
//        lp.windowAnimations = R.style.BottomDialogAnimation;
        window.setAttributes(lp);
        window.setBackgroundDrawable(new ColorDrawable());
        unbinder = ButterKnife.bind(this, view);
        initKeyBoard(inputView);
        initKeyBoard(inputView1);
        return view;
    }

    private void initKeyBoard(InputView inputView) {
        // 创建弹出键盘
        final PopupKeyboard mPopupKeyboard = new PopupKeyboard(getActivity());
        // 弹出键盘内部包含一个KeyboardView，在此绑定输入两者关联。
        mPopupKeyboard.attach(inputView, getDialog());
        //隐藏确定键
        mPopupKeyboard.getKeyboardEngine().setHideOKKey(false);
        mPopupKeyboard.getKeyboardEngine().setOkEnabled(true);
        mPopupKeyboard.getKeyboardEngine().setLocalProvinceName("贵州省");
        // KeyboardInputController提供一个默认实现的新能源车牌锁定按钮
        mPopupKeyboard.getController()
                .setDebugEnabled(true);

        mPopupKeyboard.getController().addOnInputChangedListener(new OnInputChangedListener() {
            @Override
            public void onChanged(String number, boolean isCompleted) {
                if (isCompleted) {
                    mPopupKeyboard.dismiss(getDialog().getWindow());
                }
            }

            @Override
            public void onCompleted(String number, boolean isAutoCompleted) {
                mPopupKeyboard.dismiss(getDialog().getWindow());
                //此处支持模糊查询，所以可以不自动输入完成即可赋值
            }
        });

        ivClose.setOnClickListener(v -> {
            dismiss();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
