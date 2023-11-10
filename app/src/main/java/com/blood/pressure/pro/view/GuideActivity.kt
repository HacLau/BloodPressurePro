package com.blood.pressure.pro.view

import android.text.Html
import android.view.View
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.blood.pressure.pro.BuildConfig
import com.blood.pressure.pro.R
import com.blood.pressure.pro.adapter.StepAdapter
import com.blood.pressure.pro.databinding.ActivityGuideBinding
import com.blood.pressure.pro.util.SharedHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class GuideActivity : BaseActivity<ActivityGuideBinding>(R.layout.activity_guide) {
    override fun initView() {
        binding.guide = viewModel.appDataEntity.guide
    }

    override fun initData() {
        if (SharedHelper.launchedStart)
            setVisible(1)
        else
            setVisible(0)
    }

    private fun setVisible(index: Int) {
        when (index) {
            0 -> {
                binding.guideStart.visibility = View.VISIBLE
                binding.guideSplash.visibility = View.GONE
                binding.guideStep.visibility = View.GONE
                setStart()
            }

            1 -> {
                binding.guideStart.visibility = View.GONE
                binding.guideSplash.visibility = View.VISIBLE
                binding.guideStep.visibility = View.GONE
                setSplash()
            }

            2 -> {
                binding.guideStart.visibility = View.GONE
                binding.guideSplash.visibility = View.GONE
                binding.guideStep.visibility = View.VISIBLE
                setStepView()
            }
        }
    }

    private fun setStart() {
        binding.btnStart.setOnClickListener {
            if (binding.rdStart.isChecked) {
                setVisible(1)
                SharedHelper.launchedStart = true
            } else
                viewModel.appDataEntity.guide.check.toast(this)
        }
        binding.guidePrivacy.apply {
            text = Html.fromHtml("<u>${viewModel.appDataEntity.guide.privacy}</u>")
            setOnClickListener {
                startWebViewActivity(BuildConfig.privacy, viewModel.appDataEntity.guide.privacy)
            }
        }
        binding.guideAgreement.apply {
            text = Html.fromHtml("<u>${viewModel.appDataEntity.guide.agreement}</u>")
            setOnClickListener {
                startWebViewActivity(BuildConfig.agreement, viewModel.appDataEntity.guide.agreement)
            }
        }

    }

    private fun setSplash() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                if (binding.guideProgress.progress >= 100 && isResume) {
                    cancel()
                    CoroutineScope(Dispatchers.Main).launch {
                        if (SharedHelper.launchedStep)
                            startMainActivity()
                        else
                            setVisible(2)
                    }
                } else
                    binding.guideProgress.progress++
            }

        }, 33, 33)
    }


    private fun setStepView() {
        binding.guideVp.adapter = StepAdapter(this, viewModel.appDataEntity.guide.guide_step.apply {
            for (i in indices) {
                when (i) {
                    0 -> this[i].image = R.mipmap.bg_guide_image1
                    1 -> this[i].image = R.mipmap.bg_guide_image2
                    2 -> this[i].image = R.mipmap.bg_guide_image3
                }
            }
        })
        binding.guideVp.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                binding.index = position
                when (position) {
                    0 -> binding.rb1.isChecked = true
                    1 -> binding.rb2.isChecked = true
                    2 -> binding.rb3.isChecked = true
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        binding.guideNext.setOnClickListener {
            when (binding.guideVp.currentItem) {
                0 -> binding.guideVp.currentItem = 1
                1 -> binding.guideVp.currentItem = 2
                2 -> toMainActivity()
            }
        }

        binding.stepSkip.setOnClickListener {
            toMainActivity()
        }

    }

    private fun toMainActivity() {
        startMainActivity()
        SharedHelper.launchedStep = true
    }

}