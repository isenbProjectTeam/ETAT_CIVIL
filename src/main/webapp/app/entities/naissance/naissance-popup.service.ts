import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Naissance } from './naissance.model';
import { NaissanceService } from './naissance.service';
@Injectable()
export class NaissancePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private naissanceService: NaissanceService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.naissanceService.find(id).subscribe((naissance) => {
                if (naissance.dateDeclaration) {
                    naissance.dateDeclaration = {
                        year: naissance.dateDeclaration.getFullYear(),
                        month: naissance.dateDeclaration.getMonth() + 1,
                        day: naissance.dateDeclaration.getDate()
                    };
                }
                this.naissanceModalRef(component, naissance);
            });
        } else {
            return this.naissanceModalRef(component, new Naissance());
        }
    }

    naissanceModalRef(component: Component, naissance: Naissance): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.naissance = naissance;
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
