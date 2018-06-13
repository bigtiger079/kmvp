package com.bigtiger.kmvp.utils

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.text.SpannedString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.SpannableString
import android.view.View
import android.widget.TextView
import com.bigtiger.kmvp.base.App
import com.bigtiger.kmvp.di.component.AppComponent




object ArmsUtils {
    /**
     * 设置hint大小
     *
     * @param size
     * @param v
     * @param res
     */
    fun setViewHintSize(context: Context, size: Int, v: TextView, res: Int) {
        val ss = SpannableString(getResources(context).getString(res))
        // 新建一个属性对象,设置文字的大小
        val ass = AbsoluteSizeSpan(size, true)
        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // 设置hint
        v.hint = SpannedString(ss) // 一定要进行转换,否则属性会消失
    }


    /**
     * dip转pix
     *
     * @param dpValue
     * @return
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = getResources(context).getDisplayMetrics().density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 获得资源
     */
    fun getResources(context: Context): Resources {
        return context.resources
    }

    /**
     * 得到字符数组
     */
    fun getStringArray(context: Context, id: Int): Array<String> {
        return getResources(context).getStringArray(id)
    }

    /**
     * pix转dip
     */
    fun pix2dip(context: Context, pix: Int): Int {
        val densityDpi = getResources(context).getDisplayMetrics().density
        return (pix / densityDpi + 0.5f).toInt()
    }


    /**
     * 从 dimens 中获得尺寸
     *
     * @param context
     * @param id
     * @return
     */
    fun getDimens(context: Context, id: Int): Int {
        return getResources(context).getDimension(id).toInt()
    }

    /**
     * 从 dimens 中获得尺寸
     *
     * @param context
     * @param dimenName
     * @return
     */
    fun getDimens(context: Context, dimenName: String): Float {
        return getResources(context).getDimension(getResources(context).getIdentifier(dimenName, "dimen", context.getPackageName()))
    }

    /**
     * 从String 中获得字符
     *
     * @return
     */

    fun getString(context: Context, stringID: Int): String {
        return getResources(context).getString(stringID)
    }

    /**
     * 从String 中获得字符
     *
     * @return
     */

    fun getString(context: Context, strName: String): String {
        return getString(context, getResources(context).getIdentifier(strName, "string", context.getPackageName()))
    }

    /**
     * findview
     *
     * @param view
     * @param viewName
     * @param <T>
     * @return
    </T> */
    fun <T : View> findViewByName(context: Context, view: View, viewName: String): T {
        val id = getResources(context).getIdentifier(viewName, "id", context.getPackageName())
        return view.findViewById(id)
    }

    /**
     * 根据 layout 名字获得 id
     *
     * @param layoutName
     * @return
     */
    fun findLayout(context: Context, layoutName: String): Int {
        return getResources(context).getIdentifier(layoutName, "layout", context.getPackageName())
    }

    /**
     * 填充view
     *
     * @param detailScreen
     * @return
     */
    fun inflate(context: Context, detailScreen: Int): View {
        return View.inflate(context, detailScreen, null)
    }


    /**
     * 通过资源id获得drawable
     *
     * @param rID
     * @return
     */
    fun getDrawablebyResource(context: Context, rID: Int): Drawable {
        return getResources(context).getDrawable(rID)
    }



    /**
     * 跳转界面 3
     *
     * @param activity
     * @param homeActivityClass
     */
    fun startActivity(activity: Activity, homeActivityClass: Class<*>) {
        val intent = Intent(activity.applicationContext, homeActivityClass)
        activity.startActivity(intent)
    }

    /**
     * 跳转界面 4
     *
     * @param
     */
    fun startActivity(activity: Activity, intent: Intent) {
        activity.startActivity(intent)
    }

    /**
     * 获得屏幕的宽度
     *
     * @return
     */
    fun getScreenWidth(context: Context): Int {
        return getResources(context).getDisplayMetrics().widthPixels
    }

    /**
     * 获得屏幕的高度
     *
     * @return
     */
    fun getScreenHeidth(context: Context): Int {
        return getResources(context).getDisplayMetrics().heightPixels
    }


    /**
     * 获得颜色
     */
    fun getColor(context: Context, rid: Int): Int {
        return getResources(context).getColor(rid)
    }

    /**
     * 获得颜色
     */
    fun getColor(context: Context, colorName: String): Int {
        return getColor(context, getResources(context).getIdentifier(colorName, "color", context.getPackageName()))
    }

    /**
     * 移除孩子
     *
     * @param view
     */
    fun removeChild(view: View) {
        val parent = view.getParent()
        if (parent is ViewGroup) {
            val group = parent as ViewGroup
            group.removeView(view)
        }
    }

    fun isEmpty(obj: Any?): Boolean {
        return if (obj == null) {
            true
        } else false
    }

    fun obtainAppComponentFromContext(context: Context): AppComponent {
        Preconditions.checkNotNull(context, "%s cannot be null", Context::class.java.name)
        Preconditions.checkState(context.applicationContext is App, "Application does not implements App")
        return (context.applicationContext as App).getAppComponent()
    }
}