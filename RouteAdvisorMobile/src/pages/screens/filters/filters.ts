import {Component} from "@angular/core";
import {NavController, NavParams, LoadingController} from "ionic-angular";
import {MapPage} from "../map/map";
import {TitleIcon} from "../../../model/title-icon-model";
import {ChooseTripPlaceModel, Action} from "../../../model/choose-trip-place-model";
import {constants} from "../../../shared/constants";
import {PlacesFilterModel, FilterType} from "../../../model/places-filter-model";
import {PlacesPage} from "../places/places";
import {HotelsPage} from "../hotels/hotels";
import {NotificationService} from "../../../providers/helper/notification-service";
import {HttpInterceptorService} from "../../../providers/http/http-interceptor-service";
import {ErrorHandlerService} from "../../../providers/helper/error-handler-service";

@Component({
    selector: 'page-filters',
    templateUrl: 'filters.html'
})
export class FiltersPage {

    placesFilterModel: PlacesFilterModel;
    map: Map<string,TitleIcon> = new Map<string,TitleIcon>();

    constructor(private navCtrl: NavController,
                private loadingCtrl: LoadingController,
                private errorHandlerService: ErrorHandlerService,
                private notificationService: NotificationService,
                public httpInterceptorService: HttpInterceptorService,
                private navParams: NavParams) {
        this.placesFilterModel = this.navParams.data;
        this.map = this.initMap();
    }

    ionViewDidLoad() {
        this.map = this.initMap();
    }

    filter(value, type) {

        if (type === 'top') {
            if (value === 'trip') {
                this.placesFilterModel.inTrip = !this.placesFilterModel.inTrip;
            }
            if (value === 'custom') {
                this.placesFilterModel.custom = !this.placesFilterModel.custom;
            }
            if (value === 'favorited') {
                this.placesFilterModel.favorited = !this.placesFilterModel.favorited;
            }
        }
        let additionalParam = `&inTrip=${this.placesFilterModel.inTrip}&custom=${this.placesFilterModel.custom}&favorited=${this.placesFilterModel.favorited}`;

        if (this.placesFilterModel.type === FilterType.PLACES) {
            let chooseTripPlaceModel: ChooseTripPlaceModel = new ChooseTripPlaceModel();
            chooseTripPlaceModel.action = Action.NONE;

            let categories = this.getNames(this.placesFilterModel.categoriesTags.categories);
            let tags = this.getNames(this.placesFilterModel.categoriesTags.tags);

            chooseTripPlaceModel.url = `${constants.allPlaces}&categories=${categories}&tags=${tags}${additionalParam}`;
            chooseTripPlaceModel.tripId = this.placesFilterModel.tripId;

            this.navCtrl.push(PlacesPage, chooseTripPlaceModel);
            const index = this.navCtrl.getActive().index;
            this.navCtrl.remove(index - 2, index);
        }
        if (this.placesFilterModel.type === FilterType.HOTELS) {

            this.navCtrl.push(HotelsPage);
            const index = this.navCtrl.getActive().index;
            this.navCtrl.remove(index - 2, index);
        }
        if (this.placesFilterModel.type === FilterType.MAP) {

            let mapParams = this.placesFilterModel.map;

            let categories = this.getNames(this.placesFilterModel.categoriesTags.categories);
            let tags = this.getNames(this.placesFilterModel.categoriesTags.tags);

            mapParams.urlParams = `&categories=${categories}&tags=${tags}${additionalParam}`;

            this.navCtrl.push(MapPage, mapParams);
            const index = this.navCtrl.getActive().index;
            this.navCtrl.remove(index - 2, index);
        }
    }

    private getNames(list: Array<any>) {
        let temp = list.filter((value, i, arr)=> value.selected === true).map((value, i, arr)=> {
            return value.name;
        });
        let result = temp.join(',');
        return result;
    }


    private initMap() {
        let map = new Map<string,TitleIcon>();
        map.set('none', new TitleIcon('None', 'star'));
        map.set('family', new TitleIcon('Family', 'contacts'));
        map.set('museum', new TitleIcon('Museums', 'bulb'));
        map.set('transport', new TitleIcon('Transport', 'subway'));
        map.set('sport', new TitleIcon('Sports', 'football'));
        map.set('restaurant', new TitleIcon('Restaurants', 'restaurant'));
        map.set('cafe', new TitleIcon('Cafees', 'cafe'));
        map.set('nightlife', new TitleIcon('Nightlife', 'beer'));
        map.set('shopping', new TitleIcon('Shopping', 'pricetags'));
        map.set('relax', new TitleIcon('Relaxation', 'body'));
        map.set('outdoors', new TitleIcon('Outdoors', 'ice-cream'));
        map.set('sightseeing', new TitleIcon('Sightseeing', 'camera'));
        return map;
    }

    getTitle(cat) {
        let key = cat.toLowerCase();
        if (this.map.has(key)) {
            return this.map.get(key).title;
        } else {
            return 'General';
        }
    }

    getIcon(cat) {
        let key = cat.toLowerCase();
        if (this.map.has(key)) {
            return this.map.get(key).icon;
        } else {
            return 'alert';
        }
    }

}
