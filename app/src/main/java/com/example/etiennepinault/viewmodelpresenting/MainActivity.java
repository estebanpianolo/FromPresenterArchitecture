package com.example.etiennepinault.viewmodelpresenting;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

import com.example.etiennepinault.viewmodelpresenting.commons.Activity;
import rx.subscriptions.CompositeSubscription;

public class MainActivity
        extends Activity<MainPresenter, MainViewModel> {

    @BindView(R.id.email) EditText emailView;
    @BindView(R.id.pass) EditText passView;
    @BindView(R.id.submitButton) Button submitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

    }

    @Override protected void subscribeForForeground(CompositeSubscription subscriptions) {
        super.subscribeForForeground(subscriptions);
        subscriptions.add((viewModel.submitButtonEnabled().subscribe(submitView::setEnabled)));
    }

    @OnTextChanged(R.id.email) void onEmailChanged(CharSequence s) {
        presenter.emailChanged(s.toString());
    }

    @OnTextChanged(R.id.pass) void onPassChanged(CharSequence s) {
        presenter.passChanged(s.toString());
    }
}
