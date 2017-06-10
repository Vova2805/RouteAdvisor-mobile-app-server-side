import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { RouteAdvisorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TopRecommendedDetailComponent } from '../../../../../../main/webapp/app/entities/top-recommended/top-recommended-detail.component';
import { TopRecommendedService } from '../../../../../../main/webapp/app/entities/top-recommended/top-recommended.service';
import { TopRecommended } from '../../../../../../main/webapp/app/entities/top-recommended/top-recommended.model';

describe('Component Tests', () => {

    describe('TopRecommended Management Detail Component', () => {
        let comp: TopRecommendedDetailComponent;
        let fixture: ComponentFixture<TopRecommendedDetailComponent>;
        let service: TopRecommendedService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RouteAdvisorTestModule],
                declarations: [TopRecommendedDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TopRecommendedService,
                    EventManager
                ]
            }).overrideComponent(TopRecommendedDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TopRecommendedDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TopRecommendedService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TopRecommended('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.topRecommended).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
