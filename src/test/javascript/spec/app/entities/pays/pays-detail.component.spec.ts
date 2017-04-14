import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EtatCivilTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PaysDetailComponent } from '../../../../../../main/webapp/app/entities/pays/pays-detail.component';
import { PaysService } from '../../../../../../main/webapp/app/entities/pays/pays.service';
import { Pays } from '../../../../../../main/webapp/app/entities/pays/pays.model';

describe('Component Tests', () => {

    describe('Pays Management Detail Component', () => {
        let comp: PaysDetailComponent;
        let fixture: ComponentFixture<PaysDetailComponent>;
        let service: PaysService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EtatCivilTestModule],
                declarations: [PaysDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PaysService,
                    EventManager
                ]
            }).overrideComponent(PaysDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PaysDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaysService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Pays(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pays).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
