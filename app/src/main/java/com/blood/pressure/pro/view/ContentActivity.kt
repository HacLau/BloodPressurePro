package com.blood.pressure.pro.view

import android.app.Activity
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.blood.pressure.pro.R
import com.blood.pressure.pro.adapter.RecordAdapter
import com.blood.pressure.pro.databinding.ActivityContentBinding
import com.blood.pressure.pro.db.RecordManager
import com.blood.pressure.pro.model.Info
import com.blood.pressure.pro.model.RecordEntity
import com.blood.pressure.pro.ui.ItemBottomDecoration
import com.blood.pressure.pro.ui.ItemLRBottomDecoration

class ContentActivity : BaseActivity<ActivityContentBinding>(R.layout.activity_content) {
    private lateinit var pageType: String
    override fun initView() {
        pageType = intent.getStringExtra(BaseName.type) ?: BaseName.more
        binding.back.setOnClickListener {
            finish()
        }
    }

    override fun initData() {
        when (pageType) {
            BaseName.more -> initRecordMore()
            BaseName.url -> initRecordWeb()
            BaseName.info -> initRecordInfo()
            else -> "No type in More And Url".logE()
        }
    }

    private fun initRecordInfo() {
        binding.clInfo.visibility = View.VISIBLE
        intent.getParcelableExtra<Info>(BaseName.info)?.let{
            binding.info = it
            binding.titleText = it.title
            binding.itemImage.setImageResource(it.image)
        }
    }

    private fun initRecordMore() {

        var recordList: List<RecordEntity> = RecordManager.query()
        fun setRecordList() {
            recordList = RecordManager.query()
            setRecordMoreView(recordList)
        }
        binding.titleText = viewModel.appDataEntity.title.moreRecord
        binding.noBtn.setOnClickListener {
            startRecordNewActivity {
                setResult(Activity.RESULT_OK)
                setRecordList()
            }
        }
        binding.moreRv.apply {
            addItemDecoration(ItemLRBottomDecoration(12))
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = RecordAdapter(this@ContentActivity).apply {
                list = recordList
                onItemClick = {
                    startRecordNewActivity {
                        setResult(Activity.RESULT_OK)
                        setRecordList()
                    }
                }
                onItemLongClick = {
                    setResult(Activity.RESULT_OK)
                    RecordManager.delete(it)
                    setRecordList()
                }
            }
        }

        setRecordList()

    }

    private fun setRecordMoreView(list: List<RecordEntity>) {
        list.let {
            if (it.isEmpty()) {
                binding.noBtn.visibility = View.VISIBLE
                binding.moreRv.visibility = View.GONE
            } else {
                binding.noBtn.visibility = View.GONE
                binding.moreRv.visibility = View.VISIBLE
                (binding.moreRv.adapter as RecordAdapter).list = it
            }
        }
    }

    private fun initRecordWeb() {
        binding.webView.visibility = View.VISIBLE
        intent.getStringExtra(BaseName.title).let {
            binding.titleText = it
        }
        intent.getStringExtra(BaseName.url)?.let {
            binding.webView.loadUrl(it)
        }
    }

}