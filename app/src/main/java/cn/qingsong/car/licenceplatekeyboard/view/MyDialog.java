package cn.qingsong.car.licenceplatekeyboard.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import cn.qingsong.car.licenceplatekeyboard.R;

public class MyDialog extends DialogFragment {

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

        return view;
    }

}
