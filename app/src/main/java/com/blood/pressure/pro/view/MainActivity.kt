package com.blood.pressure.pro.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.blood.pressure.pro.BuildConfig
import com.blood.pressure.pro.R
import com.blood.pressure.pro.adapter.ChartAdapter
import com.blood.pressure.pro.adapter.InfoAdapter
import com.blood.pressure.pro.adapter.MainAdapter
import com.blood.pressure.pro.adapter.RecordAdapter
import com.blood.pressure.pro.databinding.ActivityMainBinding
import com.blood.pressure.pro.databinding.LayoutHomeBinding
import com.blood.pressure.pro.databinding.LayoutInfoBinding
import com.blood.pressure.pro.databinding.LayoutRecordBinding
import com.blood.pressure.pro.databinding.LayoutSettingBinding
import com.blood.pressure.pro.db.RecordManager
import com.blood.pressure.pro.model.RecordData
import com.blood.pressure.pro.model.RecordEntity
import com.blood.pressure.pro.ui.ItemBottomDecoration
import com.blood.pressure.pro.util.formatTimeMain

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private var recordBinding:LayoutRecordBinding? = null
    private var recordList = RecordManager.query()
    override fun initView() {
        binding.title = viewModel.appDataEntity.title.apply {
            title_list[0].title = System.currentTimeMillis().formatTimeMain()
        }
        binding.position = 0

        binding.vpMain.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                binding.position = position
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
        binding.rgMain.setOnCheckedChangeListener { _, id ->
            binding.vpMain.currentItem = when (id) {
                binding.rbHome.id -> 0
                binding.rbRecord.id -> 1
                binding.rbInfo.id -> 2
                binding.rbSetting.id -> 3
                else -> 0
            }

        }

    }

    override fun initData() {
        binding.vpMain.adapter = MainAdapter(
            mutableListOf(
                initHome(this),
                initRecord(this),
                initInfo(this),
                initSetting(this)
            )
        )

    }

    private fun initHome(activity: MainActivity): View =
        LayoutHomeBinding.inflate(LayoutInflater.from(activity)).apply {
            homeTop = viewModel.appDataEntity.home_top
            homeRl.apply {
                addItemDecoration(ItemBottomDecoration(12))
                layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                adapter = InfoAdapter(
                    this@MainActivity,
                    (if (viewModel.appDataEntity.info.isEmpty())
                        mutableListOf()
                    else
                        viewModel.appDataEntity.info.subList(
                            0,
                            if (viewModel.appDataEntity.info.size > 5)
                                5
                            else
                                viewModel.appDataEntity.info.size
                        )).onEach {
                        it.image = viewModel.infoImageList.random()
                    }
                ) {
                    startInfoContentActivity(it)
                }
            }
            topBtn.setOnClickListener {
                startRecordNewActivity {
                    binding.vpMain.currentItem = 1
                    refreshRecord()
                }
            }
        }.root

    private fun refreshRecord(){
        recordList = RecordManager.query()
        recordBinding?.data = viewModel.recordData(recordList)
        (recordBinding?.rvChart?.adapter as ChartAdapter).list = recordList
        (recordBinding?.rvItem?.adapter as RecordAdapter).list = getItemList()
    }

    private fun initRecord(activity: MainActivity): View =
        LayoutRecordBinding.inflate(LayoutInflater.from(activity)).apply {
            record = viewModel.appDataEntity.record
            recordTopNew.setOnClickListener {
                startRecordNewActivity {
                    refreshRecord()
                }
            }
            recordTopMore.setOnClickListener {
                startRecordMoreActivity {
                    refreshRecord()
                }
            }
            rvChart.apply {
                addItemDecoration(ItemBottomDecoration(0))
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = ChartAdapter(this@MainActivity, max = viewModel.recordData(recordList).max.toInt(), min = viewModel.recordData(recordList).min.toInt()).apply {
                    list = recordList
                    onItemClick = {

                    }
                }
            }

            rvItem.apply {
                addItemDecoration(ItemBottomDecoration(12))
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = RecordAdapter(this@MainActivity).apply {
                    list = getItemList()
                    onItemClick = {
                        startRecordEditActivity(it) {
                            refreshRecord()
                        }
                    }
                }
            }
            recordBinding = this
            refreshRecord()
        }.root

    private fun getItemList(): List<RecordEntity> {
        return recordList.let { it.subList(0, if (it.size > 3) 3 else it.size) }
    }


    private fun initInfo(activity: MainActivity): View =
        LayoutInfoBinding.inflate(LayoutInflater.from(activity)).apply {
            infoRl.apply {
                addItemDecoration(ItemBottomDecoration(12))
                layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                adapter = InfoAdapter(
                    this@MainActivity,
                    viewModel.appDataEntity.info.ifEmpty { mutableListOf() }.onEach {
                        it.image = viewModel.infoImageList.random()
                    }
                ) {
                    startInfoContentActivity(it)
                }
            }
        }.root


    private fun initSetting(activity: MainActivity): View =
        LayoutSettingBinding.inflate(LayoutInflater.from(activity)).apply {
            setting = viewModel.appDataEntity.setting
            clClock.visibility = View.GONE
            clLanguage.visibility = View.GONE
            clPrivacy.apply {
                setOnClickListener {
                    startWebViewActivity(BuildConfig.privacy, viewModel.appDataEntity.guide.privacy)
                }
            }
            clAgreement.apply {
                setOnClickListener {
                    startWebViewActivity(BuildConfig.agreement, viewModel.appDataEntity.guide.agreement)
                }
            }
            clShared.apply {
                setOnClickListener {
                    kotlin.runCatching {
                        startActivity(Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
                        })
                    }
                }
            }
        }.root


}