import {Component} from "@angular/core";
import {NavController, LoadingController} from "ionic-angular";
import {Pager} from "../pager/pager";
import {HttpInterceptorService} from "../../../providers/http/http-interceptor-service";
import {NotificationService} from "../../../providers/helper/notification-service";
import {ErrorHandlerService} from "../../../providers/helper/error-handler-service";

@Component({
    selector: 'page-welcome',
    templateUrl: 'welcome.html'
})
export class Welcome {

    ionViewDidLoad() {
    }

    constructor(public nav: NavController,
                private loadingCtrl: LoadingController,
                private errorHandlerService: ErrorHandlerService,
                private notificationService: NotificationService,
                public httpInterceptorService: HttpInterceptorService) {
    }

    public goToPager() {
        this.nav.push(Pager);
    }
}