package cz.jankotas.bakalarka.models

import android.os.Parcelable
import androidx.annotation.NonNull
import kotlinx.android.parcel.Parcelize

/**
 * Datová třída pro informace o modulech získaných od serveru
 */
@Parcelize
data class Module constructor(@NonNull var id: Int,
                              @NonNull var name: String,
                              @NonNull var inputs: ArrayList<Input>) : Parcelable