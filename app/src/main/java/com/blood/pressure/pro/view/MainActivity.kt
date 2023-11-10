package com.blood.pressure.pro.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.blood.pressure.pro.BuildConfig
import com.blood.pressure.pro.R
import com.blood.pressure.pro.adapter.InfoAdapter
import com.blood.pressure.pro.adapter.MainAdapter
import com.blood.pressure.pro.databinding.ActivityMainBinding
import com.blood.pressure.pro.databinding.LayoutHomeBinding
import com.blood.pressure.pro.databinding.LayoutInfoBinding
import com.blood.pressure.pro.databinding.LayoutRecordBinding
import com.blood.pressure.pro.databinding.LayoutSettingBinding
import com.blood.pressure.pro.ui.ItemBottomDecoration
import com.blood.pressure.pro.util.formatTimeMain

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
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
                    startContentActivity(it)
                }
            }
            topBtn.setOnClickListener {
                startNewActivity {
                    binding.vpMain.currentItem = 1
                }
            }
        }.root


    private fun initRecord(activity: MainActivity): View = LayoutRecordBinding.inflate(LayoutInflater.from(activity)).apply {

    }.root


    private fun initInfo(activity: MainActivity): View = LayoutInfoBinding.inflate(LayoutInflater.from(activity)).apply {
        infoRl.apply {
            addItemDecoration(ItemBottomDecoration(12))
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = InfoAdapter(
                this@MainActivity,
                viewModel.appDataEntity.info.ifEmpty { mutableListOf() }.onEach {
                    it.image = viewModel.infoImageList.random()
                }
            ) {
                startContentActivity(it)
            }
        }
    }.root


    private fun initSetting(activity: MainActivity): View = LayoutSettingBinding.inflate(LayoutInflater.from(activity)).apply {
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