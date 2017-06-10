import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { RouteAdvisorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ReviewDetailComponent } from '../../../../../../main/webapp/app/entities/review/review-detail.component';
import { ReviewService } from '../../../../../../main/webapp/app/entities/review/review.service';
import { Review } from '../../../../../../main/webapp/app/entities/review/review.model';

describe('Component Tests', () => {

    describe('Review Management Detail Component', () => {
        let comp: ReviewDetailComponent;
        let fixture: ComponentFixture<ReviewDetailComponent>;
        let service: ReviewService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RouteAdvisorTestModule],
                declarations: [ReviewDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ReviewService,
                    EventManager
                ]
            }).overrideComponent(ReviewDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReviewDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReviewService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Review('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.review).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
