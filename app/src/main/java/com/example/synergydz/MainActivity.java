package com.example.synergydz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    Button checkButton;
    TextInputEditText numberInput;

    TextView status;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkButton = findViewById(R.id.check_button);
        numberInput = findViewById(R.id.number_input);
        status = findViewById(R.id.status);

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (numberInput.length() != 6) {
                        throw new NumberFormatException();
                    } else {
                        String numberString = numberInput.getText().toString();
                        status.setVisibility(View.VISIBLE);

                        if (validateNumber(numberString)) {
                            status.setText("Ваш билет счастливый!");
                        } else {
                            int currentNumber = Integer.parseInt(numberString);
                            int nearestNumber = getNearestLuckyNumber(currentNumber);

                            if (nearestNumber == currentNumber) {
                                status.setText("Ваш билет не cчастливый! Счастливых билетов больше нет");
                            } else {
                                status.setText("Ваш билет не cчастливый! Ближайший счастливый билет: " + nearestNumber);
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    status.setVisibility(View.GONE);
                    numberInput.setError("Неверный формат числа");
                }
            }
        });
    }

    private Boolean validateNumber(String numberString) {
        int evenSum = 0;
        int oddSum = 0;

        for (int i = 0; i <= 5; i++) {
            if (i % 2 == 0) {
                evenSum += Character.getNumericValue(numberString.charAt(i));
            } else {
                oddSum += Character.getNumericValue(numberString.charAt(i));
            }
        }

        return evenSum == oddSum;
    }

    private int getNearestLuckyNumber(int currentNumber) {
        for (int i = currentNumber + 1; i <= 999_999; i++) {
            if (validateNumber(String.valueOf(i))) {
                return i;
            }
        }
        return currentNumber;
    }
}