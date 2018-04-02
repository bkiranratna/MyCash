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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;




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


    //EditText et
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
                etTransAmount.setText("");
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
      //  myFileReadWrite();
      //  ReadBtn(view);


       // myFileJsonWrite();
      //  ReadMyJson(view);
       Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
       startActivity(intent);

    }

    private void updateDate() {
        editDate.setText(sdf.format(myCalendar.getTime()));
    }
///////////////////////////////////////////////////////////////////////////////////

    //TODO dashboard representation of Transaction details
    //TODO replace the total balance with actual values
    //TODO  Read & Write the Json File
    // TODO monthly wise details


private  void myFileReadWrite()
{
    //File file = new File(context.getFilesDir(), "mytext.txt");

    String filename = "mytext1.txt";
    Date date=Calendar.getInstance().getTime();
    String fileContents = "Hello world!"+ date.toString() +"\n" ;
    FileOutputStream outputStream;
    Log.i("Write the File ",fileContents);
    try {
        File file = new File(context.getFilesDir(), filename);
        outputStream = openFileOutput(filename,MODE_APPEND ); //Context.MODE_PRIVATE);
        outputStream.write(fileContents.getBytes());

      outputStream.close();
        Log.i("Write into File -done ",fileContents);
        //display file saved message
        Toast.makeText(getBaseContext(), "File saved successfully!",
                Toast.LENGTH_SHORT).show();
        Log.i("Write into File -done ",getFilesDir().toString());

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    public void ReadBtn(View v) {
        //reading text from file
        try {
            FileInputStream fileIn=openFileInput("mytext1.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            BufferedReader in = new BufferedReader(InputRead);
            String line;
            int lineCnt=0;
            while ((line = in.readLine()) != null) {  // null =-1
              //  System.out.println(line);
                Log.i("Read the File ",line.toString() + lineCnt ++);
            }

            in.close();


          // Log.i("Read the File ",s.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private  void myFileJsonWrite()
    {
        //File file = new File(context.getFilesDir(), "mytext.txt");

        String filename = "myjson1.json";
        Date currentDate = Calendar.getInstance().getTime();
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this);
        String date = dateFormat.format(currentDate);

        String fileContents = "Hello world!"+ date.toString() +"\n" ;
        FileOutputStream outputStream;
        Log.i("Write the File ",fileContents);
        try {
            File file = new File(context.getFilesDir(), filename);
            outputStream = openFileOutput(filename,MODE_PRIVATE ); //Context.MODE_PRIVATE);

            Accountdetails ads = new Accountdetails();
            ads.setCurrentmonthincomeamount("90000");

            List<Expense> expenseList = new ArrayList<Expense>();
            Expense exp = new Expense();
            exp.setAmount("200");
            exp.setId("2");
            exp.setDate(date.toString());
            exp.setTransactiontype("expense");
            expenseList.add(exp);

            exp.setAmount("500");
            exp.setId("2");
            exp.setDate(date.toString());
            exp.setTransactiontype("expense");
            expenseList.add(exp);

            ads.setExpense(expenseList);

             List<Income> incomeList = new ArrayList<Income>();

            Income imp = new Income();
            imp.setAmount("200");
            imp.setId("2");
            imp.setDate(date.toString());
            imp.setTransactiontype("income");
            incomeList.add(imp);

            imp.setAmount("500");
            imp.setId("2");
            imp.setDate(date.toString());
            imp.setTransactiontype("income");
            incomeList.add(imp);

            ads.setIncome(incomeList);

            //Accountdetails ads1 = gson.fromJson(jsonString, Accountdetails.class);

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            String fileContents1= gson.toJson(ads);



            outputStream.write(fileContents1.getBytes());

            outputStream.close();
            Log.i("Write into File -done ",fileContents1);
            //display file saved message
            Toast.makeText(getBaseContext(), "File saved successfully!",
                    Toast.LENGTH_SHORT).show();
            Log.i("Write into File -done ",getFilesDir().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void ReadMyJson(View v) {
        //reading text from file
        try {
            FileInputStream fileIn=openFileInput("myjson1.json");
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            BufferedReader in = new BufferedReader(InputRead);
            String line;
            int lineCnt=0;
            while ((line = in.readLine()) != null) {  // null =-1
                //  System.out.println(line);
                Log.i("Read the File ",line.toString() + lineCnt ++);
            }

            in.close();


            // Log.i("Read the File ",s.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}///// class
