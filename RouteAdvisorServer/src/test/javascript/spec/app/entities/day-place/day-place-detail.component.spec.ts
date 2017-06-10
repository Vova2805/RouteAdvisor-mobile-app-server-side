import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { RouteAdvisorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DayPlaceDetailComponent } from '../../../../../../main/webapp/app/entities/day-place/day-place-detail.component';
import { DayPlaceService } from '../../../../../../main/webapp/app/entities/day-place/day-place.service';
import { DayPlace } from '../../../../../../main/webapp/app/entities/day-place/day-place.model';

describe('Component Tests', () => {

    describe('DayPlace Management Detail Component', () => {
        let comp: DayPlaceDetailComponent;
        let fixture: ComponentFixture<DayPlaceDetailComponent>;
        let service: DayPlaceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RouteAdvisorTestModule],
                declarations: [DayPlaceDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DayPlaceService,
                    EventManager
                ]
            }).overrideComponent(DayPlaceDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DayPlaceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DayPlaceService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DayPlace('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.dayPlace).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
