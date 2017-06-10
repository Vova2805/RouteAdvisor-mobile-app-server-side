import {Component} from "@angular/core";
import {Platform} from "ionic-angular";
import {HomePage} from "../pages/screens/home/home";
import {AuthService} from "../providers/shared/auth-service";
import {Welcome} from "../pages/screens/welcome/welcome";
import {AppComponentService} from "./app-component-service";

@Component({
    templateUrl: 'app.component.html',
    providers: [AppComponentService]
})
export class AppComponent {

    userAuthorized = true;
    rootPage: any;

    constructor(private platform: Platform,
                private authService: AuthService) {
    }
}