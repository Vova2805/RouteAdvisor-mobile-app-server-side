import {Injectable} from "@angular/core";
import "rxjs/add/operator/map";
import {Http} from "@angular/http";
import {constants} from "../../../shared/constants";

declare let gapi;

@Injectable()
export class YoutubeService {

    constructor(private http: Http) {
        console.log('Hello YoutubeService Provider');
    }

    maxResults = 10;
    url = 'https://www.googleapis.com/youtube/v3/search?part=id,snippet&q=';

    public getVideos(query: any) {
        return this.http.get(this.url + query + '&type=video&order=viewCount&maxResults=' + this.maxResults + '&key=' + constants.googleToken)
            .map(res => res.json());
    }
}