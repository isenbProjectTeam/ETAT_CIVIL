import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Pays } from './pays.model';
import { PaysPopupService } from './pays-popup.service';
import { PaysService } from './pays.service';

@Component({
    selector: 'jhi-pays-dialog',
    templateUrl: './pays-dialog.component.html'
})
export class PaysDialogComponent implements OnInit {

    pays: Pays;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private paysService: PaysService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['pays']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pays.id !== undefined) {
            this.paysService.update(this.pays)
                .subscribe((res: Pays) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.paysService.create(this.pays)
                .subscribe((res: Pays) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Pays) {
        this.eventManager.broadcast({ name: 'paysListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-pays-popup',
    template: ''
})
export class PaysPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private paysPopupService: PaysPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.paysPopupService
                    .open(PaysDialogComponent, params['id']);
            } else {
                this.modalRef = this.paysPopupService
                    .open(PaysDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
