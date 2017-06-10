import {Injectable, ElementRef} from "@angular/core";
import {Http} from "@angular/http";
import "rxjs/add/operator/map";
import {ConnectivityService} from "./connectivity-service";
import {GoogleMapsCluster} from "./google-maps-cluster";
import {GoogleDestinationService} from "./google-destination-service";
import {AuthService} from "../../shared/auth-service";
import {SharedService} from "../../shared/shared-service";
import {constants} from "../../../shared/constants";
import {MapDataModel} from "../../../model/map-data-model";

declare let google;
declare let klokantech;

@Injectable()
export class MapInitService {

    map: any;
    mapInitialised: boolean = false;
    apiKey: any;
    mapElement: any;
    pleaseConnect: any;

    constructor(private http: Http,
                private connectivityService: ConnectivityService,
                public mapCluster: GoogleMapsCluster,
                public authService: AuthService,
                public sharedService: SharedService,
                public googleDestinationService: GoogleDestinationService) {
        this.apiKey = constants.googleToken;
    }

    public getDirectionService() {
        return new google.maps.DirectionsService();
    }

    public getPlacesService() {
        return new google.maps.PlacesService();
    }

    public init(mapElement: ElementRef, pleaseConnect: ElementRef, info: MapDataModel, callbackObject) {
        this.mapElement = mapElement.nativeElement;
        this.pleaseConnect = pleaseConnect.nativeElement;

        this.loadGoogleMaps(mapElement, info, callbackObject);
    }

    private loadGoogleMaps(mapElement: ElementRef, info: MapDataModel, callbackObject) {

        this.addConnectivityListeners(mapElement, info, callbackObject);

        if (typeof google == "undefined" || typeof google.maps == "undefined") {

            this.disableMap();

            if (this.connectivityService.isOnline()) {
                window['mapInit'] = () => {
                    this.initMap(mapElement, info, callbackObject);
                    this.enableMap();
                };

                let script = document.createElement("script");
                script.id = "googleMaps";

                if (this.apiKey) {
                    console.log('apiKey', this.apiKey);
                    script.src = 'http://maps.google.com/maps/api/js?key=' + this.apiKey + '&callback=mapInit';
                } else {
                    script.src = 'http://maps.google.com/maps/api/js?callback=mapInit';
                }
                console.log('script', script);
                document.body.appendChild(script);
            }
        }
        else {
            if (this.connectivityService.isOnline()) {
                console.log("showing map");
                this.initMap(mapElement, info, callbackObject);
                this.enableMap();
            }
            else {
                console.log("disabling map");
                this.disableMap();
            }
        }
    }

    private initMap(mapElement: ElementRef, info: MapDataModel, callbackObject) {
        let directionsService = new google.maps.DirectionsService;
        let directionsDisplay = new google.maps.DirectionsRenderer;

        this.mapInitialised = true;
        let latLng = new google.maps.LatLng(this.sharedService.lat, this.sharedService.lng);
        let mapOptions = {
            center: latLng,
            zoom: 14,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };

        this.map = new google.maps.Map(mapElement.nativeElement, mapOptions);

        directionsDisplay.setMap(this.map);

        if (info.allPlaces && info.allPlaces.length > 0) {
            console.log('ALL', info.allPlaces);
            this.mapCluster.addCluster(this.map, info.allPlaces, callbackObject);
        } else {
            console.log('IN TRIP', info.places);
            this.mapCluster.addCluster(this.map, info.places, callbackObject);
        }
        if (info.drawPath && info.places.length > 1) {
            this.googleDestinationService.calculateAndDisplayRoute(directionsService, directionsDisplay, info.places);
        }
    }

    public geolocate() {
        let map = this.map;
        let self = this;
        let infoWindow = new google.maps.InfoWindow();

        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function (position) {
                let pos = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude
                };
                infoWindow.setPosition(pos);
                infoWindow.setContent('Me');
                infoWindow.open(map);
                map.setCenter(pos);
            }, function () {
                self.handleLocationError(true, infoWindow, map.getCenter());
            });
        } else {
            self.handleLocationError(false, infoWindow, map.getCenter());
        }
    }

    public getMyPosition() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function (position) {
                let pos = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude
                };
                return pos;
            });
        } else {
            return null;
        }
    }

    private disableMap() {
        if (this.pleaseConnect) {
            this.pleaseConnect.style.display = "block";
        }
    }

    private enableMap() {
        if (this.pleaseConnect) {
            this.pleaseConnect.style.display = "none";
        }
    }

    private addConnectivityListeners(mapElement: ElementRef, info: MapDataModel, callbackObject): void {

        this.connectivityService.watchOnline().subscribe(() => {

            console.log("online");
            setTimeout(() => {
                if (typeof google == "undefined" || typeof google.maps == "undefined") {
                    this.loadGoogleMaps(mapElement, info, callbackObject);
                } else {
                    if (!this.mapInitialised) {
                        this.initMap(mapElement, info, callbackObject);
                    }
                    this.enableMap();
                }
            }, 2000);
        });

        this.connectivityService.watchOffline().subscribe(() => {
            console.log("offline");
            this.disableMap();
        });
    }
}
