import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EtatCivilTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PersonneDetailComponent } from '../../../../../../main/webapp/app/entities/personne/personne-detail.component';
import { PersonneService } from '../../../../../../main/webapp/app/entities/personne/personne.service';
import { Personne } from '../../../../../../main/webapp/app/entities/personne/personne.model';

describe('Component Tests', () => {

    describe('Personne Management Detail Component', () => {
        let comp: PersonneDetailComponent;
        let fixture: ComponentFixture<PersonneDetailComponent>;
        let service: PersonneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EtatCivilTestModule],
                declarations: [PersonneDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PersonneService,
                    EventManager
                ]
            }).overrideComponent(PersonneDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonneDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonneService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Personne(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.personne).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
