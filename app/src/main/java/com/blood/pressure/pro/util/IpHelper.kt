package com.blood.pressure.pro.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import com.blood.pressure.pro.application
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException
import java.util.Collections


object IpHelper {
    const val net: String = "net"
    const val wifi: String = "wifi"
    val conMann by lazy {
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    fun getNetType(): String? {
        val mobileNetworkInfo = conMann.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val wifiNetworkInfo = conMann.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (mobileNetworkInfo!!.isConnected) {
            return net
        } else if (wifiNetworkInfo!!.isConnected) {
            return wifi
        }
        return ""
    }

    fun getIpAddress(): String? {
        return when (getNetType()) {
            net -> getLocalIpAddress()
            wifi -> getWifiAddress()
            else -> ""
        }
    }

    private fun getLocalIpAddress(): String? {
        try {
            val netList = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (ni in netList) {
                val ipList = Collections.list(ni.inetAddresses)
                for (address in ipList) {
                    if (!address.isLoopbackAddress && address is Inet4Address) {
                        return address.getHostAddress()
                    }
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
        }
        return ""
    }

    private fun getWifiAddress(): String? {
        return intToIp((application.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager).connectionInfo.ipAddress)
    }

    private fun intToIp(ipInt: Int): String? {
        return (ipInt and 0xFF).toString() + "." +
                (ipInt shr 8 and 0xFF) + "." +
                (ipInt shr 16 and 0xFF) + "." +
                (ipInt shr 24 and 0xFF)
    }


}