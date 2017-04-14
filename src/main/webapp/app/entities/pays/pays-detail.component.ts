import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Pays } from './pays.model';
import { PaysService } from './pays.service';

@Component({
    selector: 'jhi-pays-detail',
    templateUrl: './pays-detail.component.html'
})
export class PaysDetailComponent implements OnInit, OnDestroy {

    pays: Pays;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private paysService: PaysService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['pays']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPays();
    }

    load(id) {
        this.paysService.find(id).subscribe((pays) => {
            this.pays = pays;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPays() {
        this.eventSubscriber = this.eventManager.subscribe('paysListModification', (response) => this.load(this.pays.id));
    }
}
