package com.blood.pressure.pro.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.blood.pressure.pro.model.Info
import com.blood.pressure.pro.model.RecordEntity
import com.blood.pressure.pro.viewmodel.AppModel

abstract class BaseActivity<VB : ViewDataBinding>(@LayoutRes resId: Int) : AppCompatActivity() {
    val binding: VB by lazy { DataBindingUtil.setContentView(this, resId) }
    lateinit var viewModel: AppModel
    var isResume = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[AppModel::class.java]
        initView()
        initData()
    }

    private var onResultSuccess: () -> Unit = {}


    private var forResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        "it.resultCode = ${it.resultCode}".logE()
        if (it.resultCode == Activity.RESULT_OK) {
            onResultSuccess.invoke()
        }
    }


    abstract fun initView()

    abstract fun initData()

    fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    fun startWebViewActivity(url: String, title: String) =
        startActivity(Intent(this, ContentActivity::class.java).apply {
            putExtra(BaseName.type, BaseName.url)
            putExtra(BaseName.url, url)
            putExtra(BaseName.title, title)
        })

    fun startInfoContentActivity(info: Info) =
        startActivity(Intent(this, ContentActivity::class.java).apply {
            putExtra(BaseName.type, BaseName.info)
            putExtra(BaseName.info, info)
        })

    fun startRecordMoreActivity(onResult: () -> Unit) {
        onResultSuccess = onResult
        forResult.launch(Intent(this, ContentActivity::class.java).apply {
            putExtra(BaseName.type, BaseName.more)
        })
    }

    fun startRecordNewActivity(onResult: () -> Unit) {
        onResultSuccess = onResult
        forResult.launch(Intent(this, RecordActivity::class.java).apply {
            putExtra(BaseName.type, BaseName.new)
        })
    }

    fun startRecordEditActivity(recordEntity: RecordEntity, onResult: () -> Unit) {
        onResultSuccess = onResult
        forResult.launch(Intent(this, RecordActivity::class.java).apply {
            putExtra(BaseName.type, BaseName.edit)
            putExtra(BaseName.record, recordEntity)
        })
    }


    override fun onStart() {
        super.onStart()
        isResume = false
    }

    override fun onPause() {
        super.onPause()
        isResume = false
    }

    override fun onStop() {
        super.onStop()
        isResume = false
    }

    override fun onResume() {
        super.onResume()
        isResume = true
    }
}

object BaseName {
    const val type = "type"
    const val url = "url"
    const val title = "title"
    const val info = "info"
    const val new = "new"
    const val edit = "edit"
    const val record = "record"
    const val more = "more"
}

fun String.toast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun String.logE(activity: Activity) {
    Log.e(activity.packageName, this)
}

fun String.logE() {
    Log.e("BaseActivity", this)
}