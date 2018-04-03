package com.example.kiran.mycash;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

public class TransactionActivity extends AppCompatActivity {


    // ArrayList for sourcetype,TransType,Amount,Date
    ArrayList<String> _arrAmtSourceType = new ArrayList<>();
    ArrayList<String> _arrTransType = new ArrayList<>();
    ArrayList<String> _arrAmount = new ArrayList<>();
    ArrayList<String> _arrTransDate = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        // get the reference of RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        Log.d("in transcationActivy", "onCreate: layoutmanage done");

        try {
            // get JSONObject from JSON file
            //  JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONObject obj = new JSONObject(ReadMyJsonFile());
            // fetch JSONArray named users
            JSONObject accountdetailsRootElement = obj.getJSONObject("accountdetails");
            JSONArray objExpenseArray = accountdetailsRootElement.getJSONArray("expense");
            // implement for loop for getting users list data
            for (int i = 0; i < objExpenseArray.length(); i++) {
                // create a JSONObject for fetching single user data
                JSONObject expensedetailsItems = objExpenseArray.getJSONObject(i);
                // create a object for getting contact data from JSONObject
                // fetch mobile number and store it in arraylist
                _arrAmtSourceType.add(expensedetailsItems.getString("amountsourcetype"));
                // fetch mobile number and store it in arraylist
                _arrTransType.add(expensedetailsItems.getString("transactiontype"));
                // fetch mobile number and store it in arraylist
                _arrAmount.add(expensedetailsItems.getString("amount"));
                // fetch mobile number and store it in arraylist
                _arrTransDate.add(expensedetailsItems.getString("date"));
            }
            // total sum of expense
            int sumExpense = 0;
            for(int i = 1;  i < _arrAmount.size(); i++)
                sumExpense += Integer.parseInt( _arrAmount.get(i));

            JSONArray objImcomeArray = accountdetailsRootElement.getJSONArray("income");
            // implement for loop for getting users list data
            for (int i = 0; i < objImcomeArray.length(); i++) {
                // create a JSONObject for fetching single user data
                JSONObject incomedetailsItems = objImcomeArray.getJSONObject(i);
                // create a object for getting contact data from JSONObject
                // fetch mobile number and store it in arraylist
                _arrAmtSourceType.add(incomedetailsItems.getString("amountsourcetype"));
                // fetch mobile number and store it in arraylist
                _arrTransType.add(incomedetailsItems.getString("transactiontype"));
                // fetch mobile number and store it in arraylist
                _arrAmount.add(incomedetailsItems.getString("amount"));
                // fetch mobile number and store it in arraylist
                _arrTransDate.add(incomedetailsItems.getString("date"));
            }

            // total sum of income
            int sumImcome = 0;
            for(int i = 1;  i < _arrAmount.size(); i++)
                sumImcome += Integer.parseInt( _arrAmount.get(i));

            // get the total balance
            int totalBalance =  sumImcome- sumExpense;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapter customAdapter;
        customAdapter = new CustomAdapter(TransactionActivity.this, _arrAmtSourceType, _arrTransType, _arrAmount,_arrTransDate);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("AccountDetails.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }



    public String ReadMyJsonFile() {
        myFileJsonWrite1();


        String json = null;
        //reading text from file
        try {

            String accDetailsJsonDataFileName ="accountSummaryInfo.json";
            FileInputStream fileIn=openFileInput(accDetailsJsonDataFileName);
            InputStream is= fileIn;
            int size = is.available();
            //  Log.i(TAG, "ReadMyJsonFile: "      size);
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;


    }

    private  void myFileJsonWrite1()
    {
        //File file = new File(context.getFilesDir(), "mytext.txt");

        String filename = "myjson2.json";
        Date currentDate = Calendar.getInstance().getTime();
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this);
        String date = dateFormat.format(currentDate);

        String fileContents = "Hello world!"+ date.toString() +"\n" ;
        FileOutputStream outputStream;
        Log.i("Write the File ",fileContents);
        try {
            File file = new File(this.getFilesDir(), filename);
            outputStream = openFileOutput(filename,MODE_PRIVATE ); //Context.MODE_PRIVATE);

            Accountdetails ads = new Accountdetails();
            ads.setCurrentmonthincomeamount("90000");

            List<Expense> expenseList = new ArrayList<Expense>();
            Expense exp = new Expense();
            exp.setAmountsourcetype("Cash1");
            exp.setAmount("200");
            exp.setId("1");
            exp.setDate(date.toString());
            exp.setTransactiontype("expense");
            ads.setExpense(expenseList);

            List<Income> incomeList = new ArrayList<Income>();

            Income imp = new Income();
            imp.setAmount("200");
            imp.setId("1");
            imp.setAmountsourcetype("Cash1");
            imp.setDate(date.toString());
            imp.setTransactiontype("income");
            incomeList.add(imp);
            ads.setIncome(incomeList);

            MyCashData _myCashData =  new MyCashData();
            _myCashData.setAccountdetails(ads);

            //Accountdetails ads1 = gson.fromJson(jsonString, Accountdetails.class);

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            String fileContents1= gson.toJson(_myCashData);



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

}
