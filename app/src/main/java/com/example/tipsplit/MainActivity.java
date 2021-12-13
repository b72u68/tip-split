package com.example.tipsplit;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.lang.Math;

public class MainActivity extends AppCompatActivity {

    private RadioGroup tipPercentView;
    private EditText billView;
    private TextView tipAmountView;
    private TextView totalWithTipView;
    private EditText totalPeopleView;
    private TextView totalPerPersonView;
    private TextView overageView;

    private String bill = "";
    private String tipAmount = "";
    private String totalWithTip = "";
    private String totalPeople = "";
    private String totalPerPerson = "";
    private String overage = "";
    private String tipPercentId = "";

    public String moneyFormat = "$%.2f";
    public String doubleFormat = "%.2f";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tipPercentView = findViewById(R.id.tipPercent);
        billView = findViewById(R.id.billText);
        tipAmountView = findViewById(R.id.tipAmount);
        totalWithTipView = findViewById(R.id.totalWithTip);
        totalPeopleView = findViewById(R.id.totalPeople);
        totalPerPersonView = findViewById(R.id.totalPerPerson);
        overageView = findViewById(R.id.overage);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("BILL", bill);
        outState.putString("TIP_PERCENT_ID", tipPercentId);
        outState.putString("TIP_AMOUNT", tipAmount);
        outState.putString("TOTAL_WITH_TIP", totalWithTip);
        outState.putString("TOTAL_PEOPLE", totalPeople);
        outState.putString("TOTAL_PER_PERSON", totalPerPerson);
        outState.putString("OVERAGE", overage);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        bill = savedInstanceState.getString("BILL");
        tipPercentId = savedInstanceState.getString("TIP_PERCENT_ID");
        tipAmount = savedInstanceState.getString("TIP_AMOUNT");
        totalWithTip = savedInstanceState.getString("TOTAL_WITH_TIP");
        totalPeople = savedInstanceState.getString("TOTAL_PEOPLE");
        totalPerPerson = savedInstanceState.getString("TOTAL_PER_PERSON");
        overage = savedInstanceState.getString("OVERAGE");

        if (!tipPercentId.isEmpty())
            tipPercentView.check(Integer.parseInt(tipPercentId));

        billView.setText(formatMoney(bill));
        tipAmountView.setText(formatMoney(tipAmount));
        totalWithTipView.setText(formatMoney(totalWithTip));
        totalPeopleView.setText(totalPeople);
        totalPerPersonView.setText(formatMoney(totalPerPerson));
        overageView.setText(formatMoney(overage));
    }

    public String roundDouble(double d) {
        return String.format(doubleFormat, d);
    }

    public String roundNearestCent(double d) {
        return roundDouble(Math.ceil(d * 100) / 100);
    }

    public String formatMoney(String s) {
        if (s.isEmpty()) {
            return "";
        }
        return String.format(moneyFormat, Double.parseDouble(s));
    }

    public void clearFields() {
        tipPercentView.clearCheck();
        billView.setText("");
        tipAmountView.setText("");
        totalWithTipView.setText("");
        totalPeopleView.setText("");
        totalPerPersonView.setText("");
        overageView.setText("");

        bill = "";
        tipPercentId = "";
        tipAmount = "";
        totalWithTip = "";
        totalPeople = "";
        totalPerPerson = "";
        overage = "";
    }

    public void onChooseTipPercent(View view) {
        bill = billView.getText().toString();

        if (bill.isEmpty()) {
            clearFields();
            return;
        }

        double billDouble = Double.parseDouble(bill);

        if (view.getId() == R.id.firstOption) {
            tipAmount = String.valueOf(billDouble * 15 / 100);
        } else if (view.getId() == R.id.secondOption) {
            tipAmount = String.valueOf(billDouble * 15 / 100);
        } else if (view.getId() == R.id.thirdOption) {
            tipAmount = String.valueOf(billDouble * 18 / 100);
        } else if (view.getId() == R.id.fourthOption) {
            tipAmount = String.valueOf(billDouble * 20 / 100);
        }

        tipPercentId = String.valueOf(view.getId());

        tipAmount = roundDouble(Double.parseDouble(tipAmount));
        totalWithTip = roundDouble(Double.parseDouble(tipAmount) + Double.parseDouble(bill));

        tipAmountView.setText(formatMoney(tipAmount));
        totalWithTipView.setText(formatMoney(totalWithTip));
    }

    public void onClickGoButton(View view) {
        if (bill.isEmpty() || tipAmount.isEmpty() || totalWithTip.isEmpty()) {
            clearFields();
            return;
        }

        if (totalPeopleView.getText().toString().isEmpty() || Double.parseDouble(totalPeopleView.getText().toString()) <= 0) {
            totalPeopleView.setText("");
            totalPerPersonView.setText("");
            overageView.setText("");

            totalPeople = "";
            totalPerPerson = "";
            overage = "";

            return;
        }

        totalPeople = totalPeopleView.getText().toString();
        totalPerPerson = roundNearestCent(Double.parseDouble(totalWithTip) / Double.parseDouble(totalPeople));
        overage = roundDouble(Double.parseDouble(totalPerPerson) * Double.parseDouble(totalPeople) - Double.parseDouble(totalWithTip));

        totalPerPersonView.setText(formatMoney(totalPerPerson));
        overageView.setText(formatMoney(overage));
    }

    public void onClickClearButton(View view) {
        clearFields();
    }
}