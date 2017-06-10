import {Component, ElementRef, ViewChild, ChangeDetectorRef, NgZone} from "@angular/core";
import {NavController, NavParams, LoadingController} from "ionic-angular";
import {SearchPage} from "../search/search";
import {FiltersPage} from "../filters/filters";
import {MapInitService} from "../../../providers/integration/google/map-init-service";
import {SharedService} from "../../../providers/shared/shared-service";
import {PlacePage} from "../place/place";
import {HttpInterceptorService} from "../../../providers/http/http-interceptor-service";
import {NotificationService} from "../../../providers/helper/notification-service";
import {ErrorHandlerService} from "../../../providers/helper/error-handler-service";
import {MapDataModel} from "../../../model/map-data-model";
import {constants} from "../../../shared/constants";
import {PlacesFilterModel, FilterType} from "../../../model/places-filter-model";

@Component({
    selector: 'page-map',
    templateUrl: 'map.html'
})
export class MapPage {

    @ViewChild('map') mapElement: ElementRef;
    @ViewChild('pleaseConnect') pleaseConnect: ElementRef;
    @ViewChild('buttonToggle') buttonToggle: ElementRef;

    infoOpen: boolean = false;
    pointSelected: any;
    info: MapDataModel;
    trip;

    placesResponse;

    constructor(private navCtrl: NavController,
                private navParams: NavParams,
                private sharedService: SharedService,
                private chRef: ChangeDetectorRef,
                private ngZone: NgZone,
                private loadingCtrl: LoadingController,
                private errorHandlerService: ErrorHandlerService,
                private notificationService: NotificationService,
                public httpInterceptorService: HttpInterceptorService,
                private mapInitService: MapInitService) {
        this.info = this.navParams.data;
        console.log('Map DATA', this.info);
        this.trip = this.sharedService.currentTrip;
        this.sharedService.selectedPointChangedStream$.subscribe((point)=> {
            this.ngZone.run(()=> {
                this.infoOpen = true;
                this.pointSelected = point;
            });
        });
    }

    ionViewDidLoad() {
        let loading = this.loadingCtrl.create({});
        loading.present();
        this.info.allPlaces = this.info.places;
        if (this.info.includeAllPlaces) {
            if (typeof this.info.urlParams === 'undefined') {
                this.info.urlParams = '';
            }
            let tripId = this.trip ? `&tripId=${this.trip.id}` : '';
            let url = `${constants.places}?addCatTags=true${tripId}${this.info.urlParams}`;
            this.httpInterceptorService.get(url)
                .toPromise()
                .then((response)=> {
                    this.placesResponse = response;
                    this.info.allPlaces = this.placesResponse.result;
                    this.initMap(loading, this.info);
                })
                .catch((err)=> {
                    this.errorHandlerService.handle(err, 'Unable to search. Please try later');
                    this.initMap(loading, this.info);
                });
        } else {
            this.initMap(loading, this.info);
        }
    }

    private initMap(loading, info) {
        let mapLoaded = this.mapInitService.init(this.mapElement, this.pleaseConnect, info, this);
        loading.dismissAll();
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
        placesFilterModel.map = this.info;
        placesFilterModel.type = FilterType.MAP;
        if (this.sharedService.currentTrip) {
            placesFilterModel.tripId = this.sharedService.currentTrip.id;
        } else {
            placesFilterModel.tripId = '1';
        }
        this.navCtrl.push(FiltersPage, placesFilterModel);
    }

    closeDetails() {
        this.infoOpen = false;
    }

    goToPlaceDetails(place) {
        this.navCtrl.push(PlacePage, {place: place});
    }

    locateMyPos() {
        this.notificationService.show('Geolocation started');
        this.mapInitService.geolocate();
    }
}
