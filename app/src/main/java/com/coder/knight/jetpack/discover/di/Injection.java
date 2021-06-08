package com.coder.knight.jetpack.discover.di;

import android.app.Application;

import com.coder.knight.jetpack.discover.data.source.ContentRepository;
import com.coder.knight.jetpack.discover.data.source.local.LocalRepository;
import com.coder.knight.jetpack.discover.data.source.remote.ApiInterface;
import com.coder.knight.jetpack.discover.data.source.remote.RemoteRepository;
import com.coder.knight.jetpack.discover.data.source.remote.RetrofitService;

public class Injection {
    public static ContentRepository providerRepository(Application application) {
        ApiInterface apiInterface = RetrofitService.createService(ApiInterface.class);
        RemoteRepository remoteRepository = RemoteRepository.getInstance(apiInterface);
        LocalRepository localRepository = LocalRepository.getInstance(application);
        return ContentRepository.getInstance(remoteRepository, localRepository);
    }
}
