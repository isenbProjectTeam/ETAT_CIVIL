import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { RegistreNaissance } from './registre-naissance.model';
import { RegistreNaissancePopupService } from './registre-naissance-popup.service';
import { RegistreNaissanceService } from './registre-naissance.service';

@Component({
    selector: 'jhi-registre-naissance-delete-dialog',
    templateUrl: './registre-naissance-delete-dialog.component.html'
})
export class RegistreNaissanceDeleteDialogComponent {

    registreNaissance: RegistreNaissance;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private registreNaissanceService: RegistreNaissanceService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['registreNaissance']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.registreNaissanceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'registreNaissanceListModification',
                content: 'Deleted an registreNaissance'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-registre-naissance-delete-popup',
    template: ''
})
export class RegistreNaissanceDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private registreNaissancePopupService: RegistreNaissancePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.registreNaissancePopupService
                .open(RegistreNaissanceDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
