package com.lost.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Media and camera compat method.
 *
 * @author lcf
 * @date 2018/5/31 14:07
 * @since 1.0
 */
public class MediaStoreCompat {
    private final WeakReference<Activity> mContext;
    private final WeakReference<Fragment> mFragment;
    private String mAuthority;
    private Uri mCurrentPhotoUri;

    public MediaStoreCompat(Activity context) {
        mContext = new WeakReference<>(context);
        mFragment = null;
    }

    public MediaStoreCompat(Activity context, Fragment fragment) {
        mContext = new WeakReference<>(context);
        mFragment = new WeakReference<>(fragment);
    }

    /**
     * Checks whether the device has a camera feature or not.
     *
     * @param context a context to check for camera feature.
     * @return true if the device has a camera feature. false otherwise.
     */
    public static boolean hasCameraFeature(Context context) {
        PackageManager pm = context.getApplicationContext().getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /**
     * Set FileProvider Authority.
     *
     * @param authority provider authority
     */
    public void setAuthority(String authority) {
        mAuthority = authority;
    }

    /**
     * open Camera
     *
     * @param context     a context to start camera.
     * @param requestCode request code
     */
    public void dispatchIntentCapture(Context context, int requestCode) {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (captureIntent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (TextUtils.isEmpty(mAuthority)) {
                        throw new IllegalArgumentException("FileProvider need set authority!");
                    }
                    mCurrentPhotoUri = FileProvider.getUriForFile(context, mAuthority, photoFile);
                } else {
                    mCurrentPhotoUri = Uri.fromFile(photoFile);
                }
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCurrentPhotoUri);
                if (mFragment != null) {
                    mFragment.get().startActivityForResult(captureIntent, requestCode);
                } else {
                    mContext.get().startActivityForResult(captureIntent, requestCode);
                }
            }
        }
    }

    /**
     * Take photo saved file.
     *
     * @return saved file
     * @throws IOException IOException
     */
    private File createImageFile() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String imageFileName = "out_" + timestamp + ".jpg";
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(dir + imageFileName);
    }

    /**
     * The uri of current photo.
     *
     * @return uri
     */
    public Uri getCurrentPhotoUri() {
        return mCurrentPhotoUri;
    }

    /**
     * Install apk
     *
     * @param context   a context to install
     * @param authority fileprovider authority
     * @param file      apk file
     */
    public static void installApk(Context context, String authority, File file) {
        try {
            Uri uri;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(context, authority, file);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                uri = Uri.fromFile(file);
            }
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            if (context instanceof Application) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MediaStoreCompat", "install apk failed,delete file.");
            file.deleteOnExit();
        }
    }

    /**
     * Get the absolute path of image from uri.
     *
     * @param context activity or application
     * @param uri     the image's uri
     * @return absolute path
     */
    public static String getPathFromUri(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return getPathCompatApi19(context, uri);
        } else {
            return getPathCompat(context, uri);
        }
    }

    /**
     * Get the absolute path of image from uri.
     * Build version below 19.
     *
     * @param context activity or application
     * @param uri     the image's uri
     * @return absolute path
     */
    private static String getPathCompat(Context context, Uri uri) {
        return getDataColumn(context, uri, null, null);
    }

    /**
     * Get the absolute path of image from uri.
     * Build version above 19(include 19).
     *
     * @param context activity or application
     * @param uri     the image's uri
     * @return absolute path
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static String getPathCompatApi19(Context context, Uri uri) {
        String path = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // is Document Uri,get document id
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaDocument(uri)) {
                // MediaProvider
                String id = documentId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = {id};
                path = getDataColumn(context, uri, selection, selectionArgs);
            } else if (isDownloadsDocument(uri)) {
                // DownloadsProvider
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                path = getDataColumn(context, contentUri, null, null);
            }
        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(uri.getScheme())) {
            // is Content Uri
            path = getDataColumn(context, uri, null, null);
        } else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(uri.getScheme())) {
            // is File Uri
            path = uri.getPath();
        }
        return path;
    }

    /**
     * Query the given URI from media database.
     *
     * @param context       activity or application
     * @param uri           The URI, using the content:// scheme, for the content to
     *                      retrieve.
     * @param selection     A filter declaring which rows to return, formatted as an
     *                      SQL WHERE clause (excluding the WHERE itself). Passing null will
     *                      return all rows for the given URI.
     * @param selectionArgs You may include ?s in selection, which will be
     *                      replaced by the values from selectionArgs, in the order that they
     *                      appear in the selection. The values will be bound as Strings.
     * @return absolute path
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String path = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    /**
     * Whether the Uri authority is MediaProvider
     *
     * @param uri media uri
     * @return true or false
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * Whether the Uri authority is DownloadsProvider.
     *
     * @param uri media uri
     * @return true or false
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
}
