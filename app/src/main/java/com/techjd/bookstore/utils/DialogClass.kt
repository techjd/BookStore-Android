package com.techjd.bookstore.utils

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.techjd.bookstore.R
import com.techjd.bookstore.databinding.ItemBookBinding


class DialogClass(private val view: View) {
    fun showDialog(msg: String) {

        val builder: AlertDialog.Builder = AlertDialog.Builder(view.context)

        builder.setCancelable(false)
        val view: View =
            LayoutInflater.from(view.context).inflate(R.layout.alert_dialog, null)
        builder.setView(view)

        val textView: TextView = view.findViewById(R.id.title)
        val okay: MaterialButton = view.findViewById(R.id.okayButton)

        textView.text = msg

        val alertDialog: AlertDialog = builder.create()

        okay.setOnClickListener { alertDialog.dismiss() }

        alertDialog.show()
    }
}