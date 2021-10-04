package com.act.diary.tracker.detailactivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.act.diary.tracker.R
import com.act.diary.tracker.convertLongToDateString
import com.act.diary.tracker.database.DiaryActivityDatabase
import com.act.diary.tracker.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false
        )

        val application = requireNotNull(this.activity).application

        val arguments = DetailFragmentArgs.fromBundle(arguments!!)

        val dataSource = DiaryActivityDatabase.getInstance(application).diaryActivityDatabaseDao

        val viewModelFactory = DetailViewModelFactory(arguments.activityKey, dataSource)

        viewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(DetailViewModel::class.java)


        addObservers()
        addEvents()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun addObservers() {
        viewModel.navigateToList.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController().navigate(
                    DetailFragmentDirections.actionDetailFragmentToListFragment()
                )
                viewModel.doneNavigating()
            }
        })

        viewModel.data.observe(viewLifecycleOwner, {
            it?.let {
                binding.inputTypeActivity.setText(it.type)
                binding.inputDescriptionActivity.setText(it.comment)

                binding.dateStart.text = String.format(
                    "Start %s",
                    convertLongToDateString(it.startTimeMilli)
                )
                val value = if (it.endTimeMilli == 0L) "Running" else String.format(
                    "End %s",
                    convertLongToDateString(it.endTimeMilli)
                )
                binding.dateEnd.text = value
            }

        })
        viewModel.retrieveData()
    }

    private fun addEvents() {
        binding.doneButton.setOnClickListener {
            viewModel.onSetDiaryActivity(
                binding.inputTypeActivity.text.toString(),
                binding.inputDescriptionActivity.text.toString()
            )
        }

    }
}
