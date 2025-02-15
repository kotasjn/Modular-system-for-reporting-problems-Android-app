package cz.jankotas.bakalarka.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nguyenhoanglam.imagepicker.model.Image
import cz.jankotas.bakalarka.common.Common
import kotlinx.android.synthetic.main.activity_report_get_photos.view.*
import kotlinx.android.synthetic.main.photo_image_view.view.*
import android.R
import android.app.Activity
import android.widget.Button
import android.widget.TextView

/**
 * Adaptér pro zobrazení položek vybraných obrázků v aktivitě ReportGetPhotosActivity
 */
class PhotoGridAdapter(private var context: Context, private val view: View, private var images: List<Image>) :
    RecyclerView.Adapter<PhotoGridAdapter.ImageViewHolder>() {

    // získta počet vybraných fotografií
    override fun getItemCount(): Int {
        return images.size
    }

    // předání ViewHolderu obsahujícího layout položky fotografie
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(LayoutInflater.from(context).inflate(cz.jankotas.bakalarka.R.layout.photo_image_view, parent, false))
    }

    // naplnění položky layoutu daty fotografie
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = images[position] // získání objektu fotografie

        // načtení obrázku z paměti telefonu pomocí knihovny Glide
        Glide.with(context)
            .load(image.path)
            .centerCrop()
            .apply(RequestOptions().placeholder(cz.jankotas.bakalarka.R.drawable.image_placeholder).error(cz.jankotas.bakalarka.R.drawable.image_placeholder)) // umístění placeholderu pro případ delšího načítání/či nenačtení obrázku
            .placeholder(cz.jankotas.bakalarka.R.drawable.photo_placeholder)
            .into(holder.reportPhotoLayout.report_photo_iv)

        // nastavení ikony pro odstranění fotografie z výběru
        holder.reportPhotoLayout.delete_photo_iv.setOnClickListener {
            Common.newReport.photos.remove(image) // odstranění objektu fotografie ze seznamu
            this.images = Common.newReport.photos // vložení nového seznamu namísto stávajícího
            if (images.isEmpty()) view.visibility = View.GONE // pokud seznam neobsahuje už žádné obrázky, skrýt view
            notifyDataSetChanged() // je nutné upozornit recyclerView, že se data změnily, aby se mohl znovu vykreslit
        }
    }

    // přepsání seznamu fotografií
    fun setData(images: ArrayList<Image>?) {
        if (images != null) this.images = images
        notifyDataSetChanged()
    }

    // třída ViewHolderu, která obsahuje položky layoutu, které je možné upravovat/naplnit daty
    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val reportPhotoLayout: View = view.report_photo_layout
    }
}