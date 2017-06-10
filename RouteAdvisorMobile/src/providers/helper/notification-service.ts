import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import "rxjs/add/operator/map";
import {ToastController} from "ionic-angular";

@Injectable()
export class NotificationService {

    constructor(public http: Http,
                public toastCtrl: ToastController) {
    }

    public show(text) {
        this.presentToast(text, 'bottom');
    }

    presentToast(text, position) {
        let toast = this.toastCtrl.create({
            message: text,
            position: position,
            duration: 3000
        });
        toast.present();
    }
}