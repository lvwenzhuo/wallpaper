package com.yuren.tech.main

import android.app.WallpaperManager
import android.content.Intent
import android.os.Bundle
import android.service.wallpaper.WallpaperService
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.yuren.tech.R
import com.yuren.tech.album.AlbumFragment
import com.yuren.tech.wallpaper.WallpaperFragment

class MainActivity: BottomMenuActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("xxx","    MainActivity   MainActivity   ");
        super.onCreate(savedInstanceState )
        WallpaperService  ff;
        Intent.ACTION_WALLPAPER_CHANGED
    }

    override fun supplyData(): List<DockItem> {
        return listOf(
            DockItem(AlbumFragment::class.java,"相册",R.drawable.ic_wallpapers),
            DockItem(WallpaperFragment::class.java,"壁纸",R.drawable.ic_collections)
        )
    }

}