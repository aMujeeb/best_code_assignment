package com.example.bestandroidcode.ui.advanced

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import com.example.bestandroidcode.databinding.AdvanceFragmentBinding
import com.example.bestandroidcode.model.Cat


class AdvanceFragment : Fragment() {

    private var mAdvancedFragmentBinding : AdvanceFragmentBinding? = null

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mAdvancedFragmentBinding = AdvanceFragmentBinding.inflate(inflater, container, false)

        ivCat = mAdvancedFragmentBinding!!.ivCat
        spCategory = mAdvancedFragmentBinding!!.spCategory
        tvQuestion = mAdvancedFragmentBinding!!.tvQuestion
        etAnswer = mAdvancedFragmentBinding!!.etAnswer
        btnAnswer = mAdvancedFragmentBinding!!.btnAnswer

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

        /*btnAnswer.setOnClickListener {
            val answer = etAnswer.text.toString().toIntOrNull()

            if (answer != null) {
                if (variableA + variableB == answer) {
                    val request = ServiceBuilder.buildService(CatAPI::class.java)
                    val call = request.getCatBasedOnCategory(selectedCategoryId.toString())

                    call.enqueue(object : Callback<List<Cat>> {
                        override fun onResponse(call: Call<List<Cat>>, response: Response<List<Cat>>) {
                            if (response.isSuccessful) {

                                currentCatObject = response.body()!!.first()

                                Glide.with(this@AdvanceFragment)
                                    .load(response.body()!!.first().url)
                                    .into(ivCat)

                                val activity = activity as MainActivity
                                activity.refreshFavoriteButton(currentCatObject!!.url)

                                generateQuestion()
                                etAnswer.setText("")
                            }
                        }

                        override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                            Toast.makeText(activity, "${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    Toast.makeText(activity, "The Meow Lord did not approve your answer!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, "The Meow Lord did not approve your answer!", Toast.LENGTH_SHORT).show()
            }

        }*/

        return mAdvancedFragmentBinding!!.root
    }

    private fun generateQuestion() {
        variableA = (0..10).random()
        variableB = (0..10).random()

        tvQuestion.text = "${variableA} + ${variableB} = ?"
    }

}