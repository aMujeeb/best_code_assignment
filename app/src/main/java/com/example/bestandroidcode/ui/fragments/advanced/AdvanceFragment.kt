package com.example.bestandroidcode.ui.fragments.advanced

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.bestandroidcode.R
import com.example.bestandroidcode.databinding.AdvanceFragmentBinding
import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.ui.activities.main.FavouriteCallBack
import com.example.bestandroidcode.ui.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdvanceFragment : BaseFragment(), View.OnClickListener {

    private var mAdvancedFragmentBinding: AdvanceFragmentBinding? = null
    private lateinit var iFavouriteCallBack: FavouriteCallBack
    private val mAdvancedViewModel: AdvancedViewModel by viewModels()

    companion object {
        fun newInstance(favouriteCallBack: FavouriteCallBack): AdvanceFragment {
            var instance = AdvanceFragment()
            instance.iFavouriteCallBack = favouriteCallBack
            return instance
        }
    }

    var currentCatObject: Cat? = null
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mAdvancedFragmentBinding!!.spCategory.adapter = adapter

        mAdvancedFragmentBinding!!.spCategory.onItemSelectedListener =
            object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    mAdvancedViewModel.setSelectedCategory(categoryIdList[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        mAdvancedFragmentBinding!!.btnAnswer.setOnClickListener(this)

        mAdvancedViewModel.generateQuestion()
        mAdvancedViewModel.mAdvanceViewState.observe(viewLifecycleOwner, Observer {
            when {
                it?.mGenerateQuestion != null -> {
                    mAdvancedFragmentBinding!!.tvQuestion.text = it.mGenerateQuestion
                }
                it?.mIsAnswerWrong == true -> {
                    Toast.makeText(
                        activity,
                        getString(R.string.un_approved_answer),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                it?.isLoading == true -> {
                    mAdvancedFragmentBinding!!.mProgressAdvanced.visibility = View.VISIBLE
                }
                it?.imageUrl != null -> {
                    mAdvancedFragmentBinding!!.mProgressAdvanced.visibility = View.GONE
                    iFavouriteCallBack.isFavouriteSelected(false)
                    Glide.with(this@AdvanceFragment)
                        .load(it.imageUrl)
                        .into(mAdvancedFragmentBinding!!.ivCat)
                }
                it?.isAlreadySaved == true -> {
                    iFavouriteCallBack.isFavouriteSelected(false)
                }
                it?.isAdded == true -> {
                    iFavouriteCallBack.isFavouriteSelected(true)
                }
            }
        })
    }


    override fun onClick(p0: View?) {
        if (p0 == mAdvancedFragmentBinding!!.btnAnswer) {
            mAdvancedViewModel.validateQuestion(mAdvancedFragmentBinding!!.etAnswer.text.toString())
        }
    }

    override fun addToFavoriteList() {
        Log.d("BestApp", "Add at Favourite")
        mAdvancedViewModel.saveCatRecord()
    }
}