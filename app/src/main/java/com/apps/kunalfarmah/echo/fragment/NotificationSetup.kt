package com.apps.kunalfarmah.echo.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.ImageView
import com.apps.kunalfarmah.echo.R
import com.apps.kunalfarmah.echo.activity.MainActivity
import androidx.fragment.app.Fragment
import com.apps.kunalfarmah.echo.activity.SettingsActivity
import com.apps.kunalfarmah.echo.activity.WizardActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class NotificationSetup : Fragment(){

    var next:ImageView?=null
    var act:Activity?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.setup_oreo, container,false)

        activity?.title="Setup"
        setHasOptionsMenu(true)

        next=view?.findViewById(R.id.next)



        return view

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        act = context as Activity
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        act = activity
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        //removing the sorting adn searching options as they r not needed

        val item = menu.findItem(R.id.action_sort)
        item?.isVisible=false

        val item1 = menu.findItem(R.id.action_search)
        item1?.isVisible=false
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        next?.setOnClickListener ({

            val wizard = Wizard()

            (context as WizardActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment, wizard)
                    .addToBackStack("Wizard")
                    .commit()

        })
    }



}