import {Injectable} from "@angular/core";
import {Http, Jsonp} from "@angular/http";
import "rxjs/add/operator/map";
import {LoggerService} from "../../shared/logger-service";
import {constants} from "../../../shared/constants";

@Injectable()
export class GooglePlacesService {

    private baseTextSearchURL = 'https://maps.googleapis.com/maps/api/place/textsearch/json';

    constructor(public http: Http,
                public jsonp: Jsonp,
                public loggerService: LoggerService) {
        console.log('Hello GooglePlacesService Provider');
    }


    public searchPlacesByTermAndLocation(term, lat, lng) {
        return this.jsonp
            .request(`${this.baseTextSearchURL}?query=${term}&key=${constants.googlePlacesToken}&location=${lat},${lng}&radius=10000&callback=JSONP_CALLBACK`,
                {method: 'Get'})
            .map((response)=> {
                return response.json();
            });
    }
}