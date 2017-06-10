import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import "rxjs/add/operator/map";
import * as MarkerClusterer from "node-js-marker-clusterer";
import {SharedService} from "../../shared/shared-service";

@Injectable()
export class GoogleMapsCluster {

    markerCluster: any;

    constructor(public http: Http,
                public sharedService: SharedService) {
    }

    private iconBase = 'img/map/';
    private icons = {
        family: {
            icon: this.iconBase + 'family.png'
        },
        museum: {
            icon: this.iconBase + 'museum.png'
        },
        transport: {
            icon: this.iconBase + 'transport.png'
        },
        sport: {
            icon: this.iconBase + 'sport.png'
        },
        restaurant: {
            icon: this.iconBase + 'restaurant.png'
        },
        cafe: {
            icon: this.iconBase + 'cafe.png'
        },
        nightlife: {
            icon: this.iconBase + 'nightlife.png'
        },
        shopping: {
            icon: this.iconBase + 'shopping.png'
        },
        relax: {
            icon: this.iconBase + 'relax.png'
        },
        outdoor: {
            icon: this.iconBase + 'outdoor.png'
        },
        sightseeing: {
            icon: this.iconBase + 'sightseeing.png'
        },
        hotel: {
            icon: this.iconBase + 'hotel.png'
        },
        none: {
            icon: this.iconBase + 'none.png'
        }
    };

    addCluster(map, points, callbackObject) {
        if (google.maps) {
            let self = this;
            console.log('MAP POINTS', points)
            if (points && points.length > 0) {
                let markers = points.map((point) => {
                    let marker = new google.maps.Marker({
                        position: {
                            lat: point.lat,
                            lng: point.lng
                        },
                        icon: this.icons[point.category.toLowerCase()].icon
                    });
                    return marker;
                });

                markers.forEach((value, index, arr)=> {
                    value.addListener('click', function () {

                        arr.forEach((other, i, a)=> {
                            if (i !== index) {
                                other.setAnimation(null);
                            }
                        });

                        if (value.getAnimation() !== null) {
                            value.setAnimation(null);
                        } else {
                            value.setAnimation(google.maps.Animation.BOUNCE);
                        }
                        self.sharedService.changeSelectedPoint(points[index]);
                    });
                });

                this.markerCluster = new MarkerClusterer(map, markers, {imagePath: 'img/m'});
            }
        } else {
            console.warn('Google maps needs to be loaded before adding a cluster');
        }
    }
}
