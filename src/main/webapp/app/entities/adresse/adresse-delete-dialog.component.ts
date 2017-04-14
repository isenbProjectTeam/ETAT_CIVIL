import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Adresse } from './adresse.model';
import { AdressePopupService } from './adresse-popup.service';
import { AdresseService } from './adresse.service';

@Component({
    selector: 'jhi-adresse-delete-dialog',
    templateUrl: './adresse-delete-dialog.component.html'
})
export class AdresseDeleteDialogComponent {

    adresse: Adresse;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private adresseService: AdresseService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['adresse']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.adresseService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'adresseListModification',
                content: 'Deleted an adresse'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-adresse-delete-popup',
    template: ''
})
export class AdresseDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private adressePopupService: AdressePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.adressePopupService
                .open(AdresseDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
