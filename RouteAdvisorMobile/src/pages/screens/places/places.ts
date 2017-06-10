import {Component} from "@angular/core";
import {NavController, NavParams, LoadingController} from "ionic-angular";
import {FiltersPage} from "../filters/filters";
import {SearchPage} from "../search/search";
import {PlacePage} from "../place/place";
import {LoggerService} from "../../../providers/shared/logger-service";
import {HttpInterceptorService} from "../../../providers/http/http-interceptor-service";
import {ChooseTripPlaceModel, Action, ActionDetails} from "../../../model/choose-trip-place-model";
import {TripDetailsPage} from "../trip-details/trip-details";
import {PlacesFilterModel, FilterType} from "../../../model/places-filter-model";
import {NotificationService} from "../../../providers/helper/notification-service";
import {ErrorHandlerService} from "../../../providers/helper/error-handler-service";

@Component({
    selector: 'page-places',
    templateUrl: 'places.html'
})
export class PlacesPage {

    placesResponse = {
        recommended: [],
        result: [],
        categoriesTags: {},
        inTrip: false,
        custom: false,
        favorited: false
    };
    chooseTripPlaceModel: ChooseTripPlaceModel;

    constructor(public navCtrl: NavController,
                private loadingCtrl: LoadingController,
                private errorHandlerService: ErrorHandlerService,
                private notificationService: NotificationService,
                public httpInterceptorService: HttpInterceptorService,
                public loggerService: LoggerService,
                public navParams: NavParams) {
        this.chooseTripPlaceModel = this.navParams.data;
    }

    ionViewDidLoad() {
        this.initItems();
    }

    showSearchPage() {
        this.navCtrl.push(SearchPage);
    }

    showFilters() {
        let placesFilterModel: PlacesFilterModel = new PlacesFilterModel();
        placesFilterModel.categoriesTags = this.placesResponse.categoriesTags;
        placesFilterModel.inTrip = this.placesResponse.inTrip;
        placesFilterModel.custom = this.placesResponse.custom;
        placesFilterModel.favorited = this.placesResponse.favorited;
        placesFilterModel.type = FilterType.PLACES;
        placesFilterModel.tripId = this.chooseTripPlaceModel.tripId;
        this.navCtrl.push(FiltersPage, placesFilterModel);
    }

    goToDetails(item) {
        if (this.chooseTripPlaceModel.action !== Action.NONE) {
            if (this.chooseTripPlaceModel.action === Action.CREATE_TRIP) {
                switch (this.chooseTripPlaceModel.actionDetails) {
                    case ActionDetails.CREATE_TRIP_ARRIVAL: {
                        this.chooseTripPlaceModel.trip.arrival = item;
                        break;
                    }
                }
                this.navCtrl.push(TripDetailsPage, this.chooseTripPlaceModel.trip).then(() => {
                    const index = this.navCtrl.getActive().index;
                    this.navCtrl.remove(index - 2, index);
                });
            }
        } else {
            this.navCtrl.push(PlacePage, {place: item});
        }
    }

    private initItems() {
        let loading = this.loadingCtrl.create({});
        loading.present();
        return this.httpInterceptorService.get(`${this.chooseTripPlaceModel.url}&tripId=${this.chooseTripPlaceModel.tripId}&addRecommended=true&addCatTags=true`)
            .toPromise()
            .then((response)=> {
                loading.dismissAll();
                this.placesResponse = response;
            })
            .catch((err)=> {
                loading.dismissAll();
                this.errorHandlerService.handle(err, 'Unpredictable behaviour. Please try again');
            });
    }
}
