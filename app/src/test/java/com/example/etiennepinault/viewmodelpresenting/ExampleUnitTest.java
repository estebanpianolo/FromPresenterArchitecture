package com.example.etiennepinault.viewmodelpresenting;

import com.example.etiennepinault.viewmodelpresenting.interactors.TimeOutInteractor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Mock TimeOutInteractor interactor;
    @Mock MainViewModel viewModel;

    @InjectMocks MainPresenter presenter;

    @Before
    public void prepareTests() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fields_filled_correctly() throws Exception {

        presenter.emailChanged("coucou");
        verifyZeroInteractions(viewModel);
        presenter.passChanged("dsgvsdg");
        verify(viewModel).setSubmitButtonEnabled(true);
    }

    @Test
    public void fields_filled_incorrectly() throws Exception {

        presenter.emailChanged("coucou");
        verifyZeroInteractions(viewModel);
        presenter.passChanged("dsgvsdg");
        verify(viewModel).setSubmitButtonEnabled(true);
        presenter.emailChanged("");
        verify(viewModel).setSubmitButtonEnabled(false);
    }

    @Test
    public void click_on_submit() {

        given(interactor.setTimeout(1)).willReturn(interactor);

        given(interactor.execute()).willReturn(Observable.empty());
        presenter.submitViewClicked();
        verify(interactor).setTimeout(1);
        verify(viewModel).setDefaultEmail("");
        verify(viewModel).setDefaultPass("");

    }

    @Test
    public void click_on_default_view() {

        presenter.defaultViewClicked();
        verify(viewModel).setDefaultEmail("etienne@test.cp");
        verify(viewModel).setDefaultPass("coucou");
    }

    @Test
    public void clear_fields() {

    }



}