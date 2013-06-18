package com.wsdcamp.wsdandroid.display;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.jakewharton.disklrucache.DiskLruCache;
import com.wsdcamp.wsdandroid.BuildConfig;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Created by normanpaniagua on 6/18/13.
 */
public class Image {
    private CompressFormat mCompressFormat = CompressFormat.JPEG;
    private int mCompressQuality = 70;
    private static final int IO_BUFFER_SIZE = 8 * 1024;
    private DiskLruCache mDiskLruCache;
    private final Object mDiskCacheLock = new Object();
    private boolean mDiskCacheStarting = true;
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
    private static final String DISK_CACHE_SUBDIR = "images";

    private class InitDiskCacheTask extends AsyncTask<File, Void, Void> {
        @Override
        protected Void doInBackground(File... params) {
            synchronized (mDiskCacheLock) {
                File cacheDir = params[0];
                try {
                    mDiskLruCache = DiskLruCache.open(cacheDir, 1, 2, DISK_CACHE_SIZE);
                    mDiskCacheStarting = false; // Finished initialization
                    mDiskCacheLock.notifyAll(); // Wake any waiting threads
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView mImageView;
        private String mUrl;

        public BitmapWorkerTask(ImageView imageView) {
            this.mImageView = imageView;
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(String... params) {
            this.mUrl = String.valueOf(params[0]);
            this.mImageView.setTag(this.mUrl);

            // Check disk cache in background thread
            Bitmap bitmap = getBitmapFromDiskCache(this.mUrl);

            if (bitmap == null) { // Not found in disk cache
                try {
                    URL url = new URL(this.mUrl);
                    bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                    // Add final bitmap to caches
                    addBitmapToCache(this.mUrl, bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return bitmap;
        }

        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap == null || !this.mImageView.getTag().equals(this.mUrl)) return;

            this.mImageView.setImageBitmap(bitmap);
        }
    }

    public Image(Context context) {
        File cacheDir = getDiskCacheDir(context);
        new InitDiskCacheTask().execute(cacheDir);
    }

    public File getDiskCacheDir(Context context) {
        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
        // otherwise use internal cache dir
        final String cachePath = context.getCacheDir().getPath();

        return new File(cachePath + File.separator + DISK_CACHE_SUBDIR);
    }

    public Bitmap getBitmapFromDiskCache(String key) {
        synchronized (mDiskCacheLock) {
            // Wait while disk cache is started from background thread
            try {
                while (mDiskCacheStarting) {
                        mDiskCacheLock.wait();
                }
                if (mDiskLruCache != null) {
                    return getBitmap(key);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void addBitmapToCache(final String key, Bitmap bitmap) {
        DiskLruCache.Editor editor = null;
        try {
            editor = mDiskLruCache.edit( key );
            if ( editor == null ) {
                return;
            }

            if( writeBitmapToFile( bitmap, editor ) ) {
                mDiskLruCache.flush();
                editor.commit();
                if ( BuildConfig.DEBUG ) {
                    Log.d( "cache_test_DISK_", "image put on disk cache " + key );
                }
            } else {
                editor.abort();
                if ( BuildConfig.DEBUG ) {
                    Log.d( "cache_test_DISK_", "ERROR on: image put on disk cache " + key );
                }
            }
        } catch (IOException e) {
            if ( BuildConfig.DEBUG ) {
                Log.d( "cache_test_DISK_", "ERROR on: image put on disk cache " + key );
            }
            try {
                if ( editor != null ) {
                    editor.abort();
                }
            } catch (IOException ignored) {
            }
        }
    }

    private boolean writeBitmapToFile( Bitmap bitmap, DiskLruCache.Editor editor )
            throws IOException, FileNotFoundException {
        OutputStream out = null;
        try {
            out = new BufferedOutputStream( editor.newOutputStream( 0 ), IO_BUFFER_SIZE );
            return bitmap.compress(mCompressFormat, mCompressQuality, out );
        } finally {
            if ( out != null ) {
                out.close();
            }
        }
    }

    public Bitmap getBitmap( String key ) {

        Bitmap bitmap = null;
        DiskLruCache.Snapshot snapshot = null;
        try {

            snapshot = mDiskLruCache.get( key );
            if ( snapshot == null ) {
                return null;
            }
            final InputStream in = snapshot.getInputStream( 0 );
            if ( in != null ) {
                final BufferedInputStream buffIn =
                        new BufferedInputStream( in, IO_BUFFER_SIZE );
                bitmap = BitmapFactory.decodeStream( buffIn );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( snapshot != null ) {
                snapshot.close();
            }
        }

        if ( BuildConfig.DEBUG ) {
            Log.d("cache_test_DISK_", bitmap == null ? "" : "image read from disk " + key);
        }

        return bitmap;

    }

    public void loadFromUrl(final String url, ImageView imageView) {
        final Bitmap bitmap = getBitmapFromDiskCache(url);

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            task.execute(url);
        }
    }
}