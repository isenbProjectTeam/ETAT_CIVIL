import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Adresse } from './adresse.model';
import { AdressePopupService } from './adresse-popup.service';
import { AdresseService } from './adresse.service';
import { Ville, VilleService } from '../ville';
import { Pays, PaysService } from '../pays';

@Component({
    selector: 'jhi-adresse-dialog',
    templateUrl: './adresse-dialog.component.html'
})
export class AdresseDialogComponent implements OnInit {

    adresse: Adresse;
    authorities: any[];
    isSaving: boolean;

    villes: Ville[];

    pays: Pays[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private adresseService: AdresseService,
        private villeService: VilleService,
        private paysService: PaysService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['adresse']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.villeService.query().subscribe(
            (res: Response) => { this.villes = res.json(); }, (res: Response) => this.onError(res.json()));
        this.paysService.query().subscribe(
            (res: Response) => { this.pays = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.adresse.id !== undefined) {
            this.adresseService.update(this.adresse)
                .subscribe((res: Adresse) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.adresseService.create(this.adresse)
                .subscribe((res: Adresse) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Adresse) {
        this.eventManager.broadcast({ name: 'adresseListModification', content: 'OK'});
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

    trackVilleById(index: number, item: Ville) {
        return item.id;
    }

    trackPaysById(index: number, item: Pays) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-adresse-popup',
    template: ''
})
export class AdressePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private adressePopupService: AdressePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.adressePopupService
                    .open(AdresseDialogComponent, params['id']);
            } else {
                this.modalRef = this.adressePopupService
                    .open(AdresseDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
