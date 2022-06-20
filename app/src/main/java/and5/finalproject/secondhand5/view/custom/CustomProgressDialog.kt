package and5.finalproject.secondhand5.view.custom

import and5.finalproject.secondhand5.R
import android.app.Dialog

import android.content.Context

import android.os.Handler
import android.os.Looper

import android.view.Window


class CustomProgressDialog {
    lateinit var dialog: Dialog

    fun showProgressDialog(context: Context?){
        dialog = Dialog(context!!)
        dialog.window?.setBackgroundDrawableResource(R.color.blue_300);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    fun dismissProgressDialog(context: Context?){
        Handler(Looper.getMainLooper()).postDelayed({
            dialog.dismiss()
        },2000)
    }

}