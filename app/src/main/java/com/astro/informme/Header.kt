package com.astro.informme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner

/**
 * A simple [Fragment] subclass.
 * Use the [Header.newInstance] factory method to
 * create an instance of this fragment.
 */
class Header : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_header, container, false)
        val spinner = view.findViewById<Spinner>(R.id.country_spinner)

        ArrayAdapter.createFromResource(
            view.context,
            R.array.countries,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        return view;
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            Header()
    }
}