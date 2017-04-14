import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Adresse } from './adresse.model';
import { AdresseService } from './adresse.service';

@Component({
    selector: 'jhi-adresse-detail',
    templateUrl: './adresse-detail.component.html'
})
export class AdresseDetailComponent implements OnInit, OnDestroy {

    adresse: Adresse;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private adresseService: AdresseService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['adresse']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAdresses();
    }

    load(id) {
        this.adresseService.find(id).subscribe((adresse) => {
            this.adresse = adresse;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAdresses() {
        this.eventSubscriber = this.eventManager.subscribe('adresseListModification', (response) => this.load(this.adresse.id));
    }
}
