import {Component} from "@angular/core";
import {NavController, NavParams, LoadingController} from "ionic-angular";
import {WeatherService} from "../../../providers/integration/weather/weather-service";
import {LoggerService} from "../../../providers/shared/logger-service";
import {constants} from "../../../shared/constants";
import {ErrorHandlerService} from "../../../providers/helper/error-handler-service";
import {HttpInterceptorService} from "../../../providers/http/http-interceptor-service";
import {NotificationService} from "../../../providers/helper/notification-service";

@Component({
    selector: 'page-weather',
    templateUrl: 'weather.html'
})
export class WeatherPage {

    constructor(private navCtrl: NavController,
                private navParams: NavParams,
                private loadingCtrl: LoadingController,
                private errorHandlerService: ErrorHandlerService,
                private notificationService: NotificationService,
                public httpInterceptorService: HttpInterceptorService,
                private loggerService: LoggerService,
                private weatherService: WeatherService) {
    }

    retrieved = false;

    weather: any = {
        city: '',
        weather: '',
        description: '',
        forecast: []
    };

    ionViewDidLoad() {
        let loading = this.loadingCtrl.create({});
        loading.present();
        this.weatherService.updateWeather(constants.lat, constants.lng)
            .then((response)=> {
                if (response.success) {
                    this.retrieved = true;
                    this.weather = response.weather;
                }
                loading.dismiss();
            })
            .catch((err)=> {
                loading.dismiss();
                this.errorHandlerService.handle(err, 'Weather service is unreachable. Please try later');
            });
    }
}