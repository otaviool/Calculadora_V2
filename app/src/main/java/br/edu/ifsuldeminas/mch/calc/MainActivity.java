package br.edu.ifsuldeminas.mch.calc;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ifsuldeminas.mch.calc";


    private TextView textViewResultado;
    private TextView textViewUltimaExpressao;
    private Button buttonReset, buttonDelete, buttonIgual, buttonPorcento;
    private Button buttonSoma, buttonSub, buttonMult, buttonDiv, buttonVirgula;
    private Button[] buttonsNumero = new Button[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vincularComponentes();
        configurarCliques();
    }

    private void vincularComponentes() {
        textViewResultado = findViewById(R.id.textViewResultadoID);
        textViewUltimaExpressao = findViewById(R.id.textViewUltimaExpressaoID);

        buttonReset = findViewById(R.id.buttonResetID);     // Botão C
        buttonDelete = findViewById(R.id.buttonDeleteID);   // Botão D
        buttonPorcento = findViewById(R.id.buttonPorcentoID);
        buttonIgual = findViewById(R.id.buttonIgualID);

        buttonSoma = findViewById(R.id.buttonSomaID);
        buttonSub = findViewById(R.id.buttonSubtracaoID);
        buttonMult = findViewById(R.id.buttonMultiplicacaoID);
        buttonDiv = findViewById(R.id.buttonDivisaoID);
        buttonVirgula = findViewById(R.id.buttonVirgulaID);


        buttonsNumero[0] = findViewById(R.id.buttonZeroID);
        buttonsNumero[1] = findViewById(R.id.buttonUmID);
        buttonsNumero[2] = findViewById(R.id.buttonDoisID);
        buttonsNumero[3] = findViewById(R.id.buttonTresID);
        buttonsNumero[4] = findViewById(R.id.buttonQuatroID);
        buttonsNumero[5] = findViewById(R.id.buttonCincoID);
        buttonsNumero[6] = findViewById(R.id.buttonSeisID);
        buttonsNumero[7] = findViewById(R.id.buttonSeteID);
        buttonsNumero[8] = findViewById(R.id.buttonOitoID);
        buttonsNumero[9] = findViewById(R.id.buttonNoveID);
    }

    private void configurarCliques() {
        buttonReset.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonPorcento.setOnClickListener(this);
        buttonIgual.setOnClickListener(this);
        buttonSoma.setOnClickListener(this);
        buttonSub.setOnClickListener(this);
        buttonMult.setOnClickListener(this);
        buttonDiv.setOnClickListener(this);
        buttonVirgula.setOnClickListener(this);

        for (Button btn : buttonsNumero) {
            if (btn != null) btn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.buttonResetID) {
            textViewResultado.setText("");
            textViewUltimaExpressao.setText("");
        }
        else if (id == R.id.buttonDeleteID) {
            String texto = textViewResultado.getText().toString();
            if (!texto.isEmpty()) {
                textViewResultado.setText(texto.substring(0, texto.length() - 1));
            }
        }
        else if (id == R.id.buttonIgualID) {
            executarCalculo();
        }
        else if (id == R.id.buttonPorcentoID) {

            tratarPorcentagem();
        }
        else {

            Button b = (Button) view;
            String valorBotao = b.getText().toString();



            if (textViewResultado.getText().toString().equals("0")) {
                textViewResultado.setText(valorBotao);
            } else {
                textViewResultado.append(valorBotao);
            }
        }
    }

    private void executarCalculo() {
        try {
            String expressao = textViewResultado.getText().toString();



            if (expressao.contains("%")) {
                // Essa é uma lógica simples que substitui o símbolo % por /100
                expressao = expressao.replace("%", "/100");
            }

            String expressaoParaCalculo = expressao.replace(',', '.')
                    .replace('÷', '/')
                    .replace('x', '*');

            Calculable avaliador = new ExpressionBuilder(expressaoParaCalculo).build();
            double resultado = avaliador.calculate();

            textViewUltimaExpressao.setText(textViewResultado.getText().toString());


            if (resultado == (long) resultado) {
                textViewResultado.setText(String.format("%d", (long) resultado));
            } else {
                textViewResultado.setText(String.format("%.2f", resultado).replace('.', ','));
            }

        } catch (Exception e) {
            Log.e(TAG, "Erro: " + e.getMessage());
            textViewResultado.setText("Erro");
        }
    }

    private void tratarPorcentagem() {

        try {
            double valor = Double.parseDouble(textViewResultado.getText().toString().replace(',', '.'));
            double resultado = valor / 100;
            textViewResultado.setText(String.valueOf(resultado).replace('.', ','));
        } catch (Exception e) {
            Log.d(TAG, "Erro na porcentagem");
        }
    }
}