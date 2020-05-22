package com.qyd.mydailyreport.widget.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.linqinen.library.utils.LogT
import com.qyd.mydailyreport.R

open class AppLoadingDialog(var mContext: Context, theme: Int = R.style.dialog_app_loading) : Dialog(mContext, theme) {
    constructor(context: Context) : this(context,R.style.dialog_app_loading) {

    }

//    private var message: String? = null

//    var isCancelable2: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = LayoutInflater.from(mContext).inflate(R.layout.dialog_app_loading, null, false)
        setContentView(inflate)

//        setCancelable(mCanCancle)
        setCanceledOnTouchOutside(false)
    }

//    override fun onStart() {
////        LogT.i("内容："+tv_message?.text +",message:"+message)
//        if (!TextUtils.isEmpty(message)) {
//            tv_message.text = message
//        }
//        super.onStart()
//    }

    /**Activity调用了finish后，其isFinishing()会被立即置为true
    在Dialog.show()之前，务必检测context所属的activity的isFinishing是否为true*/
    override fun show() {

        if (mContext is Activity && (mContext as Activity).isFinishing) {
            LogT.e("activity都finish了，还show毛")
            return
        }
        super.show()
    }

    override fun dismiss() {
        if (mContext is Activity && (mContext as Activity).isFinishing) {
            LogT.e("activity都finish了，还dismiss毛")
            return
        }
        super.dismiss()
    }
}