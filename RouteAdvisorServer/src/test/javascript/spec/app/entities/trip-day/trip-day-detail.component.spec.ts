import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { RouteAdvisorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TripDayDetailComponent } from '../../../../../../main/webapp/app/entities/trip-day/trip-day-detail.component';
import { TripDayService } from '../../../../../../main/webapp/app/entities/trip-day/trip-day.service';
import { TripDay } from '../../../../../../main/webapp/app/entities/trip-day/trip-day.model';

describe('Component Tests', () => {

    describe('TripDay Management Detail Component', () => {
        let comp: TripDayDetailComponent;
        let fixture: ComponentFixture<TripDayDetailComponent>;
        let service: TripDayService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RouteAdvisorTestModule],
                declarations: [TripDayDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TripDayService,
                    EventManager
                ]
            }).overrideComponent(TripDayDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TripDayDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TripDayService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TripDay('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.tripDay).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
