import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { RouteAdvisorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FeatureDetailComponent } from '../../../../../../main/webapp/app/entities/feature/feature-detail.component';
import { FeatureService } from '../../../../../../main/webapp/app/entities/feature/feature.service';
import { Feature } from '../../../../../../main/webapp/app/entities/feature/feature.model';

describe('Component Tests', () => {

    describe('Feature Management Detail Component', () => {
        let comp: FeatureDetailComponent;
        let fixture: ComponentFixture<FeatureDetailComponent>;
        let service: FeatureService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RouteAdvisorTestModule],
                declarations: [FeatureDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FeatureService,
                    EventManager
                ]
            }).overrideComponent(FeatureDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FeatureDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FeatureService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Feature('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.feature).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
