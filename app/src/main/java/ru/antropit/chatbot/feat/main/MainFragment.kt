package ru.antropit.chatbot.feat.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.antropit.chatbot.MainActivity
import ru.antropit.chatbot.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        var adapter = MainAdapter()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        activity?.title = "Chat room"
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val recyclerView = activity?.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter

        viewModel = ViewModelProvider(this,
            MainViewModel.FACTORY(MutableLiveData<String>("Hello")))
            .get(MainViewModel::class.java)
    }
}
