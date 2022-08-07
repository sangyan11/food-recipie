package com.sangyan.easyfood.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sangyan.easyfood.ui.activity.MainActivity
import com.sangyan.easyfood.adapter.FavoriteMealsAdapter
import com.sangyan.easyfood.viewmodel.HomeViewModel
import com.sangyan.easyfood.databinding.FragmentFavouriateBinding


class FavoriteFragment : Fragment() {
private  lateinit var  binding : FragmentFavouriateBinding
private lateinit var viewModel : HomeViewModel
private lateinit var  favoriteMealsAdapter: FavoriteMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
      viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriateBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        observeFavorites()
        val itemTouchHelper  = object  : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN ,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT ,
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               val position = viewHolder.adapterPosition
                viewModel.deleteMeal(favoriteMealsAdapter.differ.currentList[position])
                Snackbar.make(requireView(),"Removed From Favorites ",Snackbar.LENGTH_LONG).setAction(
                    "Undo",
                    View.OnClickListener {
                        viewModel.insertMeal(favoriteMealsAdapter.differ.currentList[position])
                    }
                ).show()
            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorites)
    }

    private fun prepareRecyclerView() {
        favoriteMealsAdapter = FavoriteMealsAdapter()
        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
             adapter  = favoriteMealsAdapter
        }
    }

    private fun observeFavorites() {
      viewModel.observeFavoriteMealsLiveData().observe(requireActivity(), Observer { meals->
          favoriteMealsAdapter.differ.submitList(meals)
      })
    }

}
