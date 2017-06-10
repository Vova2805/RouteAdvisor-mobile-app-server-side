import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { RouteAdvisorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PlaceDetailComponent } from '../../../../../../main/webapp/app/entities/place/place-detail.component';
import { PlaceService } from '../../../../../../main/webapp/app/entities/place/place.service';
import { Place } from '../../../../../../main/webapp/app/entities/place/place.model';

describe('Component Tests', () => {

    describe('Place Management Detail Component', () => {
        let comp: PlaceDetailComponent;
        let fixture: ComponentFixture<PlaceDetailComponent>;
        let service: PlaceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RouteAdvisorTestModule],
                declarations: [PlaceDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PlaceService,
                    EventManager
                ]
            }).overrideComponent(PlaceDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlaceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlaceService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Place('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.place).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
