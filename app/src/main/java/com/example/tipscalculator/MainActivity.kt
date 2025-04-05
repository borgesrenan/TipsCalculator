package com.example.tipscalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    //declarando as variaveis

    private lateinit var edtBill: EditText
    private lateinit var txtAmountPerPerson: TextView
    private lateinit var txtBillValue: TextView
    private lateinit var txtTipValue: TextView
    private lateinit var peopleCountText: TextView

    private var selectedTipPercentage = 0
    private var peopleCount = 0
    private var maxPeople = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Pegando os valores dos campos que preciso mostrar na tela
        edtBill = findViewById(R.id.edt_text_bill)
        txtAmountPerPerson = findViewById(R.id.textAmountPerPerson)
        txtBillValue = findViewById(R.id.txt_bill_value)
        txtTipValue = findViewById(R.id.txt_tip_value)
        peopleCountText = findViewById(R.id.textPeopleCount)

        //Botoes da porcentagem das tips
        findViewById<Button>(R.id.btnTip10).setOnClickListener { selectTip(10) }
        findViewById<Button>(R.id.btnTip15).setOnClickListener { selectTip(15) }
        findViewById<Button>(R.id.btnTip20).setOnClickListener { selectTip(20) }
        findViewById<Button>(R.id.btnTip25).setOnClickListener { selectTip(25) }

        //Contador de pessoas
        findViewById<Button>(R.id.btnMinus).setOnClickListener { decreasePeople() }
        findViewById<Button>(R.id.btnPlus).setOnClickListener { increasePeople() }

        //resetar
        findViewById<Button>(R.id.btn_calcAgain).setOnClickListener { resetFields() }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    //funcoes

    private fun selectTip(percent: Int) {
        selectedTipPercentage = percent
        calculate()
    }

    private fun increasePeople() {
        if (peopleCount < maxPeople) {
            peopleCount++
            peopleCountText.text = peopleCount.toString()
            calculate()
        }
    }

    private fun decreasePeople() {
        if (peopleCount > 1) {
            peopleCount--
            peopleCountText.text = peopleCount.toString()
            calculate()
        }
    }

    private fun calculate() {
        val billText = edtBill.text.toString()
        val billAmount = billText.toDoubleOrNull()

        if (billAmount == null || billAmount == 0.0 || peopleCount ==
                0 || selectedTipPercentage == 0
        ) {
            txtAmountPerPerson.text = "€0.00"
            txtBillValue.text = "€0.00"
            txtTipValue.text = "€0.00"
            return
        }

        val tipAmount = billAmount * selectedTipPercentage / 100
        val total = billAmount + tipAmount
        val perPerson = total / peopleCount

        txtAmountPerPerson.text = "€ %.2f".format(perPerson)
        txtBillValue.text = "€ %.2f".format(billAmount)
        txtTipValue.text = "€ %.2f".format(tipAmount)
    }

    //resetar todos os campos

    private fun resetFields() {
        edtBill.text.clear()
        peopleCount = 0
        selectedTipPercentage = 0
        peopleCountText.text = "0"
        txtAmountPerPerson.text = "€ 0.00"
        txtBillValue.text = "€ 0.00"
        txtTipValue.text = "€ 0.00"
    }
}