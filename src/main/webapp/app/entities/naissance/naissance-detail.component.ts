import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , DataUtils } from 'ng-jhipster';

import { Naissance } from './naissance.model';
import { NaissanceService } from './naissance.service';

@Component({
    selector: 'jhi-naissance-detail',
    templateUrl: './naissance-detail.component.html'
})
export class NaissanceDetailComponent implements OnInit, OnDestroy {

    naissance: Naissance;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private naissanceService: NaissanceService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['naissance']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNaissances();
    }

    load(id) {
        this.naissanceService.find(id).subscribe((naissance) => {
            this.naissance = naissance;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNaissances() {
        this.eventSubscriber = this.eventManager.subscribe('naissanceListModification', (response) => this.load(this.naissance.id));
    }
}
