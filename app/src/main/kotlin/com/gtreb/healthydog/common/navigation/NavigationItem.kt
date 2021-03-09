package com.gtreb.healthydog.common.navigation

import android.os.Parcel
import android.os.Parcelable
import kotlin.reflect.KClass

/**
 * This object represent a navigation event's item. It specify what has open on the screen.
 *
 * @param moduleName name of the functional module. Should be full package of the corresponding module.
 * @param screenName identifier for this particular item. Most common case -> Fragment name.
 * @param hasBack indicate this view has a back button.
 * @param title name displayed on top of navigation next to back button.
 * @param creation A nano second time for comparison between two elements.
 */
data class NavigationItem(
    val moduleName: String,
    val screenName: String,
    val title: String = DEFAULT_TITLE,
    val hasBack: Boolean = DEFAULT_BACK,
    val creation: Long = System.currentTimeMillis()
) : Parcelable {

    companion object CREATOR : Parcelable.Creator<NavigationItem> {

        override fun createFromParcel(parcel: Parcel): NavigationItem {
            return NavigationItem(parcel)
        }

        override fun newArray(size: Int): Array<NavigationItem?> {
            return arrayOfNulls(size)
        }
        // endregion Parcel

        private const val DEFAULT_BACK = false
        private const val DEFAULT_TITLE = ""
        private const val KCLASS_EXCEPTION =
            "KClass argument should be a proper class or object, not an erased one."
    }

    private class KClassQualifiedNameNotFound : RuntimeException(KCLASS_EXCEPTION)

    /**
     * Constructor to use with a class object for module
     * example: NavigationItem(AccountsOverviewModule::class, "Detail ${intent.getString(id)}", "title", hasBack = true)
     *
     * @throws KClassQualifiedNameNotFound when KClass param is an erased type.
     */
    @Throws(KClassQualifiedNameNotFound::class)
    constructor(
        module: KClass<*>,
        screenName: String,
        title: String = DEFAULT_TITLE,
        hasBack: Boolean = DEFAULT_BACK
    ) : this(
        module.qualifiedName ?: throw KClassQualifiedNameNotFound(),
        screenName, title, hasBack
    )

    /**
     * Constructor to use with a class object for module and screenName
     * example: NavigationItem(DashBoardModule::class, DashboardFragment::class, "title", hasBack = true)
     *
     * @throws KClassQualifiedNameNotFound when KClass param is an erased type.
     */
    @Throws(KClassQualifiedNameNotFound::class)
    constructor(
        module: KClass<*>,
        screenName: KClass<*>,
        title: String = DEFAULT_TITLE,
        hasBack: Boolean = DEFAULT_BACK
    ) : this(
        module.qualifiedName ?: throw KClassQualifiedNameNotFound(),
        screenName.qualifiedName ?: throw KClassQualifiedNameNotFound(),
        title, hasBack
    )

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: throw IllegalStateException("not a valid NavigationItem parcel"),
        parcel.readString() ?: throw IllegalStateException("not a valid NavigationItem parcel"),
        parcel.readString() ?: DEFAULT_TITLE,
        parcel.readByte() != 0.toByte(),
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(moduleName)
        parcel.writeString(screenName)
        parcel.writeString(title)
        parcel.writeByte(if (hasBack) 1 else 0)
        parcel.writeLong(creation)
    }

    override fun describeContents(): Int {
        return 0
    }
}
