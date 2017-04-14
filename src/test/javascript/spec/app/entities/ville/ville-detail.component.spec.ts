import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EtatCivilTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { VilleDetailComponent } from '../../../../../../main/webapp/app/entities/ville/ville-detail.component';
import { VilleService } from '../../../../../../main/webapp/app/entities/ville/ville.service';
import { Ville } from '../../../../../../main/webapp/app/entities/ville/ville.model';

describe('Component Tests', () => {

    describe('Ville Management Detail Component', () => {
        let comp: VilleDetailComponent;
        let fixture: ComponentFixture<VilleDetailComponent>;
        let service: VilleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EtatCivilTestModule],
                declarations: [VilleDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    VilleService,
                    EventManager
                ]
            }).overrideComponent(VilleDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VilleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VilleService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Ville(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.ville).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
