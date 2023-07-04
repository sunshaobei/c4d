package com.satis.c4d

import android.content.Context
import android.content.res.Resources
import android.content.res.Resources.Theme
import dalvik.system.DexClassLoader
import java.io.File

/**
 * @author sunshaobei on 2023/7/1
 */
object C4dCore {
    private const val C4D_PATH = ".c4d"
    private const val C4D_CACHE_PATH = ".c4d-cache"
    private lateinit var pluginClassLoader:ClassLoader
    private lateinit var dexDir:File
    private lateinit var dexCache:File
    fun init(context:Context){
        pluginClassLoader = this.javaClass.classLoader!!
        dexDir = File(context.externalCacheDir, C4D_PATH)
        dexCache = File(context.externalCacheDir, C4D_CACHE_PATH)
        createIfNotExist(dexDir)
        val dexFiles = dexDir.listFiles()
        dexFiles?.forEach {
            loadDex(it)
        }
    }

    private fun loadDex(dexParent: File){
        val dexFilePath = dexParent.absolutePath+"classes.dex"
        pluginClassLoader = DexClassLoader(dexFilePath, dexCache.absolutePath, null, pluginClassLoader)
    }


    private fun createIfNotExist(file: File):Boolean{
        return if (file.exists()) file.isDirectory else file.mkdirs()
    }

}