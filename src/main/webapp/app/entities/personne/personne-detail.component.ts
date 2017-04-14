import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Personne } from './personne.model';
import { PersonneService } from './personne.service';

@Component({
    selector: 'jhi-personne-detail',
    templateUrl: './personne-detail.component.html'
})
export class PersonneDetailComponent implements OnInit, OnDestroy {

    personne: Personne;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private personneService: PersonneService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['personne', 'genre']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPersonnes();
    }

    load(id) {
        this.personneService.find(id).subscribe((personne) => {
            this.personne = personne;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPersonnes() {
        this.eventSubscriber = this.eventManager.subscribe('personneListModification', (response) => this.load(this.personne.id));
    }
}
