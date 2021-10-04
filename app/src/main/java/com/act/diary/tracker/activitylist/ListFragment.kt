package com.act.diary.tracker.activitylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.act.diary.tracker.R
import com.act.diary.tracker.database.DiaryActivityDatabase
import com.act.diary.tracker.databinding.FragmentListBinding
import com.google.android.material.snackbar.Snackbar

class ListFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: ActivityAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list, container, false
        )
        val app = requireNotNull(this.activity).application

        val database = DiaryActivityDatabase.getInstance(app).diaryActivityDatabaseDao

        val viewModelFactory = ListViewModelFactory(
            database, app
        )

        viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(ListViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        initView()

        setEvents()
        setObservers()
        return binding.root
    }

    private fun initView() {
        adapter = ActivityAdapter() {
            this.findNavController().navigate(
                ListFragmentDirections
                    .actionListFragmentToDetailFragment(it.activityId)
            )
            viewModel.doneNavigating()
        }
        binding.recyclerView.adapter = adapter
    }

    private fun setObservers() {
        viewModel.showSnackBarEvent.observe(viewLifecycleOwner) {
            if (it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.cleared_message),
                    Snackbar.LENGTH_SHORT
                ).show()
                viewModel.doneShowingSnackbar()
            }
        }

        viewModel.navigateToDetail.observe(viewLifecycleOwner) { night ->
            night?.let {
                this.findNavController().navigate(
                    ListFragmentDirections
                        .actionListFragmentToDetailFragment(night.activityId)
                )
                viewModel.doneNavigating()
            }
        }

        viewModel.listRecords.observe(viewLifecycleOwner) { list ->
            adapter.updateList(list)
        }

    }

    private fun setEvents() {
        binding.startButton.setOnClickListener {
            viewModel.onStartTracking(binding.newActivity.text.toString())
            binding.newActivity.setText("")
        }
    }


}
