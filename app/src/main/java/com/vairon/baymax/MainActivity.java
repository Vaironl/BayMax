package com.vairon.baymax;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import io.fabric.sdk.android.Fabric;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "1HoUIWpvfG3QfKyw9xoJZ8Qxg";
    private static final String TWITTER_SECRET = "ZJPRfCgUICFp5b8lydfiDwAouCLJr2wIXLXxl2uBjsMGbzXz6J";


    private String TAG = this.getClass().getName();

    private TextToSpeech textToSpeech;
    private Button beginButton, calculateButton, finishButton;
    private RadioGroup radioGroup;
    private TextView titleText, symptomsTV, resultBPM, illnessList, didYouKnow;
    private EditText beatsCounted;
    private TableRow row1, row2, row3;

    //Displays gif file
    private WebView webView;

    //Contains a timerview
    private TimerView timerView;

    //Moves available
    int LEFT_ARM = 0, RIGHT_ARM = 1, BOTH_ARMS = 2;

    //message to display what the text to speech is saying
    String message;

    private OkHttpClient client;

    private boolean fever, headache, runnyNose, soreThroath, chills, soreMuscles;

    private ListView listView;

    private Bill[] bills;

    private ImageButton twitterLogo;

    private View horizontalRuler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        webView = (WebView) findViewById(R.id.gifView);
        webView.loadUrl("file:///android_asset/gifplay.html");

        client = new OkHttpClient();

//        moveArms(BOTH_ARMS);

        fever = headache = runnyNose = soreThroath = chills = soreMuscles = false;

        init();

    }

    //Used to give interesting information to the user
    private void billsInfo() {
        //Hide all other information
        hideHowAreYouFeeling();
        hideSymptoms();
        hideBPM();
        hideWebView();
        setViewInvisible(titleText);
        setViewInvisible(finishButton);


        String PARAM = "doctors";
        String url = "https://hackathon.fiscalnote.com/bills?q=" + PARAM + "&legislature=VA&apikey=S1MSQDZVUBE1J1FI854UVF6PLCFSSZR6";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected: " + response);
                }

                String result;
                result = response.body().string();

                final String finalResult = result;
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        JSONArray allBills;

                        try {
                            allBills = new JSONArray(finalResult);

                            if (allBills.length() <= 20) {

                                for (int index = 0; index < allBills.length(); index++) {

                                    String title = null, description = null;
                                    boolean passed = false;

                                    title = allBills.getJSONObject(index).getString("title");
                                    description = allBills.getJSONObject(index).getString("description");
//                                    passed = allBills.getJSONObject(index).getBoolean("pass_result");


                                    bills[index] = new Bill(title, description, passed);
                                }

                            } else {


                                for (int index = 0; index < 20; index++) {

                                    String title = allBills.getJSONObject(index).getString("title");
                                    String description = allBills.getJSONObject(index).getString("description");
                                    boolean passed = allBills.getJSONObject(index).getBoolean("pass_result");

                                    bills[index] = new Bill(title, description, passed);
                                }

                            }
                            //Make the list visible by the end of the program
                            setViewVisible(twitterLogo);
                            setViewVisible(didYouKnow);
                            setViewVisible(horizontalRuler);
                            setViewVisible(listView);
                            listView.setAdapter(new CustomBillAdapter(MainActivity.this, bills));


                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "We apologize! An error occurred reading data from the depths of the internet", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }


                    }
                });

            }
        });


    }

    private void moveArms(int arms) {

        String url = "http://baymax.michaelbailey.co/move.php?appendage=";

        if (arms == LEFT_ARM) {
            url += "l";
        } else if (arms == RIGHT_ARM) {
            url += "r";
        } else if (arms == BOTH_ARMS) {
            url += "lr";
        } else {
            //Invalid request. Do not run the request
            Toast.makeText(MainActivity.this, "Invalid request", Toast.LENGTH_SHORT).show();
            return;

        }

        Request request = new Request.Builder()
                .url(url)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected: " + response);
                }

                final String responseData = response.body().string();

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Log.v(TAG, "RESPONSE: " + responseData);

                    }
                });

            }
        });

    }

    private void tweet() {
        File myImageFile = new File("file:///android_asset/exhaust.jpg");
        Uri myImageUri = Uri.fromFile(myImageFile);

        TweetComposer.Builder builder = new TweetComposer.Builder(this).text("Thanks to Baymax, I feel a lot better now!").image(myImageUri);
        builder.show();

    }


    private void init() {

        //Initialize text to speech
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    //United State language. We can also use the UK for fun!!
                    textToSpeech.setLanguage(Locale.UK);
                } else {
                    Toast.makeText(MainActivity.this, "An error occurred initializing the text to speech", Toast.LENGTH_LONG).show();
                }

            }
        });

        //Init view components
        beginButton = (Button) findViewById(R.id.beginButton);
        calculateButton = (Button) findViewById(R.id.calculateBPMButton);
        finishButton = (Button) findViewById(R.id.finishButton);
        radioGroup = (RadioGroup) findViewById(R.id.feelingRadioGroup);
        titleText = (TextView) findViewById(R.id.titleText);
        illnessList = (TextView) findViewById(R.id.illnessList);
        didYouKnow = (TextView) findViewById(R.id.didYouKnowTV);
        symptomsTV = (TextView) findViewById(R.id.symptomsTV);
        timerView = new TimerView(findViewById(R.id.bpmView), this, 0);
        beatsCounted = (EditText) findViewById(R.id.beatsCounted);
        resultBPM = (TextView) findViewById(R.id.bpmResult);
        listView = (ListView) findViewById(R.id.listView);
        twitterLogo = (ImageButton) findViewById(R.id.twitter_logo);
        horizontalRuler = (View) findViewById(R.id.horizontalRuler);


        //Only 20 bills should be displayed at the end of a session
        bills = new Bill[20];


        //init Bills
        for (int index = 0; index < 20; index++) {
            bills[index] = null;
        }

        //Timer view startbutton
        Button startButton = (Button) timerView.getView().findViewById(R.id.startTimerButton);

        row1 = (TableRow) findViewById(R.id.row1);
        row2 = (TableRow) findViewById(R.id.row2);
        row3 = (TableRow) findViewById(R.id.row3);

        hideHowAreYouFeeling();
        hideSymptoms();
        hideBPM();
        hideWebView();
        setViewInvisible(finishButton);
        setViewInvisible(didYouKnow);


        timerView.initTimer();


        beginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showHowAreYouFeeling();
                setViewInvisible(beginButton);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //15 second timer
                timerView.setTime(15);
                timerView.startTimer();
                hideWebView();

            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setViewInvisible(timerView.getView());

                if (beatsCounted.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter the number of heart beats counted.", Toast.LENGTH_LONG).show();
                } else if (beatsCounted.getText().equals("0")) {
                    setMessage("You might be dead if your heart rate is 0.");
                } else {
                    int beats = Integer.valueOf(beatsCounted.getText().toString());
                    int BPM = beats * 4;
                    resultBPM.setText("Result: " + (BPM) + "BPM");
                    if (BPM >= 60 && BPM <= 100) {
                        setMessage("You have a good heart rate.");
                    } else {
                        setMessage("You might want to keep an eye on your heart rate.");
                    }
                }
            }
        });


        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billsInfo();
            }
        });

        twitterLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tweet();
            }
        });


    }


    private void setViewVisible(View view) {
        view.setVisibility(View.VISIBLE);

    }

    private void setViewInvisible(View view) {
        view.setVisibility(View.GONE);
    }

    private void showSymptomsResults() {
        setViewVisible(illnessList);
        setViewVisible(finishButton);
    }

    private void hideSymptomsResults() {
        setViewInvisible(illnessList);
    }


    private void showHowAreYouFeeling() {
        setViewVisible(radioGroup);
        setMessage("Hello! I'm BayMax. Today I will be helping you to get healthier." +
                " How are you feeling today?");

    }

    private void hideHowAreYouFeeling() {
        setViewInvisible(radioGroup);
    }

    private void hideWebView() {
        webView.stopLoading();
        setViewInvisible(webView);


    }

    private void showWebView() {
        webView.reload();
        setViewVisible(webView);
    }


    private void showSymptoms() {

        setViewVisible(row1);
        setViewVisible(row2);
        setViewVisible(row3);
        setViewVisible(symptomsTV);
        showSymptomsResults();
    }

    private void hideSymptoms() {

        setViewInvisible(row1);
        setViewInvisible(row2);
        setViewInvisible(row3);
        setViewInvisible(symptomsTV);
        hideSymptomsResults();

    }

    private void showBPM() {

        showWebView();
        setViewVisible(calculateButton);
        setViewVisible(resultBPM);
        setViewVisible(beatsCounted);
        setViewVisible(timerView.getView());
        setViewVisible(finishButton);
        setMessage("Let's check your heart rate. Please count your heart beats as soon as the timer starts.");

    }

    private void hideBPM() {

        setViewInvisible(calculateButton);
        setViewInvisible(resultBPM);
        setViewInvisible(beatsCounted);
        setViewInvisible(timerView.getView());

    }

    private void measureTemp() {

    }

    private void sayMessage(String message) {
        textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);

    }

    private void setMessage(final String _message) {

        titleText.setText(message);

        message = _message;
        titleText.setText(message);
        sayMessage(message);

    }


    private int numOfSymptoms = 0;

    public void onBoxChecked(View view) {

        //If you have fever, chills, and sore throat go to the doctor

        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.feverSymptom:
                if (checked) {
                    fever = true;
                    numOfSymptoms++;
                } else {
                    fever = false;
                    numOfSymptoms--;
                }

                break;
            case R.id.headacheSymptom:
                if (checked) {
                    headache = true;
                    numOfSymptoms++;
                } else {
                    headache = false;
                    numOfSymptoms--;
                }

                break;
            case R.id.runnyNoseSymptom:
                if (checked) {
                    runnyNose = true;
                    numOfSymptoms++;
                } else {
                    runnyNose = false;
                    numOfSymptoms--;
                }
                break;
            case R.id.soreThroatSymptom:
                if (checked) {
                    soreThroath = true;
                    numOfSymptoms++;
                } else {
                    soreThroath = false;
                    numOfSymptoms--;
                }

                break;
            case R.id.chillsSymptom:
                if (checked) {
                    chills = true;
                    numOfSymptoms++;
                } else {
                    chills = false;
                    numOfSymptoms--;
                }
                break;
            case R.id.soreMusclesSymptom:
                if (checked) {
                    soreMuscles = true;
                    numOfSymptoms++;
                } else {
                    soreMuscles = false;
                    numOfSymptoms--;
                }

                break;

            default:
                break;

        }


        if (numOfSymptoms == 0) {
            setMessage("");
        }
        //Illness combinations
        if (numOfSymptoms <= 1) {
            if (headache || chills || (fever && runnyNose && headache) || (fever && runnyNose && headache & soreThroath)) {
                setMessage("If the symptoms do not go away in a couple of days considering talking to your doctor.");
            } else {
                setMessage("Drink lots of fluid and the symptoms should go away in a couple of weeks.");
            }
        } else {
            if (numOfSymptoms >= 5) {
                setMessage("You should go to the doctor as soon as possible!");
            } else if ((fever && soreThroath) || (soreMuscles && headache) || (headache && soreThroath)) {
                setMessage("Stay at home and drink lots of water.");
            } else if ((fever && chills) || (fever && soreMuscles) || (numOfSymptoms >= 4) || (fever && chills && headache && soreThroath) || (fever && chills && soreThroath)) {
                setMessage("You should go to the doctor soon.");
            }
            //fever, headache, runnyNose, soreThroath, chills, soreMuscles;
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        hideHowAreYouFeeling();

        switch (view.getId()) {

            case R.id.reallyGoodRB:
                if (checked) {

                    setMessage("I am glad that you are feeling so well today!");

                    showBPM();
                }
                break;
            case R.id.goodRB:
                if (checked) {
                    setMessage("I am glad that you are feeling good today!");
                    showBPM();

                }
                break;
            case R.id.okRB:
                if (checked) {
                    setMessage("Hope you feel better soon!");
                    showBPM();
                }
                break;
            case R.id.notOkRB:
                if (checked) {
                    setMessage("Sorry to hear you are not feeling well, let's get you checked!");
                    showSymptoms();
                }
                break;
            case R.id.badRB:
                if (checked) {
                    setMessage("Let's get you checked right now!");
                    showSymptoms();
                }
                break;
            case R.id.reallyBadRB:
                if (checked) {
                    setMessage("If this is an emergency call 9 1 1. Otherwise, Let's get you checked right now!");
                    showSymptoms();
                }
                break;

        }

    }

    @Override
    protected void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
