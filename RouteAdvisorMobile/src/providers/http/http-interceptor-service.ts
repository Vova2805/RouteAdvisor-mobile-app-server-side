import {Injectable} from "@angular/core";
import {Http, RequestOptionsArgs} from "@angular/http";
import "rxjs/add/operator/map";
import {Observable} from "rxjs";
import {constants} from "../../shared/constants";

@Injectable()
export class HttpInterceptorService {

    constructor(public http: Http) {
        console.log('Hello HttpInterceptorService Provider');
    }

    public get(url: string, options?: RequestOptionsArgs) {
        console.log(`GET call ${constants.serverBaseURL}${url}`);
        return this.http.get(`${constants.serverBaseURL}${url}`, options)
            .map((response)=> {
                return response.json();
            });
    }

    public post(url: string, body: any, options?: RequestOptionsArgs) {
        console.log(`POST call ${constants.serverBaseURL}${url}`);
        return this.http.post(`${constants.serverBaseURL}${url}`, this.convert(body), options)
            .map((response)=> {
                return response.json();
            });
    }

    public put(url: string, body: any, options?: RequestOptionsArgs) {
        console.log(`PUT call ${constants.serverBaseURL}${url}`);
        return this.http.put(`${constants.serverBaseURL}${url}`, this.convert(body), options)
            .map((response)=> {
                return response.json();
            });
    }

    public delete(url: string, options?: RequestOptionsArgs) {
        console.log(`DELETE call ${constants.serverBaseURL}${url}`);
        return this.http.delete(`${constants.serverBaseURL}${url}`, options);
    }

    private intercept(observable: Observable<Response>): Observable<Response> {
        return observable.catch((err, source)=> {
            if (err.stack == 401) {

            } else {
                return Observable.throw(err);
            }
        });
    }

    private convert(object): any {
        const copy = Object.assign({}, object);
        return copy;
    }
}