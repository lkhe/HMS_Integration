package com.eric.huawei.Base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.eric.huawei.DummyFragment
import com.eric.huawei.EricViewPagerAdapter
import com.eric.huawei.R
import com.eric.huawei.map.MapFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_content.*

/*
    To abstract away non-hms stuff
 */

abstract class BaseMainActivity : AppCompatActivity() {

    lateinit var drawerLayout : DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
            it.setDisplayHomeAsUpEnabled(true)
        }

        val navigationView : NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.setChecked(true)
            drawerLayout.closeDrawers()
            true
        }

        val viewPager: ViewPager = findViewById(R.id.viewpager)
        val adapter =
            EricViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(DummyFragment.newInstance(), "1")
        adapter.addFragment(MapFragment.newInstance(), "2")
        adapter.addFragment(DummyFragment.newInstance(), "3")
        viewPager.adapter = adapter


        val tablayout: TabLayout = findViewById(R.id.tabLayout)
        tablayout.setupWithViewPager(viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> drawerLayout.openDrawer(GravityCompat.START)

        }
        return super.onOptionsItemSelected(item)
    }
}