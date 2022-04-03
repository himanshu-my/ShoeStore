package com.udacity.shoestore.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeListBinding
import com.udacity.shoestore.databinding.ItemShoeBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.ui.activities.MainActivity
import com.udacity.shoestore.view_models.ShoeListViewModel
import java.lang.ref.WeakReference

class ShoeListFragment : Fragment() {

    private var mBinding: FragmentShoeListBinding? = null
    private var activityWeakReference: WeakReference<MainActivity?>? = null
    private var navController: NavController? = null
    var shoeList: MutableList<Shoe>? = mutableListOf()
    private val viewModel: ShoeListViewModel by activityViewModels()


    private fun getActivityWeakReference(): MainActivity? {
        return try {
            activityWeakReference = if (activityWeakReference == null)
                WeakReference<MainActivity?>(requireActivity() as MainActivity?)
            else activityWeakReference
            activityWeakReference!!.get()
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_shoe_list, container, false
        )
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        mBinding?.btnFab?.setOnClickListener {
            getActivityWeakReference()?.openFragment(
                navController!!,
                R.id.action_shoeListFragment_to_shoeDetailFragment, null
            )
        }
        navController?.currentBackStackEntry?.savedStateHandle?.getLiveData<Shoe>("key")
            ?.observe(
                viewLifecycleOwner
            ) { it ->
                shoeList?.add(it)
                mBinding?.shoeLayout?.removeAllViews()
                val inflater = LayoutInflater.from(getActivityWeakReference())
                for (shoe in shoeList!!) {
                    val mShoeItemBinding: ItemShoeBinding = DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_shoe, null, false
                    )
                    mShoeItemBinding.rootView.setOnClickListener {
                        viewModel.selectedItem(shoe)
                        getActivityWeakReference()?.openFragment(
                            navController!!,
                            R.id.action_shoeListFragment_to_shoeDetailFragment, null
                        )
                    }
                    mBinding?.shoeLayout?.addView(mShoeItemBinding.root)
                    mShoeItemBinding.shoe = shoe
                }

            }
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == R.id.action_logout) {
            navController?.navigate(R.id.loginFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDetach() {
        mBinding = null
        activityWeakReference = null
        navController = null
        shoeList = null
        super.onDetach()
    }
}