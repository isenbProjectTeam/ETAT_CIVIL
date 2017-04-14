import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Ville } from './ville.model';
import { VillePopupService } from './ville-popup.service';
import { VilleService } from './ville.service';

@Component({
    selector: 'jhi-ville-delete-dialog',
    templateUrl: './ville-delete-dialog.component.html'
})
export class VilleDeleteDialogComponent {

    ville: Ville;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private villeService: VilleService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['ville']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.villeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'villeListModification',
                content: 'Deleted an ville'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ville-delete-popup',
    template: ''
})
export class VilleDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private villePopupService: VillePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.villePopupService
                .open(VilleDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
