import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { PieceJointe } from './piece-jointe.model';
import { PieceJointePopupService } from './piece-jointe-popup.service';
import { PieceJointeService } from './piece-jointe.service';

@Component({
    selector: 'jhi-piece-jointe-delete-dialog',
    templateUrl: './piece-jointe-delete-dialog.component.html'
})
export class PieceJointeDeleteDialogComponent {

    pieceJointe: PieceJointe;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private pieceJointeService: PieceJointeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['pieceJointe']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pieceJointeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pieceJointeListModification',
                content: 'Deleted an pieceJointe'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-piece-jointe-delete-popup',
    template: ''
})
export class PieceJointeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pieceJointePopupService: PieceJointePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.pieceJointePopupService
                .open(PieceJointeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
