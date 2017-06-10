import {NgModule, ErrorHandler, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {IonicApp, IonicModule, IonicErrorHandler} from "ionic-angular";
import {AppComponent} from "./app.component";
import {Welcome} from "../pages/screens/welcome/welcome";
import {Pager} from "../pages/screens/pager/pager";
import {TripDetailsPage} from "../pages/screens/trip-details/trip-details";
import {CreateAccountPage} from "../pages/screens/create-account/create-account";
import {TripHomePage} from "../pages/screens/trip-home/trip-home";
import {MapPage} from "../pages/screens/map/map";
import {FiltersPage} from "../pages/screens/filters/filters";
import {SearchPage} from "../pages/screens/search/search";
import {EditTripPage} from "../pages/screens/edit-trip/edit-trip";
import {ItineraryPage} from "../pages/screens/itinerary/itinerary";
import {DayDetailsPage} from "../pages/screens/day-details/day-details";
import {TimelinePage} from "../pages/controls/timeline/timeline";
import {NotesPage} from "../pages/controls/notes/notes";
import {EditDayPage} from "../pages/screens/edit-day/edit-day";
import {PlacePage} from "../pages/screens/place/place";
import {PlaceDetailsPage} from "../pages/controls/place-details/place-details";
import {PlacesPage} from "../pages/screens/places/places";
import {HotelsPage} from "../pages/screens/hotels/hotels";
import {ToursPage} from "../pages/screens/tours/tours";
import {WeatherPage} from "../pages/screens/weather/weather";
import {HomePage} from "../pages/screens/home/home";
import {HomeContentPage} from "../pages/controls/home-content/home-content";
import {SignUpPage} from "../pages/screens/sign-up/sign-up";
import {LoginViaPage} from "../pages/screens/login-via/login-via";
import {LoginPage} from "../pages/screens/login/login";
import {AboutRouteAdvisorPage} from "../pages/screens/about-route-advisor/about-route-advisor";
import {AddNotePage} from "../pages/controls/add-note/add-note";
import {SelectCityPage} from "../pages/screens/select-city/select-city";
import {SelectTemplatePage} from "../pages/screens/select-template/select-template";
import {IonPullupModule} from "ionic-pullup/dist/ion-pullup.module";
import {audioProviderFactory, AudioProvider} from "ionic-audio/dist/ionic-audio-providers";
import {IonicAudioModule} from "ionic-audio/dist/ionic-audio.module";
import {BrowserModule} from "@angular/platform-browser";
import {ImagesPage} from "../pages/controls/images/images";
import {AudioPage} from "../pages/controls/audio/audio";
import {VideoPage} from "../pages/controls/video/video";
import {SafePipe} from "../pipes/safe-pipe";
import {HttpModule, JsonpModule} from "@angular/http";
import {TemplateTourDetailsPage} from "../pages/screens/template-tour-details/template-tour-details";
import {ConnectivityService} from "../providers/integration/google/connectivity-service";
import {MapInitService} from "../providers/integration/google/map-init-service";
import {HttpInterceptorService} from "../providers/http/http-interceptor-service";
import {LoggerService} from "../providers/shared/logger-service";
import {WeatherService} from "../providers/integration/weather/weather-service";
import {WikipediaService} from "../providers/integration/wikipedia/wikipedia-service";
import {GooglePlacesService} from "../providers/integration/google/google-places-service";
import {GoogleDestinationService} from "../providers/integration/google/google-destination-service";
import {GoogleMapsCluster} from "../providers/integration/google/google-maps-cluster";
import {SharedService} from "../providers/shared/shared-service";
import {FormsModule} from "@angular/forms";
import {AuthService} from "../providers/shared/auth-service";
import {ValidateService} from "../providers/shared/validate-service";
import {ErrorHandlerService} from "../providers/helper/error-handler-service";
import {NotificationService} from "../providers/helper/notification-service";
import {ChangeDurationComponent} from "../pages/controls/change-duration/change-duration";
import {Ionic2RatingModule} from "ionic2-rating";
import {AddToTripControlComponent} from "../pages/controls/add-to-trip-control/add-to-trip-control";

@NgModule({
    declarations: [
        AppComponent,
        Welcome,
        Pager,
        SelectCityPage,
        SelectTemplatePage,
        TripDetailsPage,
        TemplateTourDetailsPage,
        CreateAccountPage,
        TripHomePage,
        MapPage,
        FiltersPage,
        SearchPage,
        EditTripPage,
        ItineraryPage,
        DayDetailsPage,
        TimelinePage,
        NotesPage,
        EditDayPage,
        PlaceDetailsPage,
        PlacePage,
        VideoPage,
        ImagesPage,
        AudioPage,
        PlacesPage,
        HotelsPage,
        ToursPage,
        WeatherPage,
        HomeContentPage,
        HomePage,
        SignUpPage,
        LoginViaPage,
        LoginPage,
        AboutRouteAdvisorPage,
        AddNotePage,
        ChangeDurationComponent,
        AddToTripControlComponent,

        SafePipe
    ],
    imports: [
        IonicModule.forRoot(AppComponent),
        HttpModule,
        JsonpModule,
        FormsModule,
        BrowserModule,
        IonicAudioModule.forRoot({provide: AudioProvider, useFactory: audioProviderFactory}),
        IonPullupModule,
        Ionic2RatingModule
    ],
    bootstrap: [IonicApp],
    entryComponents: [
        AppComponent,
        Welcome,
        Pager,
        SelectCityPage,
        SelectTemplatePage,
        TripDetailsPage,
        TemplateTourDetailsPage,
        CreateAccountPage,
        TripHomePage,
        MapPage,
        FiltersPage,
        SearchPage,
        EditTripPage,
        ItineraryPage,
        DayDetailsPage,
        TimelinePage,
        NotesPage,
        EditDayPage,
        PlaceDetailsPage,
        PlacePage,
        VideoPage,
        ImagesPage,
        AudioPage,
        PlacesPage,
        HotelsPage,
        ToursPage,
        WeatherPage,
        HomeContentPage,
        HomePage,
        SignUpPage,
        LoginViaPage,
        LoginPage,
        AboutRouteAdvisorPage,
        AddNotePage,
        ChangeDurationComponent,
        AddToTripControlComponent
    ],
    providers: [
        {provide: ErrorHandler, useClass: IonicErrorHandler},
        ConnectivityService,
        MapInitService,
        HttpInterceptorService,
        LoggerService,
        WeatherService,
        WikipediaService,
        GooglePlacesService,
        GoogleDestinationService,
        GoogleMapsCluster,
        SharedService,
        AuthService,
        ValidateService,
        ErrorHandlerService,
        NotificationService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule {
}