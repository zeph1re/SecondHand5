package and5.finalproject.secondhand5.Room.Model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class GetProductHome (
    @ColumnInfo(name ="base_price")
    val basePrice: Int?,
    @ColumnInfo(name ="Categories")
    val categories: String?,
    @ColumnInfo(name ="createdAt")
    val createdAt: String?,
    @ColumnInfo(name ="description")
    val description: String?,
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    @ColumnInfo(name ="image_name")
    val imageName: String?,
    @ColumnInfo(name ="image_url")
    val imageUrl: String?,
    @ColumnInfo(name ="location")
    val location: String?,
    @ColumnInfo(name ="name")
    val name: String?,
    @ColumnInfo(name ="status")
    val status: String?,
    @ColumnInfo(name ="updatedAt")
    val updatedAt: String?,
//    @ColumnInfo(name ="User")
//    val user: @RawValue User?,
//    @ColumnInfo(name ="user_id")
//    val userId: Int?
    ):Parcelable

