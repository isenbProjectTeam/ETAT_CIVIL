import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService, DataUtils } from 'ng-jhipster';

import { PieceJointe } from './piece-jointe.model';
import { PieceJointePopupService } from './piece-jointe-popup.service';
import { PieceJointeService } from './piece-jointe.service';
import { Naissance, NaissanceService } from '../naissance';

@Component({
    selector: 'jhi-piece-jointe-dialog',
    templateUrl: './piece-jointe-dialog.component.html'
})
export class PieceJointeDialogComponent implements OnInit {

    pieceJointe: PieceJointe;
    authorities: any[];
    isSaving: boolean;

    naissances: Naissance[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private alertService: AlertService,
        private pieceJointeService: PieceJointeService,
        private naissanceService: NaissanceService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['pieceJointe']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.naissanceService.query().subscribe(
            (res: Response) => { this.naissances = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, pieceJointe, field, isImage) {
        if (event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                pieceJointe[field] = base64Data;
                pieceJointe[`${field}ContentType`] = file.type;
            });
        }
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pieceJointe.id !== undefined) {
            this.pieceJointeService.update(this.pieceJointe)
                .subscribe((res: PieceJointe) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.pieceJointeService.create(this.pieceJointe)
                .subscribe((res: PieceJointe) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: PieceJointe) {
        this.eventManager.broadcast({ name: 'pieceJointeListModification', content: 'OK'});
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
    selector: 'jhi-piece-jointe-popup',
    template: ''
})
export class PieceJointePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pieceJointePopupService: PieceJointePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.pieceJointePopupService
                    .open(PieceJointeDialogComponent, params['id']);
            } else {
                this.modalRef = this.pieceJointePopupService
                    .open(PieceJointeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
