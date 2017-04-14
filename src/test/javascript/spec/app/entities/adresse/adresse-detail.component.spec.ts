import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EtatCivilTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AdresseDetailComponent } from '../../../../../../main/webapp/app/entities/adresse/adresse-detail.component';
import { AdresseService } from '../../../../../../main/webapp/app/entities/adresse/adresse.service';
import { Adresse } from '../../../../../../main/webapp/app/entities/adresse/adresse.model';

describe('Component Tests', () => {

    describe('Adresse Management Detail Component', () => {
        let comp: AdresseDetailComponent;
        let fixture: ComponentFixture<AdresseDetailComponent>;
        let service: AdresseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EtatCivilTestModule],
                declarations: [AdresseDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AdresseService,
                    EventManager
                ]
            }).overrideComponent(AdresseDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AdresseDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdresseService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Adresse(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.adresse).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
