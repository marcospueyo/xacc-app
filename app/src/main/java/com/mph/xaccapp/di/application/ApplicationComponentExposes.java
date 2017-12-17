package com.mph.xaccapp.di.application;

import com.mph.xaccapp.di.application.module.DataModule;
import com.mph.xaccapp.di.application.module.InteractorModule;
import com.mph.xaccapp.di.application.module.MappersModule;
import com.mph.xaccapp.di.application.module.NetworkModule;
import com.mph.xaccapp.di.application.module.RepositoryModule;
import com.mph.xaccapp.di.application.module.ServiceModule;

public interface ApplicationComponentExposes extends
        ApplicationModule.Exposes,
        MappersModule.Exposes,
        NetworkModule.Exposes,
        ServiceModule.Exposes,
        RepositoryModule.Exposes,
        InteractorModule.Exposes,
        DataModule.Exposes {
}
