package com.adambennett.jsonplaceholderapp.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.adambennett.jsonplaceholderapp.R
import com.adambennett.jsonplaceholderapp.ui.MainActivityCallback
import com.adambennett.jsonplaceholderapp.ui.MainViewModel
import com.adambennett.jsonplaceholderapp.ui.utils.ParentActivityDelegate
import com.adambennett.jsonplaceholderapp.ui.utils.inflate
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment() {

    private val compositeDisposable = CompositeDisposable()
    private val model: MainViewModel by viewModel()
    private val parentCallback: MainActivityCallback by ParentActivityDelegate(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = container?.inflate(R.layout.fragment_list)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    companion object {

        fun newInstance() = ListFragment()
    }
}