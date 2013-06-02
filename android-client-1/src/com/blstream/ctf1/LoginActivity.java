package com.blstream.ctf1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.blstream.ctf1.asynchronous.Login;
import com.blstream.ctf1.asynchronous.Register;
import com.blstream.ctf1.service.NetworkService;
import com.blstream.ctf1.tracker.IssueTracker;

/**
 * @author Milosz_Skalski
 * @author Rafal_Olichwer
 */

public class LoginActivity extends Activity implements OnClickListener, OnTabChangeListener {
    private Button mBtnLogin;
    private EditText mEditLogin;
    private EditText mEditPassword;
    private TabHost mTabHost;
    private TabSpec mTab1, mTab2;
    private TextView mTabTextView;

    private Button mBtnRegister;
    private EditText mEditLoginReg;
    private EditText mEditPasswordReg;
    private EditText mEditPassword2Reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mTabHost = (TabHost) findViewById(R.id.tabHost);
        mTabHost.setup();
        mTabHost.getTabWidget().setDividerDrawable(R.drawable.tab_divider);
        mTab1 = setupTabHost(new TextView(this), R.string.logging);
        mTab2 = setupTabHost(new TextView(this), R.string.registration);
        mTabHost.setCurrentTabByTag(mTab1.getTag());
        mTabHost.setOnTabChangedListener(this);

        mBtnLogin = (Button) findViewById(R.id.btnLogin);
        mBtnLogin.setOnClickListener(this);

        mEditLogin = (EditText) findViewById(R.id.editLogin);
        mEditPassword = (EditText) findViewById(R.id.editPassword);
    }

    @Override
    public void onClick(View v) {
        String login;
        String password;
        String password2;
        switch (v.getId()) {
            case R.id.btnLogin:
                IssueTracker.saveClick(this, mBtnLogin);
                login = mEditLogin.getText().toString();
                password = mEditPassword.getText().toString();
                if (correctDataLogin(login, password)) {
                    if (NetworkService.isDeviceOnline(this)) {
                        Login loginTask = new Login(this, GameListActivity.class, login, password);
                        loginTask.execute();
                    } else {
                        Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.btnRegister:
                IssueTracker.saveClick(this, mBtnRegister);
                login = mEditLoginReg.getText().toString();
                password = mEditPasswordReg.getText().toString();
                password2 = mEditPassword2Reg.getText().toString();
                if (correctDataRegister(login, password, password2)) {
                    if (NetworkService.isDeviceOnline(this)) {
                        Register register = new Register(this, LoginActivity.class, login, password);
                        register.execute();
                    } else {
                        Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private boolean correctDataLogin(String login, String password) {
        String info = Constants.EMPTY_STRING;

        if (login.length() < Constants.MIN_SIZE_LOGIN) {
            info += getResources().getString(R.string.login_too_short);
        }

        if (password.length() < Constants.MIN_SIZE_PASSWORD) {
            if (!info.isEmpty())
                info += Constants.NEW_LINE;
            info += getResources().getString(R.string.password_too_short) + '\n';
        }

        if (info.isEmpty()) {
            return true;
        }

        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean correctDataRegister(String login, String password, String password2) {
        boolean result = false;
        String info = Constants.EMPTY_STRING;
        if (login.length() < Constants.MIN_SIZE_LOGIN) {
            info += getResources().getString(R.string.login_too_short);
        }

        if (password.length() < Constants.MIN_SIZE_PASSWORD) {
            if (!info.isEmpty())
                info += Constants.NEW_LINE;

            info += getResources().getString(R.string.password_too_short);
        }

        if (!password.equals(password2)) {
            if (!info.isEmpty())
                info += Constants.NEW_LINE;
            info += getResources().getString(R.string.passwords_not_equal);
        }

        if (info.isEmpty()) {
            result = true;
        } else {
            Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    private TabSpec setupTabHost(final View view, Integer tag) {
        View tabView = createTabView(mTabHost.getContext(), tag);
        TabSpec setContent = mTabHost.newTabSpec(tag.toString()).setIndicator(tabView)
                .setContent(new TabHost.TabContentFactory() {
                    public View createTabContent(String tag) {
                        return view;
                    }
                });
        mTabHost.addTab(setContent);
        return setContent;
    }

    private View createTabView(Context context, Integer text) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.tabs_bg, null);
        mTabTextView = (TextView) view.findViewById(R.id.tabsText);
        mTabTextView.setText(text);
        return view;
    }

    @Override
    public void onTabChanged(String tabId) {
        if (tabId == mTab1.getTag().toString()) {
            setContentView(R.layout.activity_login);
            mTabHost = (TabHost) findViewById(R.id.tabHost);
            mTabHost.setup();
            mTabHost.getTabWidget().setDividerDrawable(R.drawable.tab_divider);
            mTab1 = setupTabHost(new TextView(this), R.string.logging);
            mTab2 = setupTabHost(new TextView(this), R.string.registration);
            mTabHost.setCurrentTabByTag(mTab1.getTag());
            mTabHost.setOnTabChangedListener(this);

            mBtnLogin = (Button) findViewById(R.id.btnLogin);
            mBtnLogin.setOnClickListener(this);
            mEditLogin = (EditText) findViewById(R.id.editLogin);
            mEditPassword = (EditText) findViewById(R.id.editPassword);
        }
        if (tabId == mTab2.getTag().toString()) {
            setContentView(R.layout.activity_register);
            mTabHost = (TabHost) findViewById(R.id.tabHost);
            mTabHost.setup();
            mTabHost.getTabWidget().setDividerDrawable(R.drawable.tab_divider);
            mTab1 = setupTabHost(new TextView(this), R.string.logging);
            mTab2 = setupTabHost(new TextView(this), R.string.registration);
            mTabHost.setCurrentTabByTag(mTab2.getTag());
            mTabHost.setOnTabChangedListener(this);

            mBtnRegister = (Button) findViewById(R.id.btnRegister);
            mBtnRegister.setOnClickListener(this);

            mEditLoginReg = (EditText) findViewById(R.id.editLoginReg);
            mEditPasswordReg = (EditText) findViewById(R.id.editPasswordReg);
            mEditPassword2Reg = (EditText) findViewById(R.id.editPassword2Reg);
        }
    }
}
