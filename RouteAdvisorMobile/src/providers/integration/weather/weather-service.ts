import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import "rxjs/add/operator/map";
import {LoggerService} from "../../shared/logger-service";
import {constants} from "../../../shared/constants";

@Injectable()
export class WeatherService {

    private weatherBaseURL = 'http://api.openweathermap.org/data/2.5/';
    private weather: any;
    private weatherWrappedResponse: any = {
        success: false
    };
    private kelvinDelta: number = 273;
    private iconsMap = new Map();

    constructor(private http: Http,
                private loggerService: LoggerService) {
        this.iconsMap = this.initMap();
    }

    private initMap() {
        let map = new Map();
        map.set('clear', 'sunny');
        map.set('clouds', 'cloud');
        map.set('flash', 'flash');
        map.set('rain', 'rainy');
        map.set('thunderstorm', 'thunderstorm');
        this.loggerService.log('map', map);
        return map;
    }


    public updateWeather(lat, lon) {
        this.weather = {
            city: '',
            icon: '',
            weather: '',
            description: '',
            forecast: []
        };

        return this.getTodayWeather(lat, lon)
            .toPromise()
            .then((response)=> {
                this.weather.city = response.name;
                this.weather.temp = response.main.temp - this.kelvinDelta;

                this.weather.temp_min = response.main.temp_min - this.kelvinDelta;
                this.weather.temp_max = response.main.temp_max - this.kelvinDelta;
                this.weather.pressure = response.main.pressure + ' hPa';
                this.weather.humidity = response.main.humidity + ' %';

                this.weather.icon = this.iconsMap.get(response.weather[0].main.toLowerCase());
                this.weather.weather = response.weather[0].main;
                this.weather.description = response.weather[0].description;

                this.weather.sunrise = response.sys.sunrise * 1000;
                this.weather.sunset = response.sys.sunset * 1000;

                this.weatherWrappedResponse.weather = this.weather;
                this.weatherWrappedResponse.success = true;
                return this.updateForecast(lat, lon);
            })
            .catch((err)=> {
                this.loggerService.error('WeatherService today error', err);
                return Promise.reject(this.weatherWrappedResponse);
            });
    }

    private updateForecast(lat, lon) {
        return this.getForecast(lat, lon)
            .toPromise()
            .then((response)=> {
                this.weatherWrappedResponse.success = true;

                let forecastList = response.list;

                forecastList.forEach((value, index, arr)=> {
                    let forecastItem = {
                        date: forecastList[index].dt * 1000,
                        min_temp: forecastList[index].main.temp_min - this.kelvinDelta,
                        max_temp: forecastList[index].main.temp_max - this.kelvinDelta,
                        icon: this.iconsMap.get(forecastList[index].weather[0].main.toLowerCase()),
                        weather: forecastList[index].weather[0].main
                    };
                    this.weather.forecast.push(Object.assign({}, forecastItem));
                });

                this.weatherWrappedResponse.weather = this.weather;
                return Promise.resolve(this.weatherWrappedResponse);
            })
            .catch((err)=> {
                this.loggerService.error('WeatherService forecast error', err);
                return Promise.reject(this.weatherWrappedResponse);
            });
    }

    private getTodayWeather(lat, lon) {
        return this.http.get(`${this.weatherBaseURL}weather?lat=${lat}&lon=${lon}&appid=${constants.openWeatherMapKey}`)
            .map((response)=> {
                return response.json();
            });
    }

    private getForecast(lat, lon) {
        return this.http.get(`${this.weatherBaseURL}forecast?lat=${lat}&lon=${lon}&appid=${constants.openWeatherMapKey}`)
            .map((response)=> {
                return response.json();
            });
    }
}