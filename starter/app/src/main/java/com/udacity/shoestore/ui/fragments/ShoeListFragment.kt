package com.udacity.shoestore.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeListBinding
import com.udacity.shoestore.databinding.ItemShoeBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.ui.activities.MainActivity
import com.udacity.shoestore.view_models.ShoeListViewModel
import timber.log.Timber
import java.lang.ref.WeakReference

class ShoeListFragment : Fragment() {

    private var mBinding: FragmentShoeListBinding? = null
    private var activityWeakReference: WeakReference<MainActivity?>? = null
    private var navController: NavController? = null
    companion object {
        var shoeList: MutableList<Shoe> = mutableListOf()
    }

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
        navController?.currentBackStackEntry?.savedStateHandle?.getLiveData<MutableList<Shoe>>("key")
            ?.observe(
                viewLifecycleOwner
            ) { it ->
                val tempList = mutableListOf<Shoe>()
                if (it.isEmpty().not()) {
                    for (item in it) {
                        Timber.d("Shoe Name %s, Shoe Company %s", item.name, item.company)
                        tempList.add(item)
                    }
                    shoeList = tempList
                }
                mBinding?.shoeLayout?.removeAllViews()
                val inflater = LayoutInflater.from(getActivityWeakReference())
                for (shoe in tempList) {
                    val mShoeItemBinding: ItemShoeBinding = DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_shoe, null, false
                    )
                    mBinding?.shoeLayout?.addView(mShoeItemBinding.root)
                    mShoeItemBinding.shoeNameVal.text = shoe.name
                    mShoeItemBinding.companyVal.text = shoe.company
                    mShoeItemBinding.sizeVal.text = shoe.size.toString()
                    mShoeItemBinding.descriptionVal.text = shoe.description
                }

            }
    }
}