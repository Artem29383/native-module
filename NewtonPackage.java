package io.tezis.mobile.modules;

import androidx.annotation.NonNull;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.tezis.mobile.BuildConfig;
import io.tezis.mobile.modules.apprating.AppRatingModule;
import io.tezis.mobile.modules.authorization.NTAuthorizationModule;
import io.tezis.mobile.modules.charts.NTPortfolioChartModule;
import io.tezis.mobile.modules.charts.charts.NTPortfolioSummaryLineChartManager;
import io.tezis.mobile.modules.charts.charts.NTPriceHistoryCandleChartManager;
import io.tezis.mobile.modules.charts.charts.NTPriceHistoryLineChartManager;
import io.tezis.mobile.modules.chat.NTChatModule;
import io.tezis.mobile.modules.development.NTDevelopmentMenuModule;
import io.tezis.mobile.modules.dialog.NTDialogModule;
import io.tezis.mobile.modules.filesystem.NTDocumentFileUploadModule;
import io.tezis.mobile.modules.filesystem.NTDocumentPickerModule;
import io.tezis.mobile.modules.helpers.NTHelpersModule;
import io.tezis.mobile.modules.metrica.NTFacebookAnalyticsModule;
import io.tezis.mobile.modules.metrica.NTMetricaModule;
import io.tezis.mobile.modules.metrica.NTAppsFlyerModule;
import io.tezis.mobile.modules.metrica.NTFirebaseEventsModule;
import io.tezis.mobile.modules.nativeModuleStart.NativeModuleStart;
import io.tezis.mobile.modules.notifications.NTNotificationsModule;
import io.tezis.mobile.modules.security.NTRootDetectionModule;
import io.tezis.mobile.modules.storage.NTStorageModule;
import io.tezis.mobile.modules.theme.NTThemeModule;
import io.tezis.mobile.modules.tradingview.pricechart.presentation.NTCandlesPriceChartManager;
import io.tezis.mobile.modules.tradingview.pricechart.presentation.NTLinePriceChartManager;

public class NewtonPackage implements ReactPackage {

    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        return Arrays.asList(
            new NTPortfolioSummaryLineChartManager(),
            new NTPriceHistoryCandleChartManager(),
            new NTPriceHistoryLineChartManager(),
            new NTLinePriceChartManager(reactContext),
            new NTCandlesPriceChartManager(reactContext)
        );
    }

    @NonNull
    @Override
    public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>(Arrays.asList(
            new NativeModuleStart(reactContext),
            new NTAuthorizationModule(reactContext),
            new NTDialogModule(reactContext),
            new NTHelpersModule(reactContext),
            new NTPortfolioChartModule(reactContext),
            new NTRootDetectionModule(reactContext),
            new NTStorageModule(reactContext),
            new NTNotificationsModule(reactContext),
            new NTThemeModule(reactContext),
            new NTChatModule(reactContext),
            new NTMetricaModule(reactContext),
            new NTAppsFlyerModule(reactContext),
            new NTFirebaseEventsModule(reactContext),
            new NTFacebookAnalyticsModule(reactContext),
            new NTDocumentPickerModule(reactContext),
            new NTDocumentFileUploadModule(),
            new AppRatingModule(reactContext)
        ));

        if (BuildConfig.DEBUG) {
            modules.add(new NTDevelopmentMenuModule(reactContext));
        }

        return modules;
    }
}
