package cn.qingsong.car.licenceplatekeyboard;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qingsong.car.keyboardlibrary.KeyboardInputController;
import cn.qingsong.car.keyboardlibrary.OnInputChangedListener;
import cn.qingsong.car.keyboardlibrary.PopupKeyboard;
import cn.qingsong.car.keyboardlibrary.view.InputView;
import cn.qingsong.car.licenceplatekeyboard.view.MyDialog;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.showDialog)
    Button showDialog;

    @BindView(R.id.showFragment)
    TextView showFragment;

    private MyDialog dialog;

    @BindView(R.id.inputView)
    InputView inputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 展示对话框
     */
    @OnClick(R.id.showDialog)
    void showDialog(View view) {
        dialog = new MyDialog();
        dialog.show(getSupportFragmentManager(), "myDialog");
    }

    private void initView() {
        // 创建弹出键盘
        final PopupKeyboard mPopupKeyboard = new PopupKeyboard(this);
        // 弹出键盘内部包含一个KeyboardView，在此绑定输入两者关联。
        mPopupKeyboard.attach(inputView, this);
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
                    mPopupKeyboard.dismiss(getWindow());
                }
            }

            @Override
            public void onCompleted(String number, boolean isAutoCompleted) {
                mPopupKeyboard.dismiss(getWindow());
                //此处支持模糊查询，所以可以不自动输入完成即可赋值
            }
        });
    }
}