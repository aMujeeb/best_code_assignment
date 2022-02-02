package com.example.bestandroidcode.ui.fragments.advanced

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.bestandroidcode.R
import com.example.bestandroidcode.databinding.AdvanceFragmentBinding
import com.example.bestandroidcode.ui.activities.main.MainActivityViewModel
import com.example.bestandroidcode.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AdvanceFragment : BaseFragment(), View.OnClickListener {

    private var mAdvancedFragmentBinding: AdvanceFragmentBinding? = null
    private val mAdvancedViewModel: AdvancedViewModel by viewModels()
    private val mMainActivityViewModel: MainActivityViewModel by activityViewModels()

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mAdvancedFragmentBinding = AdvanceFragmentBinding.inflate(inflater, container, false)
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
        mMainActivityViewModel.mIsAddedSuccessfully.value = false
        mAdvancedViewModel.generateQuestion()
        mAdvancedViewModel.mAdvanceViewState.observe(viewLifecycleOwner, Observer {
            when {
                it?.mGenerateQuestion != null -> {
                    mAdvancedFragmentBinding!!.tvQuestion.text = it.mGenerateQuestion
                    mAdvancedFragmentBinding!!.etAnswer.text.clear()
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
                    Glide.with(this@AdvanceFragment)
                        .load(it.imageUrl)
                        .into(mAdvancedFragmentBinding!!.ivCat)
                }
                it?.isAlreadySaved == true -> {
                    mMainActivityViewModel.mIsAddedSuccessfully.value = true
                }
                it?.isAdded == true -> {
                    mMainActivityViewModel.mIsAddedSuccessfully.value = true
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
        mAdvancedViewModel.saveCatRecord()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true
        ) {
            override fun handleOnBackPressed() {
                mMainActivityViewModel.mIsAddedSuccessfully.value = false
                this.remove()
                requireActivity().onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }
}