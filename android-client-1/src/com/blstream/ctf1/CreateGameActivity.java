package com.blstream.ctf1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blstream.ctf1.asynchronous.CreateGame;
import com.blstream.ctf1.asynchronous.EditGame;
import com.blstream.ctf1.asynchronous.EditGameDetails;
import com.blstream.ctf1.domain.GameExtendedInfo;
import com.blstream.ctf1.domain.GameStatusType;
import com.blstream.ctf1.domain.Localization;
import com.blstream.ctf1.pickers.DatePickerFragment;
import com.blstream.ctf1.pickers.TimePickerFragment;
import com.blstream.ctf1.service.NetworkService;
import com.blstream.ctf1.tracker.IssueTracker;
import com.google.android.gms.maps.model.LatLng;

/**
 * @author Milosz_Skalski
 */

public class CreateGameActivity extends FragmentActivity implements OnClickListener {

    private Button mBtnCancel;
    private Button mBtnCreate;
    public Button mBtnStartDate;
    public Button mBtnStartTime;
    private Button mBtnMap;
    public EditText mEditGameName;
    public EditText mEditGameDescription;
    public EditText mEditLocationName;
    public EditText mEditPlayingTime;
    public EditText mEditMaxPlayers;
    public EditText mEditMaxPoints;
    public EditText mEditRedNameBase;
    public EditText mEditBlueNameBase;

    // default values
    public double mLatitude = 53.432766;
    public double mLongitude = 14.548001;
    public double mRadius = 1000;
    public double mLatitudeRed = 53.432;
    public double mLongitudeRed = 14.547;
    public double mLatitudeBlue = 53.433;
    public double mLongitudeBlue = 14.549;

    private String mInfo;
    private String mId;
    private int mCode = 1;

    Handler handlerTime = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String time = msg.getData().getString("time");
            String data = msg.getData().getString("data");
            if (!(time == null)) {
                Log.d("CTF ", "CTF czas:" + time);
                mBtnStartTime.setText(time);
            }
            if (!(data == null)) {
                Log.d("CTF ", "CTF data:" + data);
                mBtnStartDate.setText(data);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        mBtnCancel = (Button) findViewById(R.id.btnCancel);
        mBtnCreate = (Button) findViewById(R.id.btnCreate);
        mBtnStartDate = (Button) findViewById(R.id.btnStartDate);
        mBtnStartTime = (Button) findViewById(R.id.btnStartTime);
        mBtnMap = (Button) findViewById(R.id.btnMap);
        mBtnCancel.setOnClickListener(this);
        mBtnCreate.setOnClickListener(this);
        mBtnStartDate.setOnClickListener(this);
        mBtnStartTime.setOnClickListener(this);
        mBtnMap.setOnClickListener(this);

        mEditGameName = (EditText) findViewById(R.id.editGameName);
        mEditGameDescription = (EditText) findViewById(R.id.editGameDescription);
        mEditLocationName = (EditText) findViewById(R.id.editLocationName);
        mEditPlayingTime = (EditText) findViewById(R.id.editPlayingTime);
        mEditMaxPlayers = (EditText) findViewById(R.id.editMaxPlayers);
        mEditMaxPoints = (EditText) findViewById(R.id.editMaxPoints);
        mEditRedNameBase = (EditText) findViewById(R.id.editRedBaseName);
        mEditBlueNameBase = (EditText) findViewById(R.id.editBlueBaseName);

        Intent intent = getIntent();
        mId = intent.getStringExtra(Constants.EXTRA_KEY_ID);
        if (mId == null) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            int hour = (c.get(Calendar.HOUR_OF_DAY) + 2) % 24;
            int minute = 0;
            mBtnStartTime.setText(hour + ":" + minute + ":00");
            mBtnStartDate.setText(year + "-" + (month + 1) + "-" + day);
            mBtnCreate.setText(R.string.create_game);
        } else {
            mBtnCreate.setText(R.string.edit_game);
            EditGameDetails editGameDetails = new EditGameDetails(this, mId);
            editGameDetails.execute();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnCancel:
                IssueTracker.saveClick(this, mBtnCancel);
                finish();
                break;

            case R.id.btnCreate:
                onCreateGameBtnClicked();
                break;

            case R.id.btnStartDate:
                DatePickerFragment datePicker;
                datePicker = new DatePickerFragment();
                datePicker.setHandler(handlerTime);
                datePicker.show(getSupportFragmentManager(), "datePicker");
                break;

            case R.id.btnStartTime:
                TimePickerFragment timePicker;
                timePicker = new TimePickerFragment();
                timePicker.setHandler(handlerTime);
                timePicker.show(getSupportFragmentManager(), "timePicker");
                break;

            case R.id.btnMap:
                Intent intent = new Intent(this, GameAreaMapActivity.class);
                intent.putExtra("latitude", mLatitude);
                intent.putExtra("longitude", mLongitude);
                intent.putExtra("radius", mRadius);
                intent.putExtra("latitudeRed", mLatitudeRed);
                intent.putExtra("longitudeRed", mLongitudeRed);
                intent.putExtra("latitudeBlue", mLatitudeBlue);
                intent.putExtra("longitudeBlue", mLongitudeBlue);
                startActivityForResult(intent, mCode);
                break;
        }
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);
        Log.d("CTF", "CTF onActivityResult");
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            mLatitude = bundle.getDouble("latitude");
            mLongitude = bundle.getDouble("longitude");
            mRadius = bundle.getDouble("radius");
            mLatitudeRed = bundle.getDouble("latitudeRed");
            mLongitudeRed = bundle.getDouble("longitudeRed");
            mLatitudeBlue = bundle.getDouble("latitudeBlue");
            mLongitudeBlue = bundle.getDouble("longitudeBlue");
            Log.d("CTF", "Last settings");
            Log.d("CTF", "latitude: " + mLatitude + " longitude: " + mLongitude + " radius: " + mRadius);
        }
    }

    private void onCreateGameBtnClicked() {
        IssueTracker.saveClick(this, mBtnCreate);

        String mLocationName = mEditLocationName.getText().toString();
        String mGameName = mEditGameName.getText().toString();
        String mStartDate = mBtnStartDate.getText().toString();
        String mStartTime = mBtnStartTime.getText().toString();
        String mPlayingTimeTmp = mEditPlayingTime.getText().toString();
        String mMaxPlayersTmp = mEditMaxPlayers.getText().toString();
        String mMaxPointsTmp = mEditMaxPoints.getText().toString();
        String mRedBaseName = mEditRedNameBase.getText().toString();
        String mBlueBaseName = mEditBlueNameBase.getText().toString();

        boolean correct = correctDateTime(mStartDate, mStartTime);
        boolean correct2 = correctData(mGameName, mLocationName, mPlayingTimeTmp);

        if (correct && correct2) {
            if (NetworkService.isDeviceOnline(this)) {
                if (mMaxPlayersTmp.length() == 0)
                    mMaxPlayersTmp = "120"; // no limits?
                if (mMaxPointsTmp.length() == 0)
                    mMaxPointsTmp = "120"; // no limits?

                long mPlayingTime = Integer.parseInt(mPlayingTimeTmp) * 60 * 1000;
                int mMaxPlayers = Integer.parseInt(mMaxPlayersTmp);
                int mMaxPoints = Integer.parseInt(mMaxPointsTmp);
                Localization localization = new Localization();
                GameExtendedInfo gameInfo = new GameExtendedInfo();
                gameInfo.setName(mEditGameName.getText().toString());
                gameInfo.setDescription(mEditGameDescription.getText().toString());
                localization.setName(mEditLocationName.getText().toString());
                localization.setRadius(mRadius);
                localization.setLatLng(new LatLng(mLatitude, mLongitude));
                gameInfo.setLocalization(localization);
                Date timeStart = null;
                try {
                    timeStart = new SimpleDateFormat(Constants.DATE_FORMAT + " " + Constants.TIME_FORMAT).parse(mStartDate + " " + mStartTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                gameInfo.setTimeStart(timeStart);
                gameInfo.setDuration(mPlayingTime);
                gameInfo.setPlayersMax(mMaxPlayers);
                gameInfo.setPointsMax(mMaxPoints);
                gameInfo.setBlueTeamName(mBlueBaseName);
                gameInfo.setRedTeamName(mRedBaseName);
                gameInfo.setRedBase(new LatLng(mLatitudeRed, mLongitudeRed));
                gameInfo.setBlueBase(new LatLng(mLatitudeBlue, mLongitudeBlue));

                if (mId == null) {
                    CreateGame createGame = new CreateGame(this, gameInfo);
                    createGame.execute();
                } else {
                    if (NetworkService.isDeviceOnline(this)) {
                        EditGame editGame = new EditGame(this, mId, GameStatusType.NEW.toString(), gameInfo);
                        editGame.execute();
                    } else {
                        Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT);
                    }
                }
            } else {
                Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean correctDateTime(String mStartDate, String mStartTime) {
        mInfo = Constants.EMPTY_STRING;
        final Calendar c = Calendar.getInstance();
        int actualYear = c.get(Calendar.YEAR);
        int actualMonth = c.get(Calendar.MONTH);
        int actualDay = c.get(Calendar.DAY_OF_MONTH) + 1;
        int actualHour = c.get(Calendar.HOUR_OF_DAY);
        int actualMinute = c.get(Calendar.MINUTE);

        int year = Integer.parseInt(mStartDate.substring(0, mStartDate.indexOf("-")));
        int month = Integer.parseInt(mStartDate.substring(mStartDate.indexOf("-") + 1, mStartDate.lastIndexOf("-")));
        int day = Integer.parseInt(mStartDate.substring(mStartDate.lastIndexOf("-") + 1));
        int hour = Integer.parseInt(mStartTime.substring(0, mStartTime.indexOf(":")));
        int minute = Integer.parseInt(mStartTime.substring(mStartTime.indexOf(":") + 1, mStartTime.lastIndexOf(":")));
        Log.d("CTF ", "CTF date correct " + year + ":" + month + ":" + day + "   " + hour + ":" + minute);

        if (year > actualYear)
            return true;
        if (year < actualYear) {
            mInfo += getResources().getString(R.string.start_date_past);
            return false;
        }
        if (month > actualMonth)
            return true;
        if (month < actualMonth) {
            mInfo += getResources().getString(R.string.start_date_past);
            return false;
        }
        if (day > actualDay)
            return true;
        if (day < actualDay) {
            mInfo += getResources().getString(R.string.start_date_past);
            return false;
        }
        if (hour > actualHour)
            return true;
        if (hour < actualHour) {
            mInfo += getResources().getString(R.string.start_date_past);
            return false;
        }
        if (minute > actualMinute)
            return true;

        mInfo += getResources().getString(R.string.start_date_past);
        return false;
    }

    private boolean correctData(String gameName, String locationName, String playingTime) {
        boolean result = false;

        if (gameName.isEmpty()) {
            if (!mInfo.isEmpty())
                mInfo += Constants.NEW_LINE;
            mInfo += getResources().getString(R.string.game_name_too_short);
        }

        if (locationName.isEmpty()) {
            if (!mInfo.isEmpty())
                mInfo += Constants.NEW_LINE;
            mInfo += getResources().getString(R.string.location_name_too_short);
        }

        if (playingTime.isEmpty()) {
            if (!mInfo.isEmpty())
                mInfo += Constants.NEW_LINE;
            mInfo += getResources().getString(R.string.playing_time_too_short);
        }

        float[] results = new float[1];
        Location.distanceBetween(mLatitude, mLongitude, mLatitudeRed, mLongitudeRed, results);
        if(results[0] > mRadius){
            if (!mInfo.isEmpty())
                mInfo += Constants.NEW_LINE;
            mInfo += getResources().getString(R.string.red_base_too_far);
        }

        float[] results2 = new float[1];
        Location.distanceBetween(mLatitude, mLongitude, mLatitudeBlue, mLongitudeBlue, results2);
        if(results2[0] > mRadius){
            if (!mInfo.isEmpty())
                mInfo += Constants.NEW_LINE;
            mInfo += getResources().getString(R.string.blue_base_too_far);
        }

        if (mInfo.isEmpty()) {
            result = true;
        } else {
            Toast.makeText(this, mInfo, Toast.LENGTH_SHORT).show();
        }

        return result;
    }
}