import {CreateTripModel} from "./create-trip-model";

export class ChooseTripPlaceModel {
    public action: Action;
    public actionDetails: ActionDetails;
    public url: string;
    public tripId: string;
    public trip: CreateTripModel;
}

export enum Action{
    NONE,
    CREATE_TRIP
}

export enum ActionDetails{
    CREATE_TRIP_ARRIVAL,
    CREATE_TRIP_ACCOMODATION
}