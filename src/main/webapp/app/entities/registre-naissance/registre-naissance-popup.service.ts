import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RegistreNaissance } from './registre-naissance.model';
import { RegistreNaissanceService } from './registre-naissance.service';
@Injectable()
export class RegistreNaissancePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private registreNaissanceService: RegistreNaissanceService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.registreNaissanceService.find(id).subscribe((registreNaissance) => {
                if (registreNaissance.annee) {
                    registreNaissance.annee = {
                        year: registreNaissance.annee.getFullYear(),
                        month: registreNaissance.annee.getMonth() + 1,
                        day: registreNaissance.annee.getDate()
                    };
                }
                this.registreNaissanceModalRef(component, registreNaissance);
            });
        } else {
            return this.registreNaissanceModalRef(component, new RegistreNaissance());
        }
    }

    registreNaissanceModalRef(component: Component, registreNaissance: RegistreNaissance): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.registreNaissance = registreNaissance;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
