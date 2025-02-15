package cz.jankotas.bakalarka.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import cz.jankotas.bakalarka.common.Common
import cz.jankotas.bakalarka.models.Report
import cz.jankotas.bakalarka.services.reportFetcher.factories.ReportOwnDataSourceFactory

/**
 * Třída, která slouží jako ViewModel v architektuře MVVM. Cílem třídy je držet data seznamu podnětů pro RecyclerView
 * a zasílat observerům upozornění na změnu svých dat.
 */
class ReportOwnViewModel : ViewModel() {

    internal var itemPagedList: LiveData<PagedList<Report>>
    private var liveDataSource: LiveData<PageKeyedDataSource<Int, Report>>

    init {

        val itemDataSourceFactory = ReportOwnDataSourceFactory()
        liveDataSource = itemDataSourceFactory.reportLiveDataSource

        val config = PagedList.Config.Builder().setEnablePlaceholders(false).setPageSize(Common.PAGE_SIZE).build()

        itemPagedList = LivePagedListBuilder(itemDataSourceFactory, config).build()

    }
}