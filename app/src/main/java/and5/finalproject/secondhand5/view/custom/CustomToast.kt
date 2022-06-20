package and5.finalproject.secondhand5.view.custom

import and5.finalproject.secondhand5.R
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlin.properties.Delegates

class CustomToast {


    fun failureToast(context: Context?, msg: String?) {
            val inflater = LayoutInflater.from(context)
            val layout: View = inflater.inflate(R.layout.error_toast, null)
            val text = layout.findViewById<View>(R.id.errortext) as? TextView
            text?.text = msg
            text?.setPadding(20, 0, 20, 0)
            text?.textSize = 22f

            val toast = Toast(context)
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 400)
            toast.duration = Toast.LENGTH_SHORT

            toast.view = layout
            toast.show()
    }

    fun successToast(context: Context?, msg: String?) {
        val inflater = LayoutInflater.from(context)
        val layout: View = inflater.inflate(R.layout.success_toast, null)
        val text = layout.findViewById<View>(R.id.successtext) as? TextView
        text?.text = msg
        text?.setPadding(20, 0, 20, 0)
        text?.textSize = 22f
        text?.setTextColor(Color.WHITE)
        val toast = Toast(context)
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()

    }
}