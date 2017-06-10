export class MapDataModel {
    public drawPath: boolean;
    public includeAllPlaces: boolean;
    public allPlaces: Array<any>;
    public places: Array<any>;
    public urlParams: string;


    constructor() {
        this.drawPath = true;
        this.includeAllPlaces = false;
        this.allPlaces = new Array<any>();
        this.places = new Array<any>();
    }
}