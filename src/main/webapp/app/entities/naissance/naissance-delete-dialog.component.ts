import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Naissance } from './naissance.model';
import { NaissancePopupService } from './naissance-popup.service';
import { NaissanceService } from './naissance.service';

@Component({
    selector: 'jhi-naissance-delete-dialog',
    templateUrl: './naissance-delete-dialog.component.html'
})
export class NaissanceDeleteDialogComponent {

    naissance: Naissance;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private naissanceService: NaissanceService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['naissance']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.naissanceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'naissanceListModification',
                content: 'Deleted an naissance'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-naissance-delete-popup',
    template: ''
})
export class NaissanceDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private naissancePopupService: NaissancePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.naissancePopupService
                .open(NaissanceDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
