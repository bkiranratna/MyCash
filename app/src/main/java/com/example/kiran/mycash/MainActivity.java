package com.example.kiran.mycash;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.DatePicker;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import java.nio.file.*;


public class MainActivity extends AppCompatActivity {

    TextView tVTotBalnce;
    TextView etTotBalance;
    CheckBox chkIncomeValue;
    CheckBox chkExpenseValue;
    EditText etTransAmount;
    ImageView ivIconAddDate;
    Button btnTransSubmit;
    EditText editDate;

    Context context = this;
    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "dd.MM.yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.GERMAN);

    // json file name
    String accDetailsJsonDataFileName ="accountSummaryInfo.json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tVTotBalnce = (TextView) findViewById(R.id.tvTotalBalance);
        etTotBalance = (TextView) findViewById(R.id.etTotalBalance);
        chkIncomeValue = (CheckBox) findViewById(R.id.chkIncome);
        chkExpenseValue = (CheckBox) findViewById(R.id.ChkExpense);
        etTransAmount = (EditText) findViewById(R.id.etTransationAmount);
        ivIconAddDate = (ImageView) findViewById(R.id.ivDatePopup);
        btnTransSubmit = (Button) findViewById(R.id.btnAddTransaction);
        editDate = (EditText) findViewById(R.id.etDate);

        etTotBalance.setText(R.string.totBalanceAmt);
        chkExpenseValue.setChecked(true);
        chkIncomeValue.setChecked(false);
        etTransAmount.setText(R.string.enterTransactionamount);

        // etTransAmount.setInputType(InputType.TYPE_CLASS_NUMBER);
        etTransAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  etTransAmount.setText("");
            }
        });

// On  Income checkbox click
        chkIncomeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkIncomeValue.isChecked()) {
                    chkExpenseValue.setChecked(false);
                } else {
                    chkExpenseValue.setChecked(true);
                    chkIncomeValue.setChecked(false);
                }
            }
        });
// On  expense checkbox click
        chkExpenseValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkExpenseValue.isChecked()) {
                    chkIncomeValue.setChecked(false);
                } else {
                    chkExpenseValue.setChecked(false);
                    chkIncomeValue.setChecked(true);
                }
            }
        });



        // init - set date to current date
        editDate.setText(sdf.format(new Date()));
        long currentdate = System.currentTimeMillis();
        String dateString = sdf.format(currentdate);
        editDate.setText(dateString);
        // set calendar date and update editDate
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

        };
        // onclick - popup datepicker
        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }  ///////////////


    // On click on the Save Transaction button
    public void OnClickbtnAddTransaction(View view) {
        Context context = getApplicationContext();
        String transactionamt = etTransAmount.getText().toString();
        CharSequence text = "Expense Amount added RS: " + transactionamt;
        //Toast.makeText(context, text, Toast.LENGTH_LONG).show();
        Log.i("MainActivity","Move to next");
        boolean fileExists =  new File(context.getFilesDir(), accDetailsJsonDataFileName).exists();
        if(fileExists) {
            boolean chkExpValue=  chkExpenseValue.isChecked();
            boolean chkIncmValue= chkIncomeValue.isChecked();
            String transType="";
            if(chkExpValue  ){ transType="expense"; }
            else  if ( chkIncmValue){ transType="income";}
            String transDate = editDate.getText().toString();
            String transAmount =  etTransAmount.getText().toString();
            AddTransactionDetails2Json(accDetailsJsonDataFileName,transType,transDate,transAmount);
               /* List<Expense> expenseList = new ArrayList<Expense>();
                Expense exp = new Expense();
                exp.setAmountsourcetype("Cash1");
                exp.setAmount(transAmount);
                exp.setId("1");
                exp.setDate("today");
                exp.setTransactiontype(transType); */
        }else{
            GenerateJsonSummaryData(  accDetailsJsonDataFileName);
        }

        Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
        startActivity(intent);

    }

    private void updateDate() {
        editDate.setText(sdf.format(myCalendar.getTime()));
    }
///////////////////////////////////////////////////////////////////////////////////

    //TODO dashboard representation of Transaction details
    //TODO replace the total balance with actual values
    // TODO monthly wise details

    private  String  GenerateJsonSummaryData( String accDetailsJsonDataFileName)
    {
        //File file = new File(context.getFilesDir(), "mytext.txt");

        String filename = accDetailsJsonDataFileName;

        FileOutputStream outputStream;
        Log.i("MainActivity OnCreate()", "GenerateJsonSummaryData: Start Json File Create" );
        try {
            File file = new File(this.getFilesDir(), filename);
            outputStream = openFileOutput(filename,MODE_APPEND ); //Context.MODE_PRIVATE);

            MyCashData _myCashData =  new MyCashData();


            List<Expense> expenseList = new ArrayList<Expense>();
            List<Income> incomeList = new ArrayList<Income>();

            Accountdetails ads = new Accountdetails();
            ads.setCurrentmonthincomeamount("0");
            ads.setCurrentmonthexpenseamount("0");
            ads.setTotalbalanceamount("0");
            ads.setTotalexpenseamount("0");
            ads.setTotalincomeamount("0");
            ads.setExpense(expenseList);
            ads.setIncome(incomeList);
            _myCashData.setAccountdetails(ads);

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            String fileContents1= gson.toJson(_myCashData);
            outputStream.write(fileContents1.getBytes());
            outputStream.close();

            //display file saved message
            Toast.makeText(getBaseContext(), "Json File Created  saved successfully!",
                    Toast.LENGTH_SHORT).show();
            Log.i("MainActivity OnCreate()", "GenerateJsonSummaryData: End Json File Create ");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    private String AddTransactionDetails2Json(String accDetailsJsonDataFileName,String transType,String transDate,String transAmount){
        String filename = accDetailsJsonDataFileName;

        String _transType=transType.trim();
        String _date = transDate;
        String _transAmount=transAmount;

        Log.i("MainActivity OnCreate()", "AddTransactionDetails2Json: Start Write into Json File  data " );

        FileOutputStream outputStream;

        try {
            String _JsonString ;
            FileInputStream fileIn=openFileInput(accDetailsJsonDataFileName);
            InputStream is= fileIn;
            int size = is.available();
            //  Log.i(TAG, "ReadMyJsonFile: "      size);
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            _JsonString = new String(buffer, "UTF-8");

            JSONObject PreviousJsonObj = new JSONObject(_JsonString);
            JSONObject PreviousADsJsonObj =  PreviousJsonObj.getJSONObject("accountdetails");// new JSONObject(_JsonString);
            JSONArray expenseArray = PreviousADsJsonObj.getJSONArray("expense");
            JSONObject jsonArrObj= new JSONObject();
            Expense exp = new Expense();
            jsonArrObj.put("amountsourcetype","Cash" );
            jsonArrObj.put("amount",transAmount );
            jsonArrObj.put("date",transDate );
            jsonArrObj.put("transactiontype",transType );

            expenseArray.put(jsonArrObj);

            //JSONObject currentADsJsonObject = new JSONObject();
            // currentADsJsonObject.put("expense",array);
            File file = new File(this.getFilesDir(), filename);
            outputStream = openFileOutput(filename,MODE_PRIVATE );
            String fileContents1= PreviousJsonObj.toString() ;//gsonObj.toJson(obj);
            outputStream.write(fileContents1.getBytes());
            outputStream.close();
            Log.i("Write into File -done ",fileContents1);
            //display file saved message
            Toast.makeText(getBaseContext(), "File saved successfully!",
                    Toast.LENGTH_SHORT).show();
            Log.i("MainActivity OnCreate()", "AddTransactionDetails2Json: End-  Write into Json File  data");

        }catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

}