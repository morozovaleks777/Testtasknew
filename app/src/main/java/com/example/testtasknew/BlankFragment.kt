package com.example.testtasknew

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.testtasknew.MyAdapter.Companion.i

const val ARG_OBJECT = "object"

class BlankFragment : Fragment() {
    var arg = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val textView: TextView = view.findViewById(R.id.textView)
            val minusButton = view.findViewById<Button>(R.id.minusButton2)
            if (i >= 2) {
                minusButton.isVisible = true
            }
            textView.text = getInt(ARG_OBJECT).toString()
            arg = getInt(ARG_OBJECT)
        }
    }
}