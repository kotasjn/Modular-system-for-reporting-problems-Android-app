package cz.jankotas.bakalarka.services.reportFetcher

import android.util.Log
import androidx.annotation.NonNull
import androidx.paging.PageKeyedDataSource
import cz.jankotas.bakalarka.common.Common
import cz.jankotas.bakalarka.models.APIReportResponse
import cz.jankotas.bakalarka.models.Report
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportOwnDataSource : PageKeyedDataSource<Int, Report>() {

    private val FIRST_PAGE = 0

    override fun loadInitial(@NonNull params: LoadInitialParams<Int>, @NonNull callback: LoadInitialCallback<Int, Report>) {

        if (Common.userID != null) {
            Common.api.getReports(Common.token, Common.location, FIRST_PAGE, Common.userID, null).enqueue(object :
                Callback<APIReportResponse> {
                override fun onResponse(call: Call<APIReportResponse>, response: Response<APIReportResponse>) {

                    if (response.body() != null) {

                        callback.onResult(response.body()!!.reports, null, FIRST_PAGE + 1)

                        Log.d(Common.APP_NAME, "User's reports data: " + response.body().toString())
                    }
                }

                override fun onFailure(call: Call<APIReportResponse>, t: Throwable) {
                    Log.d(Common.APP_NAME, "Failure during getting reports data.")
                    t.printStackTrace()
                }
            })
        }
    }

    override fun loadBefore(@NonNull params: LoadParams<Int>, @NonNull callback: LoadCallback<Int, Report>) {

        if (Common.userID != null) {
            Common.api.getReports(Common.token, Common.location, params.key, Common.userID, null).enqueue(object :
                Callback<APIReportResponse> {
                override fun onResponse(call: Call<APIReportResponse>, response: Response<APIReportResponse>) {


                    if (response.body() != null) {
                        val key = params.key + 1
                        callback.onResult(response.body()!!.reports, key)

                        Log.d(Common.APP_NAME, "User's reports data: " + response.body().toString())
                    }
                }

                override fun onFailure(call: Call<APIReportResponse>, t: Throwable) {
                    Log.d(Common.APP_NAME, "Failure during getting reports data.")
                    t.printStackTrace()
                }
            })
        }
    }

    override fun loadAfter(@NonNull params: LoadParams<Int>, @NonNull callback: LoadCallback<Int, Report>) {

        if (Common.userID != null) {
            Common.api.getReports(Common.token, Common.location, params.key, Common.userID, null).enqueue(object :
                Callback<APIReportResponse> {
                override fun onResponse(call: Call<APIReportResponse>, response: Response<APIReportResponse>) {

                    if (response.body() != null) {
                        val key = params.key + 1
                        callback.onResult(response.body()!!.reports, key)

                        Log.d(Common.APP_NAME, "User's reports data: " + response.body().toString())
                    }
                }

                override fun onFailure(call: Call<APIReportResponse>, t: Throwable) {
                    Log.d(Common.APP_NAME,"Failure during getting reports data.")
                    t.printStackTrace()
                }
            })
        }
    }
}