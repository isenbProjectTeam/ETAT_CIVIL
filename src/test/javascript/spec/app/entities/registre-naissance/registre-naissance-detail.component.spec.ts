import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EtatCivilTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RegistreNaissanceDetailComponent } from '../../../../../../main/webapp/app/entities/registre-naissance/registre-naissance-detail.component';
import { RegistreNaissanceService } from '../../../../../../main/webapp/app/entities/registre-naissance/registre-naissance.service';
import { RegistreNaissance } from '../../../../../../main/webapp/app/entities/registre-naissance/registre-naissance.model';

describe('Component Tests', () => {

    describe('RegistreNaissance Management Detail Component', () => {
        let comp: RegistreNaissanceDetailComponent;
        let fixture: ComponentFixture<RegistreNaissanceDetailComponent>;
        let service: RegistreNaissanceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EtatCivilTestModule],
                declarations: [RegistreNaissanceDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RegistreNaissanceService,
                    EventManager
                ]
            }).overrideComponent(RegistreNaissanceDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RegistreNaissanceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegistreNaissanceService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RegistreNaissance(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.registreNaissance).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
