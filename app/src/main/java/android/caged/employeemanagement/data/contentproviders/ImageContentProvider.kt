package android.caged.employeemanagement.data.contentproviders

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.os.ParcelFileDescriptor
import java.io.File
import java.io.FileNotFoundException

class ImageContentProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "android.caged.contentproviders.ImageContentProvider"
        const val IMAGE_PATH = "images"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$IMAGE_PATH")

        private const val IMAGES = 1
        private const val IMAGE_ID = 2

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, IMAGE_PATH, IMAGES)
            addURI(AUTHORITY, "$IMAGE_PATH/#", IMAGE_ID)
        }
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        // get the image i selected
        throw UnsupportedOperationException("Not implemented")
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            IMAGES -> "vnd.android.cursor.dir/$IMAGE_PATH"
            IMAGE_ID -> "vnd.android.cursor.item/$IMAGE_PATH"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException("Not implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException("Not implemented")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException("Not implemented")
    }

    override fun openFile(uri: Uri, mode: String): ParcelFileDescriptor? {
        val file = getFileForUri(uri)
        return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
    }

    private fun getFileForUri(uri: Uri): File {
        val context = context ?: throw IllegalStateException("Context is null")
        val id = ContentUris.parseId(uri)
        val file = File(context.filesDir, "$IMAGE_PATH/$id")
        if (!file.exists()) {
            throw FileNotFoundException("File not found: $uri")
        }
        return file
    }
}