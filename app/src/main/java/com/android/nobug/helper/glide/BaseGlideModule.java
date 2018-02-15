package com.android.nobug.helper.glide;

import android.content.Context;

import com.android.nobug.helper.okhttp.OkHttpUrlLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.module.GlideModule;

import java.io.File;
import java.io.InputStream;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

public class BaseGlideModule implements GlideModule {

    //  ========================================================================================

    private final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private final int DISK_CACHE_SIZE = 1024 * 1024 * 128;

    //  ========================================================================================

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "cache", DISK_CACHE_SIZE))
                .setBitmapPool(new LruBitmapPool(defaultBitmapPoolSize))
                .setMemoryCache(new LruResourceCache(defaultMemoryCacheSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        Cache cache = new Cache(new File(context.getCacheDir(), "okhttp"), DISK_CACHE_SIZE);
        OkHttpClient client = new OkHttpClient().newBuilder().cache(cache).build();
        glide.register(CachedGlideUrl.class, InputStream.class, superFactory(new OkHttpUrlLoader.Factory(client), CachedGlideUrl.class));
        glide.register(ForceLoadGlideUrl.class, InputStream.class, superFactory(new OkHttpUrlLoader.Factory(client), ForceLoadGlideUrl.class));
    }

    @SuppressWarnings({"unchecked", "unused"})
    private static <T> ModelLoaderFactory<T, InputStream> superFactory(
            ModelLoaderFactory<? super T, InputStream> factory, Class<T> modelType) {
        return (ModelLoaderFactory<T, InputStream>)factory;
    }


    public static class CachedGlideUrl extends GlideUrl {
        public CachedGlideUrl(String url) {
            super(url);
        }
    }

    public static class ForceLoadGlideUrl extends GlideUrl {
        private static final Headers FORCE_ETAG_CHECK = new LazyHeaders.Builder()
                // I need to set the Cache-Control in order to force server side validation for the ETAG
                .addHeader("Cache-Control", "max-age=0")
                .build();
        public ForceLoadGlideUrl(String url) { super(url, FORCE_ETAG_CHECK); }
    }

}