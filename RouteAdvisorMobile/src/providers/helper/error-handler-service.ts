import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import "rxjs/add/operator/map";
import {NotificationService} from "./notification-service";
import {LoggerService} from "../shared/logger-service";

@Injectable()
export class ErrorHandlerService {

    constructor(public http: Http,
                public loggerService: LoggerService,
                public notificationService: NotificationService) {
    }

    public handle(err, text) {
        this.loggerService.log('Error occured : ', err);
        this.notificationService.show(text);
    }
}