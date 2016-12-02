package com.example.etiennepinault.viewmodelpresenting;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import com.example.etiennepinault.viewmodelpresenting.commons.BaseActivity;
import rx.subscriptions.CompositeSubscription;

public class MainActivity
        extends BaseActivity<MainPresenter, MainViewModel> {

    @BindView(R.id.email) EditText emailView;
    @BindView(R.id.pass) EditText passView;
    @BindView(R.id.submitButton) Button submitView;
    @BindView(R.id.defaultButton) Button defaultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

    }

    @Override protected void subscribeForForeground(CompositeSubscription subscriptions) {
        super.subscribeForForeground(subscriptions);
        subscriptions.add((viewModel.submitButtonEnabled().subscribe(submitView::setEnabled)));
        subscriptions.add((viewModel.defaultEmailObs().subscribe(this::setEmailText)));
        subscriptions.add((viewModel.defaultPassObs().subscribe(this::setPassText)));
    }

    private void setEmailText(String text) {
        emailView.setText(text);
        emailView.setSelection(text.length());
    }

    private void setPassText(String text) {
        passView.setText(text);
        passView.setSelection(text.length());
    }
    @OnTextChanged(R.id.email) void onEmailChanged(CharSequence s) {
        presenter.emailChanged(s.toString());
    }

    @OnTextChanged(R.id.pass) void onPassChanged(CharSequence s) {
        presenter.passChanged(s.toString());
    }

    @OnClick(R.id.defaultButton) void defaultViewClicked() {
        presenter.defaultViewClicked();
    }

    @OnClick(R.id.submitButton) void submitViewClicked() {
        presenter.submitViewClicked();
    }

}
