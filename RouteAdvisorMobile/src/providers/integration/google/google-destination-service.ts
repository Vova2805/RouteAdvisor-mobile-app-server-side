import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import "rxjs/add/operator/map";
import {NotificationService} from "../../helper/notification-service";

@Injectable()
export class GoogleDestinationService {

    constructor(public http: Http,
                public notificationService: NotificationService) {

    }

    private getDirectionConfig(points: Array<any>) {
        let origin = points[0];
        let destination = points[points.length - 1];
        let waypoints = [];
        for (var i = 0; i < points.length; i++) {
            waypoints.push({
                location: `${points[i].lat},${points[i].lng}`,
                stopover: true
            });
        }

        let config = {
            origin: `${origin.lat},${origin.lng}`,
            destination: `${destination.lat},${destination.lng}`,
            travelMode: 'WALKING'
        };
        let configWaypoints = {
            origin: `${origin.lat},${origin.lng}`,
            destination: `${destination.lat},${destination.lng}`,
            travelMode: 'WALKING',
            waypoints: waypoints,
            optimizeWaypoints: true
        };

        if (waypoints.length > 2) {
            return configWaypoints;
        }
        console.log('directionConfig', config);
        return config;
    }

    public calculateAndDisplayRoute(directionsService, directionsDisplay, points: Array<any>) {
        if (points && points.length > 0) {
            let directionConfig = this.getDirectionConfig(points);
            directionsService.route(
                directionConfig,
                function (response, status) {
                    if (status === 'OK') {
                        directionsDisplay.setDirections(response);
                    } else {
                        window.alert('Directions request failed due to ' + status);
                    }
                });
        } else {
            this.notificationService.show('No points for displaying');
        }
    }

    public calculateRoute(directionsService, points: Array<any>, callback, self) {
        if (points && points.length > 0) {
            console.log('Call Google direction API');
            let directionConfig = this.getDirectionConfig(points);
            directionsService.route(
                directionConfig,
                function (response, status) {
                    console.log('Google direction API response',response);
                    callback(response, status, self);
                });
        }
    }
}
