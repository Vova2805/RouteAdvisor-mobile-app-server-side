import {Component} from "@angular/core";
import {NavController, NavParams, LoadingController} from "ionic-angular";
import {Observable, Subject} from "rxjs";
import {HttpInterceptorService} from "../../../providers/http/http-interceptor-service";
import {NotificationService} from "../../../providers/helper/notification-service";
import {ErrorHandlerService} from "../../../providers/helper/error-handler-service";
import {constants} from "../../../shared/constants";
import {PlacePage} from "../place/place";

@Component({
    selector: 'page-search',
    templateUrl: 'search.html'
})
export class SearchPage {

    searchResult: Observable<any>;
    searchTerm: string = '';
    private searchTermStream = new Subject<string>();

    constructor(public navCtrl: NavController,
                private loadingCtrl: LoadingController,
                private errorHandlerService: ErrorHandlerService,
                private notificationService: NotificationService,
                private httpInterceptorService: HttpInterceptorService,
                public navParams: NavParams) {
        this.searchResult = this.searchTermStream
            .debounceTime(500)
            .distinctUntilChanged()
            .switchMap((term: string) => null);
    }

    onInput(event: any) {
        this.updateSearchResult(event.target.value);
        this.searchTermStream.next(event.target.value);
    }

    private updateSearchResult(term) {
        let loading = this.loadingCtrl.create({});
        loading.present();
        this.httpInterceptorService.get(`${constants.placesSearch}${term}`)
            .toPromise()
            .then((result)=> {
                this.searchResult = result;
                loading.dismiss();
            })
            .catch((err)=> {
                this.errorHandlerService.handle(err, 'Unable to search.Please try later');
            });
    }

    ionViewDidLoad() {
    }

    goToDetails(item) {
        this.navCtrl.push(PlacePage, {place: item});
    }
}
