import {MapDataModel} from "./map-data-model";
export class PlacesFilterModel {
    public categoriesTags;
    public type: FilterType;
    public tripId: string;
    public inTrip: boolean;
    public custom: boolean;
    public favorited: boolean;
    public map: MapDataModel;
}

export enum FilterType{
    PLACES,
    MAP,
    HOTELS
}