import {Injectable} from "@angular/core";
import {Http, Jsonp} from "@angular/http";
import "rxjs/add/operator/map";

@Injectable()
export class WikipediaService {

    private baseURL = 'https://en.wikipedia.org/w/api.php';

    constructor(private http: Http,
                public jsonp: Jsonp) {
    }

    public search(term: string) {
        return this.jsonp.request(`${this.baseURL}?action=opensearch&prop=info&format=json&inprop=url&search=${term}&callback=JSONP_CALLBACK`,
            {method: 'Get'})
            .map((response)=> {
                return response.json();
            });
    }
}