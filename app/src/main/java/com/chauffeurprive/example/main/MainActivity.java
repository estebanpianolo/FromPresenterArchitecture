package com.chauffeurprive.example.main;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import com.chauffeurprive.core.mvp.BaseActivity;
import com.chauffeurprive.example.CustomApplication;
import com.chauffeurprive.example.R;
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

    @Override public void buildDependencies() {
        DaggerMainComponent.builder()
                .appComponent(((CustomApplication) getApplication()).getAppComponent())
                .mainModule(new MainModule())
                .build()
                .inject(this);
    }

    @Override public void subscribeForForeground(CompositeSubscription subscriptions) {
        super.subscribeForForeground(subscriptions);
        subscriptions.addAll(
                viewModel.submitButtonEnabled()
                        .subscribe(submitView::setEnabled),
                viewModel.defaultEmailObs()
                        .subscribe(this::setEmailText),
                viewModel.defaultPassObs()
                        .subscribe(this::setPassText)
        );
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
