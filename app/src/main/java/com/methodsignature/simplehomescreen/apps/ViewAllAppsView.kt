package com.methodsignature.simplehomescreen.apps

import android.content.Context
import android.content.pm.PackageManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.methodsignature.simplehomescreen.R
import com.methodsignature.simplehomescreen.android.view.EvenPaddingItemDecoration
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

interface ViewAllAppsView {
    fun display(): Consumer<List<AppViewModel>>
    fun onAppClickedId(): Observable<AppViewModel>
    fun onAppLongClickedId(): Observable<AppViewModel>
}

class LinearViewAllAppsView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet?,
    defStyleAttribute: Int = 0
): ViewAllAppsView, RecyclerView(context, attributeSet, defStyleAttribute) {

    private val clickListener: Subject<AppViewModel> = PublishSubject.create()
    private val longClickListener: Subject<AppViewModel> = PublishSubject.create()

    init {
        setHasFixedSize(true)
        adapter = ListAdapter()
        layoutManager = LinearLayoutManager(context)
        addItemDecoration(EvenPaddingItemDecoration(resources.getDimension(R.dimen.standard_list_item_margin)))
    }

    override fun display(): Consumer<List<AppViewModel>> {
        return Consumer {
            (adapter as ListAdapter).update(it)
        }
    }

    override fun onAppClickedId(): Observable<AppViewModel> {
        return clickListener
    }

    override fun onAppLongClickedId(): Observable<AppViewModel> {
        return longClickListener
    }

    private inner class ListAdapter: RecyclerView.Adapter<ViewHolder>() {

        private val mList: MutableList<AppViewModel> = mutableListOf()

        fun update(viewModels: List<AppViewModel>) {
            this.mList.clear()
            this.mList.addAll(viewModels)
            notifyDataSetChanged()
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            val vh = requireNotNull(holder)

            val model = mList[position]
            vh.title.text = model.readableName
            vh.itemView.setOnClickListener { clickListener.onNext(model) }
            vh.itemView.setOnLongClickListener {
                longClickListener.onNext(model)
                true
            }

            vh.loadDrawableRunnable?.apply {
                handler.removeCallbacks(vh.loadDrawableRunnable)
            }

            vh.loadDrawableRunnable = Runnable {
                try {
                    Glide.with(context)
                        .load(context.packageManager.getActivityIcon(model.componentName))
                        .into(vh.icon)
                } catch (e: PackageManager.NameNotFoundException) {
                    Log.e(
                        ViewAllAppsView::class.java.simpleName,
                        "Unable to find activity ${model.componentName.className} for package ${model.componentName.packageName}",
                        e
                    )
                }
            }
            handler.post(vh.loadDrawableRunnable)
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.linear_view_all_apps_view_list_item, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return mList.size
        }
    }

    private class ViewHolder(rootView: View): RecyclerView.ViewHolder(rootView) {
        var loadDrawableRunnable: Runnable? = null
        val icon: ImageView = rootView.findViewById(R.id.linear_view_all_apps_view_list_item_icon) as ImageView
        val title: TextView = rootView.findViewById(R.id.linear_view_all_apps_view_list_item_title) as TextView
    }
}