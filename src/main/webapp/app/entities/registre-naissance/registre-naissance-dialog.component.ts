import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { RegistreNaissance } from './registre-naissance.model';
import { RegistreNaissancePopupService } from './registre-naissance-popup.service';
import { RegistreNaissanceService } from './registre-naissance.service';
import { Naissance, NaissanceService } from '../naissance';

@Component({
    selector: 'jhi-registre-naissance-dialog',
    templateUrl: './registre-naissance-dialog.component.html'
})
export class RegistreNaissanceDialogComponent implements OnInit {

    registreNaissance: RegistreNaissance;
    authorities: any[];
    isSaving: boolean;

    naissances: Naissance[];
        constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private registreNaissanceService: RegistreNaissanceService,
        private naissanceService: NaissanceService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['registreNaissance']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.naissanceService.query().subscribe(
            (res: Response) => { this.naissances = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.registreNaissance.id !== undefined) {
            this.registreNaissanceService.update(this.registreNaissance)
                .subscribe((res: RegistreNaissance) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.registreNaissanceService.create(this.registreNaissance)
                .subscribe((res: RegistreNaissance) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: RegistreNaissance) {
        this.eventManager.broadcast({ name: 'registreNaissanceListModification', content: 'OK'});
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

    trackNaissanceById(index: number, item: Naissance) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-registre-naissance-popup',
    template: ''
})
export class RegistreNaissancePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private registreNaissancePopupService: RegistreNaissancePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.registreNaissancePopupService
                    .open(RegistreNaissanceDialogComponent, params['id']);
            } else {
                this.modalRef = this.registreNaissancePopupService
                    .open(RegistreNaissanceDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
