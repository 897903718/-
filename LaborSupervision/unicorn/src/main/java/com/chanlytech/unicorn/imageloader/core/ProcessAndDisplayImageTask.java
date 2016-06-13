/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.chanlytech.unicorn.imageloader.core;

import android.graphics.Bitmap;
import android.os.Handler;

import com.chanlytech.unicorn.imageloader.core.assist.LoadedFrom;
import com.chanlytech.unicorn.imageloader.core.process.BitmapProcessor;
import com.chanlytech.unicorn.imageloader.utils.L;

/**
 * Presents process'n'display image task. Processes image {@linkplain android.graphics.Bitmap} and display it in {@link android.widget.ImageView} using
 * <<<<<<< HEAD
 * {@link DisplayBitmapTask}.
 * =======
 * {@link com.chanlytech.unicorn.imageloader.core.DisplayBitmapTask}.
 * >>>>>>> 3d93dda83bebe4abb74f4bd33ca8ab71908e18a9
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @since 1.8.0
 */
final class ProcessAndDisplayImageTask implements Runnable
{

    private static final String LOG_POSTPROCESS_IMAGE = "PostProcess image before displaying [%s]";

    private final ImageLoaderEngine engine;
    private final Bitmap            bitmap;
    private final ImageLoadingInfo  imageLoadingInfo;
    private final Handler           handler;

    public ProcessAndDisplayImageTask(ImageLoaderEngine engine, Bitmap bitmap, ImageLoadingInfo imageLoadingInfo, Handler handler)
    {
        this.engine = engine;
        this.bitmap = bitmap;
        this.imageLoadingInfo = imageLoadingInfo;
        this.handler = handler;
    }

    @Override
    public void run()
    {
        L.d(LOG_POSTPROCESS_IMAGE, imageLoadingInfo.memoryCacheKey);

        BitmapProcessor processor = imageLoadingInfo.options.getPostProcessor();
        Bitmap processedBitmap = processor.process(bitmap);
        DisplayBitmapTask displayBitmapTask = new DisplayBitmapTask(processedBitmap, imageLoadingInfo, engine, LoadedFrom.MEMORY_CACHE);
        LoadAndDisplayImageTask.runTask(displayBitmapTask, imageLoadingInfo.options.isSyncLoading(), handler, engine);
    }
}
