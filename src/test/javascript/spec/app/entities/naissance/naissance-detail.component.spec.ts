import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EtatCivilTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { NaissanceDetailComponent } from '../../../../../../main/webapp/app/entities/naissance/naissance-detail.component';
import { NaissanceService } from '../../../../../../main/webapp/app/entities/naissance/naissance.service';
import { Naissance } from '../../../../../../main/webapp/app/entities/naissance/naissance.model';

describe('Component Tests', () => {

    describe('Naissance Management Detail Component', () => {
        let comp: NaissanceDetailComponent;
        let fixture: ComponentFixture<NaissanceDetailComponent>;
        let service: NaissanceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EtatCivilTestModule],
                declarations: [NaissanceDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    NaissanceService,
                    EventManager
                ]
            }).overrideComponent(NaissanceDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NaissanceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NaissanceService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Naissance(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.naissance).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
