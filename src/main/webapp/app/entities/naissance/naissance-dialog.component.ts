import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService, DataUtils } from 'ng-jhipster';

import { Naissance } from './naissance.model';
import { NaissancePopupService } from './naissance-popup.service';
import { NaissanceService } from './naissance.service';
import { Personne, PersonneService } from '../personne';
import { User, UserService } from '../../shared';
import { Ville, VilleService } from '../ville';

@Component({
    selector: 'jhi-naissance-dialog',
    templateUrl: './naissance-dialog.component.html'
})
export class NaissanceDialogComponent implements OnInit {

    naissance: Naissance;
    authorities: any[];
    isSaving: boolean;

    personnes: Personne[];

    users: User[];

    villes: Ville[];
        constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private alertService: AlertService,
        private naissanceService: NaissanceService,
        private personneService: PersonneService,
        private userService: UserService,
        private villeService: VilleService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['naissance']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.personneService.query().subscribe(
            (res: Response) => { this.personnes = res.json(); }, (res: Response) => this.onError(res.json()));
        this.userService.query().subscribe(
            (res: Response) => { this.users = res.json(); }, (res: Response) => this.onError(res.json()));
        this.villeService.query().subscribe(
            (res: Response) => { this.villes = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, naissance, field, isImage) {
        if (event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                naissance[field] = base64Data;
                naissance[`${field}ContentType`] = file.type;
            });
        }
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.naissance.id !== undefined) {
            this.naissanceService.update(this.naissance)
                .subscribe((res: Naissance) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.naissanceService.create(this.naissance)
                .subscribe((res: Naissance) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Naissance) {
        this.eventManager.broadcast({ name: 'naissanceListModification', content: 'OK'});
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

    trackPersonneById(index: number, item: Personne) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackVilleById(index: number, item: Ville) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-naissance-popup',
    template: ''
})
export class NaissancePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private naissancePopupService: NaissancePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.naissancePopupService
                    .open(NaissanceDialogComponent, params['id']);
            } else {
                this.modalRef = this.naissancePopupService
                    .open(NaissanceDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
