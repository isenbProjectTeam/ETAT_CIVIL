import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Personne } from './personne.model';
import { PersonnePopupService } from './personne-popup.service';
import { PersonneService } from './personne.service';
import { Adresse, AdresseService } from '../adresse';
import { Ville, VilleService } from '../ville';

@Component({
    selector: 'jhi-personne-dialog',
    templateUrl: './personne-dialog.component.html'
})
export class PersonneDialogComponent implements OnInit {

    personne: Personne;
    authorities: any[];
    isSaving: boolean;

    adresses: Adresse[];

    villes: Ville[];

    personnes: Personne[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private personneService: PersonneService,
        private adresseService: AdresseService,
        private villeService: VilleService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['personne', 'genre']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.adresseService.query().subscribe(
            (res: Response) => { this.adresses = res.json(); }, (res: Response) => this.onError(res.json()));
        this.villeService.query().subscribe(
            (res: Response) => { this.villes = res.json(); }, (res: Response) => this.onError(res.json()));
        this.personneService.query().subscribe(
            (res: Response) => { this.personnes = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.personne.id !== undefined) {
            this.personneService.update(this.personne)
                .subscribe((res: Personne) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.personneService.create(this.personne)
                .subscribe((res: Personne) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Personne) {
        this.eventManager.broadcast({ name: 'personneListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackAdresseById(index: number, item: Adresse) {
        return item.id;
    }

    trackVilleById(index: number, item: Ville) {
        return item.id;
    }

    trackPersonneById(index: number, item: Personne) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-personne-popup',
    template: ''
})
export class PersonnePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personnePopupService: PersonnePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.personnePopupService
                    .open(PersonneDialogComponent, params['id']);
            } else {
                this.modalRef = this.personnePopupService
                    .open(PersonneDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
