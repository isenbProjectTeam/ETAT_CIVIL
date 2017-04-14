import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { RegistreNaissance } from './registre-naissance.model';
import { RegistreNaissanceService } from './registre-naissance.service';

@Component({
    selector: 'jhi-registre-naissance-detail',
    templateUrl: './registre-naissance-detail.component.html'
})
export class RegistreNaissanceDetailComponent implements OnInit, OnDestroy {

    registreNaissance: RegistreNaissance;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private registreNaissanceService: RegistreNaissanceService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['registreNaissance']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRegistreNaissances();
    }

    load(id) {
        this.registreNaissanceService.find(id).subscribe((registreNaissance) => {
            this.registreNaissance = registreNaissance;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRegistreNaissances() {
        this.eventSubscriber = this.eventManager.subscribe('registreNaissanceListModification', (response) => this.load(this.registreNaissance.id));
    }
}
