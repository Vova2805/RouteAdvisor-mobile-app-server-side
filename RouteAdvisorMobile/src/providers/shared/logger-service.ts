import {Injectable} from "@angular/core";
import "rxjs/add/operator/map";

@Injectable()
export class LoggerService {

    private loggingEnabled = true;

    constructor() {
    }

    public log(message: string, object?: any, show?: boolean) {
        if (this.loggingEnabled) {
            console.log(message, JSON.stringify(object));
        }
    }

    public error(message: string, object?: any, show?: boolean) {
        if (this.loggingEnabled) {
            console.error(message, JSON.stringify(object));
        }
    }
}