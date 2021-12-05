package com.example.bestandroidcode.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.bestandroidcode.MainActivity
import com.example.bestandroidcode.R
import com.example.bestandroidcode.model.Cat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*

@AndroidEntryPoint
class AdvanceFragment : Fragment() {

    var currentCatObject: Cat? = null

    private lateinit var ivCat: ImageView
    private lateinit var spCategory: Spinner
    private lateinit var tvQuestion: TextView
    private lateinit var etAnswer: EditText
    private lateinit var btnAnswer: Button

    private val categoryList = arrayOf(
        "Boxes",
        "Clothes",
        "Hats",
        "Sinks",
        "Space",
        "Sunglasses",
        "Ties"
    )
    private val categoryIdList = arrayOf(5, 15, 1, 14, 2, 4, 7)

    private var selectedCategoryId: Int = -1
    private var variableA: Int = 0
    private var variableB: Int = 0
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.advance_fragment, container, false)
        activity?.title  = "Cats"
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        ivCat = view.findViewById(R.id.ivCat)
        spCategory = view.findViewById(R.id.spCategory)
        tvQuestion = view.findViewById(R.id.tvQuestion)
        etAnswer = view.findViewById(R.id.etAnswer)
        btnAnswer = view.findViewById(R.id.btnAnswer)

        generateQuestion()

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spCategory.adapter = adapter

        spCategory.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                selectedCategoryId = categoryIdList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        btnAnswer.setOnClickListener {
            val answer = etAnswer.text.toString().toIntOrNull()

            if (answer != null) {
                if (variableA + variableB == answer) {
                    viewModel.getCatBasedOnCategory(selectedCategoryId.toString())
                    observeCatData()

                } else {
                    Toast.makeText(activity, "The Meow Lord did not approve your answer!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, "The Meow Lord did not approve your answer!", Toast.LENGTH_SHORT).show()
            }

        }

        return view
    }

    private fun generateQuestion() {
        variableA = (0..10).random()
        variableB = (0..10).random()

        tvQuestion.text = "${variableA} + ${variableB} = ?"
    }

    fun observeCatData() {
        viewModel.randomCatDataList.observe(viewLifecycleOwner, Observer {
            currentCatObject = it.body()!!.first()
            Glide.with(this@AdvanceFragment)
                .load(it.body()!!.first().url)
                .into(ivCat)

            val activity = activity as MainActivity
            activity.refreshFavoriteButton(currentCatObject!!.url)

            generateQuestion()
            etAnswer.setText("")
        })
    }

}