package cz.jankotas.bakalarka

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nguyenhoanglam.imagepicker.model.Config.EXTRA_IMAGES
import com.nguyenhoanglam.imagepicker.model.Config.RC_PICK_IMAGES
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker
import cz.jankotas.bakalarka.adapters.PhotoGridAdapter
import cz.jankotas.bakalarka.common.Common
import kotlinx.android.synthetic.main.activity_report_get_photos.*
import kotlinx.android.synthetic.main.photos_recycler_view.*

class ReportGetPhotosActivity : AppCompatActivity() {

    private lateinit var adapter: PhotoGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_get_photos)

        adapter = PhotoGridAdapter(this, bottom_banner, Common.newReport.photos)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close_white)

        btn_continue.setOnClickListener {
            val intent = Intent(this, ReportGetLocationActivity::class.java)
            startActivity(intent)
        }

        photo_fab.setOnClickListener {
            getImages()
        }

        if (Common.newReport.photos.isNotEmpty()) {
            adapter.setData(Common.newReport.photos)
            photos_recycler_view.adapter = adapter
        }

        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        photos_recycler_view.layoutManager = layoutManager

        getImages()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu. This adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_report_bug, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /* Handle action bar item clicks here. The action bar will
         * automatically handle clicks on the Home/Up button, so long
         * as you specify a parent activity in AndroidManifest.xml.*/
        return when (item.itemId) {
            R.id.action_report_bug -> {
                startActivity(Intent(this, ReportBugActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        showDialog()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_PICK_IMAGES && resultCode == Activity.RESULT_OK && data != null) {
            Common.newReport.photos = data.getParcelableArrayListExtra(EXTRA_IMAGES)
            adapter.setData(Common.newReport.photos)
            if (Common.newReport.photos.isNotEmpty()) bottom_banner.visibility = View.VISIBLE else bottom_banner.visibility = View.GONE
            photos_recycler_view.adapter = adapter
        }

        super.onActivityResult(requestCode, resultCode, data)  // You MUST have this line to be here
        // so ImagePicker can work with fragment mode
    }

    private fun getImages() {
        ImagePicker.with(this)                         //  Initialize ImagePicker with activity or fragment context
            .setToolbarColor(getColorAsString(R.color.colorPrimary))         //  Toolbar color
            .setStatusBarColor(getColorAsString(R.color.colorPrimaryDark))       //  StatusBar color (works with SDK >= 21  )
            .setToolbarTextColor(getColorAsString(R.color.colorWhitePrimary))     //  Toolbar text color (Title and Done button)
            .setToolbarIconColor(getColorAsString(R.color.colorWhitePrimary))     //  Toolbar icon color (Back and Camera button)
            .setProgressBarColor(getColorAsString(R.color.colorAccent))     //  ProgressBar color
            .setBackgroundColor(getColorAsString(R.color.colorWhitePrimary))      //  Background color
            .setCameraOnly(false)               //  Camera mode
            .setMultipleMode(true)              //  Select multiple images or single image
            .setFolderMode(true)                //  Folder mode
            .setShowCamera(true)                //  Show camera button
            .setFolderTitle(getString(R.string.albums))           //  Folder title (works with FolderMode = true)
            .setImageTitle("Galleries")         //  Image title (works with FolderMode = false)
            .setDoneTitle(getString(R.string.done))               //  Done button title
            .setLimitMessage(getString(R.string.selection_limit_reached))    // Selection limit message
            .setMaxSize(9)                     //  Max images can be selected
            .setSavePath(Common.APP_NAME)         //  Image capture folder name
            .setSelectedImages(Common.newReport.photos)          //  Selected images
            .setAlwaysShowDoneButton(true)      //  Set always show done button in multiple mode
            .setRequestCode(100)                //  Set request code, default Config.RC_PICK_IMAGES
            .setKeepScreenOn(true)              //  Keep screen on when selecting images
            .start()                            //  Start ImagePicker
    }

    private fun getColorAsString(resource: Int): String {
        return "#" + Integer.toHexString(ContextCompat.getColor(this, resource))
    }

    private fun showDialog() {
        val builder1 = AlertDialog.Builder(this)
        builder1.setMessage(getString(R.string.warning_closing_report))
        builder1.setCancelable(true)

        builder1.setPositiveButton("OK") { dialog, _ ->
            run {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                Common.newReport.clearData()
                dialog.cancel()
            }
        }

        builder1.setNegativeButton("Cancel") { dialog, _ ->
            run {
                dialog.cancel()
            }
        }

        val alert11 = builder1.create()
        alert11.show()
    }
}
