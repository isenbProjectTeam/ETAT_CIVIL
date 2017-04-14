import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Pays } from './pays.model';
import { PaysPopupService } from './pays-popup.service';
import { PaysService } from './pays.service';

@Component({
    selector: 'jhi-pays-delete-dialog',
    templateUrl: './pays-delete-dialog.component.html'
})
export class PaysDeleteDialogComponent {

    pays: Pays;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private paysService: PaysService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['pays']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.paysService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'paysListModification',
                content: 'Deleted an pays'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pays-delete-popup',
    template: ''
})
export class PaysDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private paysPopupService: PaysPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.paysPopupService
                .open(PaysDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
