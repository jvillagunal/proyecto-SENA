package com.example.grigoriperelmanapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.animation.ObjectAnimator;

public class MainActivity extends AppCompatActivity {

    private TextView tvDisplay;
    private TextView tvExpression;

    private String currentNumber = "";
    private String operator = "";
    private double firstOperand = 0;
    private boolean isNewOperation = false;
    private boolean hasResult = false;
    private String fullExpression = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay    = findViewById(R.id.tv_display);
        tvExpression = findViewById(R.id.tv_expression);

        // ── Dígitos ──────────────────────────────────────────────────────────
        int[] digitIds = {
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3,
            R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7,
            R.id.btn_8, R.id.btn_9
        };
        for (int id : digitIds) {
            findViewById(id).setOnClickListener(v -> {
                animateButton(v);
                onDigitPressed(((Button) v).getText().toString());
            });
        }

        // ── Punto decimal ────────────────────────────────────────────────────
        findViewById(R.id.btn_dot).setOnClickListener(v -> {
            animateButton(v);
            onDotPressed();
        });

        // ── Operadores ────────────────────────────────────────────────────────
        findViewById(R.id.btn_plus).setOnClickListener(v  -> { animateButton(v); onOperatorPressed("+"); });
        findViewById(R.id.btn_minus).setOnClickListener(v -> { animateButton(v); onOperatorPressed("-"); });
        findViewById(R.id.btn_mult).setOnClickListener(v  -> { animateButton(v); onOperatorPressed("×"); });
        findViewById(R.id.btn_div).setOnClickListener(v   -> { animateButton(v); onOperatorPressed("÷"); });

        // ── Acciones especiales ───────────────────────────────────────────────
        findViewById(R.id.btn_equals).setOnClickListener(v  -> { animateButton(v); onEqualsPressed(); });
        findViewById(R.id.btn_clear).setOnClickListener(v   -> { animateButton(v); onClearPressed(); });
        findViewById(R.id.btn_delete).setOnClickListener(v  -> { animateButton(v); onDeletePressed(); });
        findViewById(R.id.btn_percent).setOnClickListener(v -> { animateButton(v); onPercentPressed(); });
        findViewById(R.id.btn_sign).setOnClickListener(v    -> { animateButton(v); onSignPressed(); });
    }

    // ── Lógica de dígitos ─────────────────────────────────────────────────────
    private void onDigitPressed(String digit) {
        if (isNewOperation || hasResult) {
            currentNumber = digit;
            isNewOperation = false;
            hasResult = false;
        } else {
            if (currentNumber.equals("0") && !digit.equals("0")) {
                currentNumber = digit;
            } else if (currentNumber.equals("0") && digit.equals("0")) {
                // no añadir más ceros
            } else {
                currentNumber += digit;
            }
        }
        updateDisplay(currentNumber);
    }

    private void onDotPressed() {
        if (isNewOperation || hasResult) {
            currentNumber = "0.";
            isNewOperation = false;
            hasResult = false;
        } else if (!currentNumber.contains(".")) {
            if (currentNumber.isEmpty()) currentNumber = "0";
            currentNumber += ".";
        }
        updateDisplay(currentNumber);
    }

    // ── Operadores ────────────────────────────────────────────────────────────
    private void onOperatorPressed(String op) {
        if (!currentNumber.isEmpty()) {
            if (!operator.isEmpty() && !isNewOperation) {
                // Calcular operación encadenada
                calculate();
                fullExpression = formatNumber(firstOperand) + " " + op + " ";
            } else {
                firstOperand = parseNumber(currentNumber);
                fullExpression = formatNumber(firstOperand) + " " + op + " ";
            }
        } else if (hasResult) {
            fullExpression = formatNumber(firstOperand) + " " + op + " ";
        }
        operator = op;
        isNewOperation = true;
        hasResult = false;
        tvExpression.setText(fullExpression);
    }

    // ── Igual ─────────────────────────────────────────────────────────────────
    private void onEqualsPressed() {
        if (operator.isEmpty() || currentNumber.isEmpty()) return;

        double secondOperand = parseNumber(currentNumber);
        fullExpression = formatNumber(firstOperand) + " " + operator + " " + formatNumber(secondOperand) + " =";
        tvExpression.setText(fullExpression);

        calculate();
        hasResult = true;
        operator = "";
        currentNumber = "";
    }

    private void calculate() {
        double second = currentNumber.isEmpty() ? firstOperand : parseNumber(currentNumber);
        double result;
        switch (operator) {
            case "+": result = firstOperand + second; break;
            case "-": result = firstOperand - second; break;
            case "×": result = firstOperand * second; break;
            case "÷":
                if (second == 0) {
                    tvDisplay.setText("Error");
                    currentNumber = "";
                    firstOperand  = 0;
                    operator      = "";
                    isNewOperation = true;
                    return;
                }
                result = firstOperand / second;
                break;
            default: return;
        }
        firstOperand   = result;
        currentNumber  = "";
        isNewOperation = true;
        updateDisplay(formatNumber(result));
    }

    // ── Limpiar ───────────────────────────────────────────────────────────────
    private void onClearPressed() {
        currentNumber  = "";
        operator       = "";
        firstOperand   = 0;
        isNewOperation = false;
        hasResult      = false;
        fullExpression = "";
        tvDisplay.setText("0");
        tvExpression.setText("");
    }

    // ── Borrar un carácter ────────────────────────────────────────────────────
    private void onDeletePressed() {
        if (!currentNumber.isEmpty() && !hasResult) {
            currentNumber = currentNumber.substring(0, currentNumber.length() - 1);
            if (currentNumber.isEmpty() || currentNumber.equals("-")) {
                currentNumber = "";
                updateDisplay("0");
            } else {
                updateDisplay(currentNumber);
            }
        }
    }

    // ── Porcentaje ────────────────────────────────────────────────────────────
    private void onPercentPressed() {
        if (!currentNumber.isEmpty()) {
            double val = parseNumber(currentNumber) / 100.0;
            currentNumber = formatNumber(val);
            updateDisplay(currentNumber);
        }
    }

    // ── Cambio de signo ───────────────────────────────────────────────────────
    private void onSignPressed() {
        if (!currentNumber.isEmpty() && !currentNumber.equals("0")) {
            if (currentNumber.startsWith("-")) {
                currentNumber = currentNumber.substring(1);
            } else {
                currentNumber = "-" + currentNumber;
            }
            updateDisplay(currentNumber);
        }
    }

    // ── Utilidades ────────────────────────────────────────────────────────────
    private void updateDisplay(String text) {
        if (text == null || text.isEmpty()) {
            tvDisplay.setText("0");
        } else {
            tvDisplay.setText(text);
        }
        // Ajustar tamaño de texto según longitud
        if (text != null && text.length() > 10) {
            tvDisplay.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 36);
        } else if (text != null && text.length() > 7) {
            tvDisplay.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 48);
        } else {
            tvDisplay.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 64);
        }
    }

    private double parseNumber(String s) {
        try { return Double.parseDouble(s); }
        catch (NumberFormatException e) { return 0; }
    }

    private String formatNumber(double n) {
        if (n == (long) n) {
            return String.valueOf((long) n);
        } else {
            // Máx. 8 decimales, sin ceros finales
            String formatted = String.format("%.8f", n).replaceAll("0+$", "").replaceAll("\\.$", "");
            return formatted;
        }
    }

    // ── Animación de botón ────────────────────────────────────────────────────
    private void animateButton(View v) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(v, "scaleX", 1f, 0.88f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(v, "scaleY", 1f, 0.88f, 1f);
        scaleX.setDuration(120);
        scaleY.setDuration(120);
        scaleX.start();
        scaleY.start();
    }
}
