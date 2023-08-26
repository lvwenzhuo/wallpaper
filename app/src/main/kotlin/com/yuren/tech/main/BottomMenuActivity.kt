package com.yuren.tech.main

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.skydoves.androidbottombar.BottomMenuItem
import com.skydoves.androidbottombar.OnBottomMenuInitializedListener
import com.skydoves.androidbottombar.OnMenuItemSelectedListener
import com.skydoves.androidbottombar.animations.BadgeAnimation
import com.skydoves.androidbottombar.forms.badgeForm
import com.skydoves.androidbottombar.forms.iconForm
import com.skydoves.androidbottombar.forms.titleForm
import com.yuren.base.BaseActivity
import com.yuren.tech.R
import kotlinx.android.synthetic.main.layout_main.*

abstract class BottomMenuActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.layout_main);

        var dockItems = supplyData();
        initPage(dockItems);

    }

    private fun initPage(dockItems: List<DockItem>) {
        with(viewpager) {
            adapter = object : FragmentStateAdapter(this@BottomMenuActivity) {
                override fun getItemCount(): Int {
                    return dockItems.count();
                }

                override fun createFragment(position: Int): Fragment {
                    var target = dockItems.get(position).target;
                    return target.newInstance() as Fragment;
                }

            }
        }

        initBottom(dockItems);


        androidBottomBar.onMenuItemSelectedListener = OnMenuItemSelectedListener { index, _, _ ->
            viewpager.currentItem = index
           androidBottomBar.dismissBadge(index)
        }


        androidBottomBar.onBottomMenuInitializedListener = OnBottomMenuInitializedListener {
            androidBottomBar.bindViewPager2(viewpager)
        }
    }

    private fun initBottom(dockItems: List<DockItem>) {
        val titleForm = titleForm(this) {
            setTitleColor(Color.BLACK)
            setTitleActiveColorRes(R.color.md_blue_200)
        }

        val iconForm = iconForm(this) {
            setIconSize(28)
        }

        val badgeForm = badgeForm(this) {
            setBadgeTextSize(9f)
            setBadgePaddingLeft(2)
            setBadgePaddingRight(2)
            setBadgeDuration(550)
        }
        androidBottomBar.addBottomMenuItems(
            mutableListOf<BottomMenuItem>().apply {
                dockItems.forEach {
                    this.add(
                        BottomMenuItem(this@BottomMenuActivity)
                            .setTitleForm(titleForm)
                            .setIconForm(iconForm)
                            .setBadgeForm(badgeForm)
                            .setTitle(it.title)
                            .setIcon(it.iconResId)
                            .setBadgeAnimation(BadgeAnimation.NONE)
                            .build()
                    )

                }
            }

        )

    }

    abstract fun supplyData(): List<DockItem>

}