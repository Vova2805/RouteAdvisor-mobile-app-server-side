import {Component} from "@angular/core";
import {NavController, NavParams, LoadingController} from "ionic-angular";
import {TemplateTourDetailsPage} from "../template-tour-details/template-tour-details";
import {HttpInterceptorService} from "../../../providers/http/http-interceptor-service";
import {LoggerService} from "../../../providers/shared/logger-service";
import {NotificationService} from "../../../providers/helper/notification-service";
import {ErrorHandlerService} from "../../../providers/helper/error-handler-service";
import {constants} from "../../../shared/constants";
import {SelectTemplateModel} from "../../../model/select-template-model";

@Component({
    selector: 'page-tours',
    templateUrl: 'tours.html'
})
export class ToursPage {

    selectTemplateModel: SelectTemplateModel = new SelectTemplateModel();

    constructor(private navCtrl: NavController,
                private loadingCtrl: LoadingController,
                private errorHandlerService: ErrorHandlerService,
                private notificationService: NotificationService,
                public httpInterceptorService: HttpInterceptorService,
                private loggerService: LoggerService,
                private navParams: NavParams) {
        this.selectTemplateModel = this.navParams.data;
    }

    toursResponse = {
        recommended: [],
        result: []
    };

    ionViewDidLoad() {
        this.initItems();
    }

    private initItems() {
        let loading = this.loadingCtrl.create({});
        loading.present();
        let url = constants.cityAllTemplates;
        url = url.replace('{id}', this.selectTemplateModel.cityId);
        return this.httpInterceptorService.get(`${url}&type=tour&addRecommended=true`)
            .toPromise()
            .then((response)=> {
                this.toursResponse = response;
                loading.dismissAll();
            })
            .catch((err)=> {
                loading.dismissAll();
                this.errorHandlerService.handle(err, 'Unable to retrieve tours. Please try again');
            });
    }

    openTourDetails(item) {
        this.navCtrl.push(TemplateTourDetailsPage, {type: 'tour', item: item});
    }
}
