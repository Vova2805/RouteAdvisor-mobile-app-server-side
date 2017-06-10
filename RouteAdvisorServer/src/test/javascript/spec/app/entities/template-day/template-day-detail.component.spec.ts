import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { RouteAdvisorTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TemplateDayDetailComponent } from '../../../../../../main/webapp/app/entities/template-day/template-day-detail.component';
import { TemplateDayService } from '../../../../../../main/webapp/app/entities/template-day/template-day.service';
import { TemplateDay } from '../../../../../../main/webapp/app/entities/template-day/template-day.model';

describe('Component Tests', () => {

    describe('TemplateDay Management Detail Component', () => {
        let comp: TemplateDayDetailComponent;
        let fixture: ComponentFixture<TemplateDayDetailComponent>;
        let service: TemplateDayService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RouteAdvisorTestModule],
                declarations: [TemplateDayDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TemplateDayService,
                    EventManager
                ]
            }).overrideComponent(TemplateDayDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TemplateDayDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TemplateDayService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TemplateDay('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.templateDay).toEqual(jasmine.objectContaining({id:'aaa'}));
            });
        });
    });

});
