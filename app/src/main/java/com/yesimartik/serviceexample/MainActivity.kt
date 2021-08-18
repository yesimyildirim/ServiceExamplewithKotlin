package com.yesimartik.serviceexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.app.ActivityManager
import android.content.Context
import android.widget.Toast
import android.content.Intent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Servis sınıfı adını tutacak deişkeni tanımlıyoruz
        val serviceClass = RandomNumberService::class.java

        // servis Sınıfı için yeni bir intent başlatıyoruz
        val intent = Intent(applicationContext, serviceClass)


        // Servisi başlatacak butonun tıklanma olayı
        button_start.setOnClickListener{
            // Servis çalışmıyorsa başlatma koşulu
            if (!isServiceRunning(serviceClass)) {
                // Servisi başlatma
                startService(intent)
            } else {
                toast("Service already running.")
            }
        }


        // Servisi durduracak butonun tıklanma olayı
        button_stop.setOnClickListener{
            // Servis çalışmıyorsa başlatma koşulu
            if (isServiceRunning(serviceClass)) {
                // Servisi durdurma
                stopService(intent)
            } else {
                toast("Service already stopped.")
            }
        }


        // servis durumunu belirleme butonu tıklama olayı
        button_stats.setOnClickListener{
            if (isServiceRunning(serviceClass)) {
                toast("Service is running.")
            } else {
                toast("Service is stopped.")
            }

        }
    }


    // Servisin çalışıp çalışmadığını belirlemek için özel method
    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        // Çalışan servisler arasında döngü
        for (service in activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                // Hizmet çalışıyorsa, true değerini döndürün
                return true
            }
        }
        return false
    }
}



// Toast mesajını göstermek için fonksiyon
fun Context.toast(message:String){
    Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
}