package com.aysegul.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.aysegul.besinlerkitabi.R
import com.aysegul.besinlerkitabi.adapter.BesinRecyclerAdapter
import com.aysegul.besinlerkitabi.viewmodel.BesinListesiViewModel
import kotlinx.android.synthetic.main.fragment_besin_detayi.*
import kotlinx.android.synthetic.main.fragment_besin_listesi.*


class BesinListesiFragment : Fragment() {

    private lateinit var viewModel : BesinListesiViewModel  //Viewmodel 'ın objesini oluşturduk
    private val recyclerBesinAdapter = BesinRecyclerAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_besin_listesi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(BesinListesiViewModel::class.java)   //Bu fragment la BesinListesiViewModel'i bağlıyorum
        viewModel.refreshFromInternet()


        besinListRecycler.layoutManager = LinearLayoutManager(context)  //Recycler işlemini yaptık
        besinListRecycler.adapter = recyclerBesinAdapter

        swipeRefreshLayout.setOnRefreshListener { //Kullanıcı refresh ettiğinde ne yapması gerektiğini anlatan bir kod bloğu
            besinYukleniyor.visibility = View.VISIBLE
            besinHataMesaji.visibility = View.GONE
            besinListRecycler.visibility = View.GONE
            viewModel.refreshData()
            swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()


    }
    fun observeLiveData(){     //Gözlemleme işlemlerinin yapıldığı kod blokları

        viewModel.besinler.observe(viewLifecycleOwner, Observer { besinler ->
            besinler?.let {
                besinListRecycler.visibility = View.VISIBLE
                recyclerBesinAdapter.besinListesiniGüncelle(besinler)
            }

        })

        viewModel.besinHataMesaji.observe(viewLifecycleOwner, Observer {hata ->
            hata?.let{
                if(it){
                    besinHataMesaji.visibility = View.VISIBLE
                    besinListRecycler.visibility = View.GONE

                }else{
                    besinHataMesaji.visibility = View.GONE

                }
            }

        })
        viewModel.besinYukleniyor.observe(viewLifecycleOwner, Observer { yuleniyor ->
            yuleniyor?.let {
                if(it){
                    besinListRecycler.visibility = View.GONE
                    besinHataMesaji.visibility = View.GONE
                    besinYukleniyor.visibility = View.VISIBLE

                }else{
                    besinYukleniyor.visibility = View.GONE

                }
            }
        })


    }

    
}