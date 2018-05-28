package com.acmenxd.glide;

import android.support.annotation.NonNull;

import java.io.File;

/**
 * Author: weichyang
 * Date:   2018/5/25
 * Description:
 */
public interface SaveCallback {

    void succeed(@NonNull final File file);

    void failed(@NonNull final Exception pE);
}
