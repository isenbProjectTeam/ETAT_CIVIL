import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Ville } from './ville.model';
import { VillePopupService } from './ville-popup.service';
import { VilleService } from './ville.service';
import { Pays, PaysService } from '../pays';

@Component({
    selector: 'jhi-ville-dialog',
    templateUrl: './ville-dialog.component.html'
})
export class VilleDialogComponent implements OnInit {

    ville: Ville;
    authorities: any[];
    isSaving: boolean;

    pays: Pays[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private villeService: VilleService,
        private paysService: PaysService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['ville']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.paysService.query().subscribe(
            (res: Response) => { this.pays = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.ville.id !== undefined) {
            this.villeService.update(this.ville)
                .subscribe((res: Ville) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.villeService.create(this.ville)
                .subscribe((res: Ville) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Ville) {
        this.eventManager.broadcast({ name: 'villeListModification', content: 'OK'});
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

    trackPaysById(index: number, item: Pays) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-ville-popup',
    template: ''
})
export class VillePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private villePopupService: VillePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.villePopupService
                    .open(VilleDialogComponent, params['id']);
            } else {
                this.modalRef = this.villePopupService
                    .open(VilleDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
