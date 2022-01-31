package com.example.bestandroidcode.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.bestandroidcode.R
import com.example.bestandroidcode.databinding.MainFragmentBinding
import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.ui.advanced.AdvanceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private var mMainFragmentBinding : MainFragmentBinding? = null

    var currentCatObject: Cat? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mMainFragmentBinding =
            MainFragmentBinding.inflate(inflater, container, false)
        return mMainFragmentBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMainFragmentBinding!!.btnLoadCat.setOnClickListener(this)
        mMainFragmentBinding!!.btnProUser.setOnClickListener(this)

        viewModel.mMainViewState.observe(viewLifecycleOwner, Observer {
            when {
                it?.isLoading == true -> {
                    mMainFragmentBinding!!.mMainProgress.visibility = View.VISIBLE
                }
                it?.imageUrl != null ->{
                    mMainFragmentBinding!!.mMainProgress.visibility = View.GONE
                    Glide.with(this@MainFragment)
                        .load(it.imageUrl)
                        .into(mMainFragmentBinding!!.ivCat)
                }
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*mMainFragmentBinding!!.btnLoadCat.setOnClickListener {
            val request = ServiceBuilder.buildService(CatAPI::class.java)
            val call = request.getCatRandom()

            call.enqueue(object : Callback<List<Cat>> {
                override fun onResponse(call: Call<List<Cat>>, response: Response<List<Cat>>) {
                    if (response.isSuccessful) {

                        currentCatObject = response.body()!!.first()

                        Glide.with(this@MainFragment)
                            .load(response.body()!!.first().url)
                            .into(ivCat)

                        val activity = activity as MainActivity
                        activity.refreshFavoriteButton(currentCatObject!!.url)
                    }
                }

                override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                    Toast.makeText(activity, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }*/
    }

    override fun onClick(p0: View?) {
        if(p0 == mMainFragmentBinding!!.btnLoadCat) {
            viewModel.requestRandomCatImage()
        } else if(p0 == mMainFragmentBinding!!.btnProUser) {
            val advanceFragment = AdvanceFragment()

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, advanceFragment)
            transaction.addToBackStack("")
            transaction.commit()
        }
    }
}