import {Component} from "@angular/core";
import {NavController, PopoverController, NavParams, LoadingController} from "ionic-angular";
import {ItineraryPage} from "../itinerary/itinerary";
import {WeatherPage} from "../weather/weather";
import {MapPage} from "../map/map";
import {PlacesPage} from "../places/places";
import {ToursPage} from "../tours/tours";
import {HotelsPage} from "../hotels/hotels";
import {constants} from "../../../shared/constants";
import {HttpInterceptorService} from "../../../providers/http/http-interceptor-service";
import {ChooseTripPlaceModel, Action} from "../../../model/choose-trip-place-model";
import {SharedService} from "../../../providers/shared/shared-service";
import {NotificationService} from "../../../providers/helper/notification-service";
import {ErrorHandlerService} from "../../../providers/helper/error-handler-service";
import {MapDataModel} from "../../../model/map-data-model";
import {SelectTemplateModel} from "../../../model/select-template-model";

@Component({
    selector: 'page-trip-home',
    templateUrl: 'trip-home.html'
})
export class TripHomePage {

    daysCount: string = '0 days';

    trip: any = {
        name: 'Lviv fabulous',
        photoURL: 'img/background.png'
    };

    private items: any[] = [];

    objectForMap: MapDataModel = new MapDataModel();

    constructor(public navCtrl: NavController,
                public navParams: NavParams,
                private sharedService: SharedService,
                private loadingCtrl: LoadingController,
                private errorHandlerService: ErrorHandlerService,
                private notificationService: NotificationService,
                public httpInterceptorService: HttpInterceptorService,
                private popoverCtrl: PopoverController) {
        this.trip = this.navParams.get('trip');
        this.sharedService.currentTrip = this.trip;
        let url = constants.tripDaysCount;
        url = url.replace('{id}', this.trip.id);
        let loading = this.loadingCtrl.create({});
        loading.present();
        this.getTripDetails();
        this.httpInterceptorService.get(url)
            .toPromise()
            .then((response)=> {
                this.daysCount = `${response} days`;
                loading.dismissAll();
            })
            .catch((err)=> {
                loading.dismissAll();
                this.errorHandlerService.handle(err, 'Unable to get days count. Please try again');
            });
    }

    private getTripDetails() {
        let url = constants.tripDays;
        url = url.replace('{id}', this.trip.id);
        this.httpInterceptorService.get(url)
            .toPromise()
            .then((response)=> {
                this.items = response;
                this.items.forEach((value, index, arr)=> {
                    for (let i = 0; i < value.places.length; i++) {
                        this.objectForMap.places.push(value.places[i]);
                    }
                });
            })
            .catch((err)=> {
                this.errorHandlerService.handle(err, 'Unable to get trip details. Please try again');
            });
    }

    goToItinerary() {
        this.navCtrl.push(ItineraryPage, {days: this.items, trip: this.trip});
    }

    ionViewDidLoad() {
        console.log('ionViewDidLoad TripHomePage');
    }

    showMap() {
        this.objectForMap.includeAllPlaces = true;
        this.navCtrl.push(MapPage, this.objectForMap);
    }

    showPlaces() {
        let chooseTripPlaceModel: ChooseTripPlaceModel = new ChooseTripPlaceModel();
        chooseTripPlaceModel.action = Action.NONE;
        chooseTripPlaceModel.url = constants.allPlaces;
        chooseTripPlaceModel.tripId = this.trip.id;
        this.navCtrl.push(PlacesPage, chooseTripPlaceModel);
    }

    showTours() {
        let selectTemplateModel: SelectTemplateModel = new SelectTemplateModel();
        selectTemplateModel.cityId = this.trip.cityId;
        this.navCtrl.push(ToursPage, selectTemplateModel);
    }

    showHotels() {
        let chooseTripPlaceModel: ChooseTripPlaceModel = new ChooseTripPlaceModel();
        chooseTripPlaceModel.action = Action.NONE;
        chooseTripPlaceModel.url = constants.allHotels;
        chooseTripPlaceModel.tripId = this.trip.id;
        this.navCtrl.push(HotelsPage, chooseTripPlaceModel);
    }

    showWeather() {
        this.navCtrl.push(WeatherPage);
    }
}