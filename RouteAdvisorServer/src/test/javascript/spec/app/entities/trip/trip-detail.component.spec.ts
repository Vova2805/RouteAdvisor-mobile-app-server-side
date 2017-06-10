import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { RouteAdvisorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TripDetailComponent } from '../../../../../../main/webapp/app/entities/trip/trip-detail.component';
import { TripService } from '../../../../../../main/webapp/app/entities/trip/trip.service';
import { Trip } from '../../../../../../main/webapp/app/entities/trip/trip.model';

describe('Component Tests', () => {

    describe('Trip Management Detail Component', () => {
        let comp: TripDetailComponent;
        let fixture: ComponentFixture<TripDetailComponent>;
        let service: TripService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RouteAdvisorTestModule],
                declarations: [TripDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TripService,
                    EventManager
                ]
            }).overrideComponent(TripDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TripDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TripService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Trip('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.trip).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
